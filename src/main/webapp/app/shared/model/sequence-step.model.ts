import { Moment } from 'moment';
import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceStepGeneration } from 'app/shared/model/enumerations/sequence-step-generation.model';

export interface ISequenceStep {
  id?: number;
  generation?: SequenceStepGeneration;
  ordinal?: number;
  description?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  conditions?: ISequenceStepCondition[];
  actions?: ISequenceStepAction[];
  sequenceName?: string;
  sequenceId?: number;
}

export class SequenceStep implements ISequenceStep {
  constructor(
    public id?: number,
    public generation?: SequenceStepGeneration,
    public ordinal?: number,
    public description?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public conditions?: ISequenceStepCondition[],
    public actions?: ISequenceStepAction[],
    public sequenceName?: string,
    public sequenceId?: number
  ) {}
}
