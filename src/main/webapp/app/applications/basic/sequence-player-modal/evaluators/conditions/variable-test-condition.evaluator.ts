import { ISequenceStepConditionRunContext } from '../../sequence-player.model';


export abstract class SequenceVariableTestConditionEvaluator {
    
    public static evaluate(conditionContext: ISequenceStepConditionRunContext): Promise<boolean> {

        return Promise.resolve(!conditionContext);
    }

}