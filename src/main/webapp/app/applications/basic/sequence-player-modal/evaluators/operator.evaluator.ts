import { ISequenceStepRunContext } from '../sequence-player.model';
import { SequenceOperator } from 'app/shared/model/enumerations/sequence-operator.model';

export abstract class SequenceOperatorEvaluator {
    
    private static operatorEvaluators: {
        [definition: string] : (value1: any, value2: any, stepContext: ISequenceStepRunContext) => Promise<boolean>
    } = {
        // eslint-disable-next-line eqeqeq
        'EQUALS': (v1, v2) => Promise.resolve(v1 == v2),
        // eslint-disable-next-line eqeqeq
        'DIFFERS': (v1, v2) => Promise.resolve(v1 != v2),
        'GREATER_THAN': (v1, v2) => Promise.resolve(v1 > v2),
        'GREATER_THAN_OR_EQUAL_TO': (v1, v2) => Promise.resolve(v1 >= v2),
        'LESS_THAN': (v1, v2) => Promise.resolve(v1 < v2),
        'LESS_THAN_OR_EQUAL_TO': (v1, v2) => Promise.resolve(v1 <= v2),
        'DEFINED': value => Promise.resolve(!(value === null || typeof value === 'undefined')),
        'NOT_DEFINED': value => Promise.resolve(value === null || typeof value === 'undefined'),
        'ALWAYS': () => Promise.resolve(true),
        'NEVER': () => Promise.resolve(false),
    };

    static async evaluateOperator(operator: SequenceOperator, param1: any, param2: any, stepContext: ISequenceStepRunContext): Promise<boolean> {
        const logger = stepContext.sequenceContext.logger;
        logger.debug('evaluating operator ' + operator);
        logger.trace('evaluating operator ' + operator + ' with params', [param1, param2]);

        if (! operator) {
            // TODO emit warning
            logger.warn('evaluating operator not defined', operator);
            return false;
        }

        const evaluator = this.operatorEvaluators[operator];
        if (!evaluator) {
            // TODO emit warning
            logger.warn('evaluating unknown operator', operator);
            return false;
        }

        const result = await evaluator(param1, param2, stepContext);
        logger.debug('operator evaluated to', result);

        return result;
    }

}