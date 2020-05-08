import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';

export interface ISequenceStepConditionDefinition {
  id?: number;
  generation?: SequenceStepConditionGeneration;
  code?: string;
  description?: string;
  parameters?: ISequenceStepConditionDefinitionParameter[];
}

export class SequenceStepConditionDefinition implements ISequenceStepConditionDefinition {
  constructor(
    public id?: number,
    public generation?: SequenceStepConditionGeneration,
    public code?: string,
    public description?: string,
    public parameters?: ISequenceStepConditionDefinitionParameter[]
  ) {}
}
