import { ISequenceStepConditionRunContext, ISequenceStepRunContext, SequenceStepMessageLevel } from '../sequence-player.model';
import { SequenceParameterEvaluator } from './parameter.evaluator';
import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { SequenceVariableTestConditionEvaluator } from './conditions/variable-test-condition.evaluator';
import { SequenceDeviceTestConditionEvaluator } from './conditions/device-test-condition.evaluator';

export abstract class SequenceConditionEvaluator {
    
    private static evaluators: {
        [definition: string] : (conditionContext: ISequenceStepConditionRunContext) => Promise<boolean>
    } = {
        'ALWAYS': () => Promise.resolve(true),
        'NEVER': () => Promise.resolve(false),
        'RANDOM': (conditionContext: ISequenceStepConditionRunContext) => Promise.resolve(
                (Math.random() * 100.0) <=  (SequenceParameterEvaluator.requireParameter(conditionContext.parameterMap, 'CHANCE')) ),
        'VARIABLE_TEST': 
            (conditionContext: ISequenceStepConditionRunContext) => SequenceVariableTestConditionEvaluator.evaluate(conditionContext),
        'DEVICE_TEST': 
            (conditionContext: ISequenceStepConditionRunContext) => SequenceDeviceTestConditionEvaluator.evaluate(conditionContext),
    };

    static buildContext(condition: ISequenceStepCondition, stepContext: ISequenceStepRunContext): ISequenceStepConditionRunContext {
        const conditionContext: ISequenceStepConditionRunContext = {
            stepContext,
            condition,
            parameterMap: {}
        };
        conditionContext.parameterMap = SequenceParameterEvaluator.extractParameterMap(conditionContext);
        return conditionContext;
    }

    static async evaluateCondition(conditionContext: ISequenceStepConditionRunContext): Promise<boolean> {
        const logger = conditionContext.stepContext.sequenceContext.logger;
        logger.debug('evaluating condition ' + conditionContext.condition.definitionCode);

        if (! conditionContext.condition.definitionCode) {
            // TODO emit warning
            logger.warn('evaluating condition with missing definition', conditionContext.condition.id);
            return false;
        }

        const evaluator = this.evaluators[conditionContext.condition.definitionCode];
        if (!evaluator) {
            // TODO emit warning
            logger.warn('evaluating unknown condition', conditionContext.condition.definitionCode);
            return false;
        }

        
        let result;
        
        try {
            result = await evaluator(conditionContext);
        } catch(err) {
            logger.error('error evaluating condition:', err);
            conditionContext.stepContext.messages.push({ text: 'error evaluating condition: ' + err, level: SequenceStepMessageLevel.ERROR });
            result = false;
        }

        logger.debug('condition until now evaluated to', result);

        // check if AND conditions
        if (conditionContext.condition.andConditions?.length) {
            logger.debug('condition has other conditions in AND that will be evaluated now');
            for (const otherAndCondition of conditionContext.condition.andConditions) {
                if (!await this.evaluateCondition(this.buildContext(otherAndCondition, conditionContext.stepContext))) {
                    logger.debug('other AND condition evaluated negative');
                    result = false;
                    break;
                } else {
                    logger.debug('other AND condition evaluated positive, continuing');
                }
            }
        }

        if (result) {
            logger.debug('condition passed all AND tests, skipping ORs check');

        } else {
            logger.debug('condition evaluated negative for AND tests, proceeding with ORs check');
                
            // check if OR conditions
            if (conditionContext.condition.orConditions?.length) {
                logger.debug('condition has other conditions in OR that will be evaluated now');
                for (const otherOrCondition of conditionContext.condition.orConditions) {
                    if (await this.evaluateCondition(this.buildContext(otherOrCondition, conditionContext.stepContext))) {
                        logger.debug('other OR condition evaluated positive, skipping other OR checks');
                        result = true;
                        break;
                    } else {
                        logger.debug('other OR condition evaluated negative');
                    }
                }
            }
        }

        logger.debug('condition finally evaluated to', result);
        return result;
    }

}