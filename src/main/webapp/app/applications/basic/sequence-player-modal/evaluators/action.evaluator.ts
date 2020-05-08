import { ISequenceStepActionRunContext, ISequenceStepRunContext, SequencePlayStatus } from '../sequence-player.model';
import { SequenceParameterEvaluator } from './parameter.evaluator';
import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceDeviceActionEvaluator } from './actions/device-action.evaluator';


export abstract class SequenceActionEvaluator {
    
    private static evaluators: {
        [definition: string] : (actionContext: ISequenceStepActionRunContext) => Promise<any>
    } = {
        NOP(): Promise<void> { 
            return Promise.resolve();
        },
        WAIT(actionContext: ISequenceStepActionRunContext): Promise<void> {
            return SequenceActionEvaluator.sleep(
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'TIME'),
                actionContext,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.PAUSED,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.ABORTING ) 
        },
        WAIT_RANGE(actionContext: ISequenceStepActionRunContext): Promise<void> {
            return SequenceActionEvaluator.sleep(
                SequenceParameterEvaluator.randomInterval(
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'TIME_MIN'),
                    SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'TIME_MAX')
                ),
                actionContext,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.PAUSED,
                () => actionContext.stepContext.sequenceContext.status === SequencePlayStatus.ABORTING )
        },
        GOTO(actionContext: ISequenceStepActionRunContext): Promise<void> {
            actionContext.stepContext.sequenceContext.pendingHooks.gotoStep = 
                SequenceParameterEvaluator.requireParameter(actionContext.parameterMap, 'STEP_ORDINAL') - 1;

            return Promise.resolve();
        },
    };

    static buildContext(action: ISequenceStepAction, stepContext: ISequenceStepRunContext): ISequenceStepActionRunContext {
        const actionContext: ISequenceStepActionRunContext = {
            stepContext,
            action,
            parameterMap: {}
        };
        actionContext.parameterMap = SequenceParameterEvaluator.extractParameterMap(actionContext);
        return actionContext;
    }

    static async evaluateAction(actionContext: ISequenceStepActionRunContext): Promise<boolean> {
        const logger = actionContext.stepContext.sequenceContext.logger;
        logger.debug('evaluating action ' + actionContext.action.definitionCode);

        if (! actionContext.action.definitionCode) {
            // TODO emit warning
            logger.warn('evaluating action with missing definition', actionContext.action.id);
            return false;
        }

        const evaluator = this.evaluators[actionContext.action.definitionCode];
        if (evaluator) {
            const result = await evaluator(actionContext);
            logger.debug('action evaluated to', result);
            return result;            
        } else if (SequenceDeviceActionEvaluator.canHandle(actionContext)) {
            return SequenceDeviceActionEvaluator.evaluate(actionContext);
        } else {
            // TODO emit warning
            logger.warn('evaluating unknown action', actionContext.action.definitionCode);
            return false;
        }
    }

    public static sleep(ms: number, actionContext: ISequenceStepActionRunContext,
        pause: () => boolean,
        cancel: () => boolean
    ): Promise<void> {
        const step = 50;
        let counter = 0;
        return new Promise((resolve) => {
            const handler = setInterval(() => {
                if (!pause()) {
                    counter += step;
                }

                actionContext.progress = Math.round(100.0 * counter / ms);

                if (counter >= ms || cancel()) {
                    clearInterval(handler);
                    resolve();
                }
            }, step);
        })
    }

}