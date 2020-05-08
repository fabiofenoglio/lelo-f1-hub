import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';

export interface ISequenceStepActionDefinition {
  id?: number;
  generation?: SequenceStepActionGeneration;
  code?: string;
  description?: string;
  parameters?: ISequenceStepActionDefinitionParameter[];
}

export class SequenceStepActionDefinition implements ISequenceStepActionDefinition {
  constructor(
    public id?: number,
    public generation?: SequenceStepActionGeneration,
    public code?: string,
    public description?: string,
    public parameters?: ISequenceStepActionDefinitionParameter[]
  ) {}
}
