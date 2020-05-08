import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';
import { SequenceStepConditionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-condition-definition-parameter-type.model';
import { SequenceStepConditionEvaluationEngine } from 'app/shared/model/enumerations/sequence-step-condition-evaluation-engine.model';

export interface ISequenceStepConditionDefinitionParameter {
  id?: number;
  generation?: SequenceStepConditionGeneration;
  code?: string;
  name?: string;
  description?: string;
  type?: SequenceStepConditionDefinitionParameterType;
  evaluationEngine?: SequenceStepConditionEvaluationEngine;
  minValue?: number;
  maxValue?: number;
  stepValue?: number;
  minLength?: number;
  maxLength?: number;
  definitionCode?: string;
  definitionId?: number;
}

export class SequenceStepConditionDefinitionParameter implements ISequenceStepConditionDefinitionParameter {
  constructor(
    public id?: number,
    public generation?: SequenceStepConditionGeneration,
    public code?: string,
    public name?: string,
    public description?: string,
    public type?: SequenceStepConditionDefinitionParameterType,
    public evaluationEngine?: SequenceStepConditionEvaluationEngine,
    public minValue?: number,
    public maxValue?: number,
    public stepValue?: number,
    public minLength?: number,
    public maxLength?: number,
    public definitionCode?: string,
    public definitionId?: number
  ) {}
}
