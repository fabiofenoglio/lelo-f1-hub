import { ISequenceStepConditionRunContext, SequenceStepMessageLevel } from '../../sequence-player.model';
import { SequenceParameterEvaluator } from '../parameter.evaluator';
import { SequenceOperatorEvaluator } from '../operator.evaluator';


export abstract class SequenceDeviceTestConditionEvaluator {
    
    private static variableEvaluators: {
        [definition: string] : (conditionContext: ISequenceStepConditionRunContext) => Promise<any>
    } = {
        'USER_VARIABLE': () => Promise.resolve(true), // NOT SUPPORTED (OBSOLETE)
        'RANDOM': () => Promise.resolve(Math.random() * 100.0), // NOT SUPPORTED (OBSOLETE)
        'RUN_TIME': () => Promise.resolve(true), // NOT SUPPORTED YET
        'CENTRAL_BUTTON_PRESSED': () => Promise.resolve(true), // NOT SUPPORTED YET
        'PLUS_BUTTON_PRESSED': () => Promise.resolve(true), // NOT SUPPORTED YET
        'MINUS_BUTTON_PRESSED': () => Promise.resolve(true), // NOT SUPPORTED YET
        MOTOR_SPEED(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading motor speed from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getMainMotorSpeed();
        },
        VIBE_SPEED(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading vibe speed from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getVibratorSpeed();
        },
        TEMPERATURE(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading temperature from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getTemperature();
        },
        PRESSURE(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading pressure from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getPressure();
        },
        INSERTION_DEPTH(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading insertion depth from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getInsertionDepth();
        },
        ACCELERATION_X(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading acceleration on X axis from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getAccelerometerX();
        },
        ACCELERATION_Y(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading acceleration on Y axis from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getAccelerometerY();
        },
        ACCELERATION_Z(conditionContext: ISequenceStepConditionRunContext): Promise<number> {
            conditionContext.stepContext.sequenceContext.logger.debug('reading acceleration on Z axis from client interface');
            const client = conditionContext.stepContext.sequenceContext.deviceInterface!;
            if (!client.isConnected()) {
                return Promise.reject('not connected');
            }
            return client.getAccelerometerZ();
        },
    };

    public static async evaluate(conditionContext: ISequenceStepConditionRunContext): Promise<any> {
        const logger = conditionContext.stepContext.sequenceContext.logger;
        
        const variable: string = SequenceParameterEvaluator.requireParameter(conditionContext.parameterMap, 'DEVICE_PROPERTY');
        const operator = SequenceParameterEvaluator.requireParameter(conditionContext.parameterMap, 'OPERATOR');
        const argument = SequenceParameterEvaluator.requireParameter(conditionContext.parameterMap, 'ARGUMENT');

        logger.debug('evaluating variable test ' + variable);
        logger.trace('evaluating variable test ' + variable + ' with arguments', [operator, argument]);

        const variableEvaluator = this.variableEvaluators[variable];
        if (!variableEvaluator) {
            // TODO emit warning
            logger.warn('evaluating unknown variable test', variable);
            return false;
        }

        const variableValue = await variableEvaluator(conditionContext);
        logger.debug('variable evaluated to', variableValue);
        logger.trace('evaluating operator on variable');
        const result = SequenceOperatorEvaluator.evaluateOperator(operator, variableValue, argument, conditionContext.stepContext);
        logger.debug('variable test evaluated to', result);

        conditionContext.stepContext.messages.push({ 
            text: variable.toLocaleLowerCase().replace(/_/g, ' ') + ' value was ' + variableValue, 
            level: SequenceStepMessageLevel.DEBUG 
        });

        return result
    }

}