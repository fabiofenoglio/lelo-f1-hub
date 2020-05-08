import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';
import { SequenceStepActionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-action-definition-parameter-type.model';
import { SequenceStepActionEvaluationEngine } from 'app/shared/model/enumerations/sequence-step-action-evaluation-engine.model';

export interface ISequenceStepActionDefinitionParameter {
  id?: number;
  generation?: SequenceStepActionGeneration;
  code?: string;
  name?: string;
  description?: string;
  type?: SequenceStepActionDefinitionParameterType;
  evaluationEngine?: SequenceStepActionEvaluationEngine;
  minValue?: number;
  maxValue?: number;
  stepValue?: number;
  minLength?: number;
  maxLength?: number;
  definitionCode?: string;
  definitionId?: number;
}

export class SequenceStepActionDefinitionParameter implements ISequenceStepActionDefinitionParameter {
  constructor(
    public id?: number,
    public generation?: SequenceStepActionGeneration,
    public code?: string,
    public name?: string,
    public description?: string,
    public type?: SequenceStepActionDefinitionParameterType,
    public evaluationEngine?: SequenceStepActionEvaluationEngine,
    public minValue?: number,
    public maxValue?: number,
    public stepValue?: number,
    public minLength?: number,
    public maxLength?: number,
    public definitionCode?: string,
    public definitionId?: number
  ) {}
}
