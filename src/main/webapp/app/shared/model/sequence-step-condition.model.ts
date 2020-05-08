import { Moment } from 'moment';
import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';
import { ISequenceStepConditionDefinition } from './sequence-step-condition-definition.model';

export interface ISequenceStepCondition {
  id?: number;
  generation?: SequenceStepConditionGeneration;
  description?: string;
  negate?: boolean;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  parameters?: ISequenceStepConditionParameter[];
  andConditions?: ISequenceStepCondition[];
  orConditions?: ISequenceStepCondition[];
  definitionCode?: string;
  definitionId?: number;
  stepId?: number;
  otherAndConditionId?: number;
  otherOrConditionId?: number;
  definition?: ISequenceStepConditionDefinition;
}

export class SequenceStepCondition implements ISequenceStepCondition {
  constructor(
    public id?: number,
    public generation?: SequenceStepConditionGeneration,
    public description?: string,
    public negate?: boolean,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public parameters?: ISequenceStepConditionParameter[],
    public andConditions?: ISequenceStepCondition[],
    public orConditions?: ISequenceStepCondition[],
    public definitionCode?: string,
    public definitionId?: number,
    public stepId?: number,
    public otherAndConditionId?: number,
    public otherOrConditionId?: number,
    public definition?: ISequenceStepConditionDefinition
  ) {
    this.negate = this.negate || false;
  }
}
