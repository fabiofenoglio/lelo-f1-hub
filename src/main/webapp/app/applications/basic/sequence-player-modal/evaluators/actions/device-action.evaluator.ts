import { ISequenceStepActionRunContext, SequencePlayStatus } from '../../sequence-player.model';
import { SequenceParameterEvaluator } from '../parameter.evaluator';


export abstract class SequenceDeviceActionEvaluator {
    
    private static evaluators: {
        [definition: string] : (actionContext: ISequenceStepActionRunContext) => Promise<any>
    } = {
        SET_MAIN_MOTOR_SPEED(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting motor speed via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setMainMotorSpeed(SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED'));
        },

        SET_VIBE_SPEED(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting vibrator speed via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setVibratorSpeed(SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED'));
        },

        SET_MAIN_MOTOR_SPEED_RANGE(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting motor speed via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setMainMotorSpeed(
                Math.round(SequenceParameterEvaluator.randomInterval(
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_MIN'),
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_MAX')
                ))
            );
        },

        SET_VIBE_SPEED_RANGE(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting vibrator speed via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setVibratorSpeed(
                Math.round(SequenceParameterEvaluator.randomInterval(
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_MIN'),
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_MAX')
                ))
            );
        },

        SET_MAIN_MOTOR_RAMP(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting motor speed ramp via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            return SequenceDeviceActionEvaluator.ramp(
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_START'),
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_END'),
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'RAMP_TIME'),
                (value: number) => {
                    if (!client.isConnected()) {
                        return Promise.reject('not connected');
                    }
                    actionContext.stepContext.sequenceContext.logger.debug('setting motor speed via client interface to ' + value);
                    return client.setMainMotorSpeed(Math.round(value));
                },
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.PAUSED,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.ABORTING,
                actionContext
            );
        },

        SET_VIBE_RAMP(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('setting vibrator speed ramp via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            return SequenceDeviceActionEvaluator.ramp(
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_START'),
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'SPEED_END'),
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'RAMP_TIME'),
                (value: number) => {
                    if (!client.isConnected()) {
                        return Promise.reject('not connected');
                    }
                    actionContext.stepContext.sequenceContext.logger.debug('setting vibrator speed via client interface to ' + value);
                    return client.setVibratorSpeed(Math.round(value));
                },
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.PAUSED,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.ABORTING,
                actionContext
            );
        },

        STOP_MAIN_MOTOR(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('stopping motor via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setMainMotorSpeed(0);
        },

        STOP_VIBE(actionContext: ISequenceStepActionRunContext): Promise<number> {
            actionContext.stepContext.sequenceContext.logger.debug('stopping vibrator via client interface');
            const client = actionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.setVibratorSpeed(0);
        },

    };

    public static canHandle(actionContext: ISequenceStepActionRunContext): boolean {
        return !!this.evaluators[actionContext.action.definitionCode!];
    }

    public static async evaluate(actionContext: ISequenceStepActionRunContext): Promise<any> {
        const logger = actionContext.stepContext.sequenceContext.logger;
        
        logger.debug('running device action ' + actionContext.action.definitionCode);

        const evaluator = this.evaluators[actionContext.action.definitionCode!];
        if (!evaluator) {
            // TODO emit warning
            logger.warn('running unknown device action', actionContext.action.definitionCode);
            return false;
        }

        const result = await evaluator(actionContext);
        logger.debug('action ran with result', result);
        return result;
    }

    private static async ramp(
        min: number, max: number, time: number, 
        applier: (v: number) => Promise<any>,
        pause: () => boolean,
        cancel: () => boolean,
        actionContext: ISequenceStepActionRunContext 
    ): Promise<any> {
        const logger = actionContext.stepContext.sequenceContext.logger;
        
        const steps = SequenceParameterEvaluator.buildRamp(min, max, time, 300);

        logger.trace('handling ramp with configured steps', steps);
        actionContext.progress = 0;

        let stepIndex = 0;
        let elapsed = 0;
        let currentStep;
        let nextStep;
        let lastSetValue = null;
        let timeOffset = 0;
        
        const startTime = new Date().getTime();

        while (!cancel()) {
            if (cancel()) {
                logger.trace('interrupted from cancel handler');
                break;
            }

            currentStep = steps[stepIndex];
            nextStep = steps.length > stepIndex + 1 ? steps[stepIndex + 1] : null;

            const elapsedBefore = new Date().getTime() - startTime - timeOffset;
            
            // eslint-disable-next-line require-atomic-updates
            actionContext.progress = Math.round(100.0 * elapsedBefore / time);
            await this.sleepTick();

            logger.debug('handling ramp step ' + stepIndex + ' with elapsed ' + elapsedBefore);
            try {
                await applier(currentStep.value);
            } catch (err) {
                logger.error('error applying ramp value', err);
            }
            lastSetValue = currentStep.value;

            elapsed = new Date().getTime() - startTime - timeOffset;
            
            // eslint-disable-next-line require-atomic-updates
            actionContext.progress = Math.round(100.0 * elapsed / time);
            await this.sleepTick();

            logger.trace('handled ramp step ' + stepIndex + ' with elapsed ' + elapsed 
                + ' took ' + (elapsed - elapsedBefore) + ' ms');

            while (nextStep && elapsed > nextStep.time) {
                stepIndex ++;
                logger.warn('skipping step ' + stepIndex + ' because of too much time elapsed handling this one', stepIndex);
                nextStep = steps.length > stepIndex + 1 ? steps[stepIndex + 1] : null;
            }

            if (!nextStep) {
                logger.trace('interrupted because of missing next step');
                break;
            }

            if (elapsed < nextStep.time) {
                const toSleep = nextStep.time - elapsed;
                logger.trace('sleeping other ' + toSleep + ' ms to wait for next step');
                await SequenceDeviceActionEvaluator.sleep(toSleep, cancel);
            } else {
                logger.trace('enough elapsed, no need to wait');
            }

            if (cancel()) {
                logger.trace('interrupted from cancel handler');
                break;
            }

            if (pause()) {
                logger.trace('paused from pause handler'); 
                const pauseStart = new Date().getTime();
                while (pause()) {
                    await SequenceDeviceActionEvaluator.sleep(50, () => !pause());
                }
                timeOffset += (new Date().getTime() - pauseStart);
            }

            logger.trace('moving to next step');
            stepIndex ++;
        }

        // ensure final value is always reached
        if (!cancel()) {
            if (max > (lastSetValue || 0)) {
                logger.warn('final value has not been reached, setting forcefully');
                await applier(max);
            }
        }

        logger.debug('ramp handling completed');
    }

    public static sleep(ms: number, cancel: () => boolean): Promise<void> {
        const step = 10;
        let counter = 0;
        return new Promise((resolve) => {
            const handler = setInterval(() => {
                counter += step;
                if (counter >= ms || cancel()) {
                    clearInterval(handler);
                    resolve();
                }
            }, step);
        })
    }

    private static sleepTick(): Promise<void> {
        return new Promise((resolve) => {
          setTimeout(() => {
            resolve();
          }, 1);
        })
    }
    
}