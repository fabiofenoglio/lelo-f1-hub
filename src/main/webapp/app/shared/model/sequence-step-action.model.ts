import { Moment } from 'moment';
import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';
import { ISequenceStepActionDefinition } from './sequence-step-action-definition.model';

export interface ISequenceStepAction {
  id?: number;
  generation?: SequenceStepActionGeneration;
  description?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  parameters?: ISequenceStepActionParameter[];
  definitionCode?: string;
  definitionId?: number;
  stepId?: number;
  definition?: ISequenceStepActionDefinition;
  ordinal?: number;
}

export class SequenceStepAction implements ISequenceStepAction {
  constructor(
    public id?: number,
    public generation?: SequenceStepActionGeneration,
    public description?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public parameters?: ISequenceStepActionParameter[],
    public definitionCode?: string,
    public definitionId?: number,
    public stepId?: number,
    public definition?: ISequenceStepActionDefinition,
    public ordinal?: number
  ) {}
}
