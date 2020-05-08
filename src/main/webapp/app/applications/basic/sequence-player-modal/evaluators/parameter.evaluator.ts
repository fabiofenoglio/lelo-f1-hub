import { ISequenceParameterMap, ISequenceStepConditionRunContext, ISequenceStepActionRunContext } from '../sequence-player.model';
import { SequenceStepConditionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-condition-definition-parameter-type.model';
import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

export abstract class SequenceParameterEvaluator {
    
    public static extractParameterMap(conditionContext: ISequenceStepConditionRunContext | ISequenceStepActionRunContext): ISequenceParameterMap {
        const source = (conditionContext as any).condition ? 
            (conditionContext as ISequenceStepConditionRunContext).condition : 
            (conditionContext as ISequenceStepActionRunContext).action;

        const logger = conditionContext.stepContext.sequenceContext.logger;
        logger.debug('extracting parameter map from condition ' + source.definitionCode);

        const output = {};
        (source.parameters || []).forEach((parameter: ISequenceStepConditionParameter | ISequenceStepActionParameter) => {
            const k = parameter.definition!.code;
            let v;
            logger.trace('evaluating parameter ' + k + ' of type ' + parameter.definition?.type);

            switch (parameter.definition?.type) {
                case SequenceStepConditionDefinitionParameterType.NUMBER:
                    v = parameter.valueNumber;
                    break;
                case SequenceStepConditionDefinitionParameterType.STRING:
                    v = parameter.valueString;
                    break;
                case SequenceStepConditionDefinitionParameterType.BOOLEAN:
                    v = parameter.valueBoolean;
                    break;
                case SequenceStepConditionDefinitionParameterType.OPERATOR:
                    v = (parameter as ISequenceStepConditionParameter).valueOperator;
                    break;
                case SequenceStepConditionDefinitionParameterType.VARIABLE:
                    v = parameter.valueVariable;
                    break;
            }

            logger.trace('found parameter ' + k + ' of type ' + parameter.definition?.type + ' with value', v);

            if (!k) {
                // TODO emit warning
                logger.warn('parameter has no key: ' + k);
                output['_#' + parameter.id] = v;
            } else {
                output[k] = v;
            }
        });

        logger.trace('completed parameter map evaluation', output);
        return output;
    }

    public static requireParameter(parameterMap: ISequenceParameterMap, key: string): any {
        const v = parameterMap[key];
        if (v === null || typeof v === 'undefined') {
            // TODO emit warning
            throw Error('Missing required parameter ' + key);
        }
        return v;
    }

    public static randomInterval(min: number, max: number): number { // min and max included 
        return (Math.random() * (max - min) + min);
    }

    public static buildRamp(
        min: number, max: number, time: number, minInterval: number
    ): {time: number, value: number}[] {
        if (min > max) {
            const reversed = this.buildRamp(max, min, time, minInterval);
            // invert time
            const times = reversed.map(o => o.time);
            reversed.reverse();
            for (let i = 0; i < reversed.length; i ++) {
                reversed[i].time = times[i];
            }

            return reversed;
        }
        if (time < 1000) {
            throw Error('Transition time should be at least 1000 ms');
        }

        const msPerIncrement = time / (max - min);

        let markTime = 0;
        let markValue = min;
        const steps: {time: number, value: number}[] = [];
        let last = true;

        if (msPerIncrement >= minInterval) {
            // use msPerIncrement as step
            do {
                steps.push({
                    value: markValue,
                    time: markTime < time ? markTime : time
                });
                markTime += msPerIncrement;
                markValue += 1;
                if (markValue > max && last) {
                    markValue = max;
                    last = false;
                } else if (!last) {
                break;
                }
            } while (markValue <= max);
        } else {
            // use minInterval as step
            const incrementPerMinInterval = (max - min) * minInterval / time;
            do {
                if (!(steps.length && steps[steps.length - 1].value === Math.round(markValue))) {       
                    steps.push({
                        value: Math.round(markValue),
                        time: markTime < time ? markTime : time
                    });
                }
                markTime += minInterval;
                markValue += incrementPerMinInterval;
                if (markValue > max && last) {
                    markValue = max;
                    last = false;
                } else if (!last) {
                break;
                }
            } while (markValue <= max);
        }
        return steps;
    }

}