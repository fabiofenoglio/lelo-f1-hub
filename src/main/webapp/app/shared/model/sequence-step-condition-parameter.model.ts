import { Moment } from 'moment';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';
import { SequenceVariable } from 'app/shared/model/enumerations/sequence-variable.model';
import { SequenceOperator } from 'app/shared/model/enumerations/sequence-operator.model';
import { ISequenceStepConditionDefinitionParameter } from './sequence-step-condition-definition-parameter.model';

export interface ISequenceStepConditionParameter {
  id?: number;
  generation?: SequenceStepConditionGeneration;
  valueString?: string;
  valueNumber?: number;
  valueBoolean?: boolean;
  valueVariable?: SequenceVariable;
  valueOperator?: SequenceOperator;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  definitionName?: string;
  definitionId?: number;
  conditionId?: number;
  definition?: ISequenceStepConditionDefinitionParameter;
}

export class SequenceStepConditionParameter implements ISequenceStepConditionParameter {
  constructor(
    public id?: number,
    public generation?: SequenceStepConditionGeneration,
    public valueString?: string,
    public valueNumber?: number,
    public valueBoolean?: boolean,
    public valueVariable?: SequenceVariable,
    public valueOperator?: SequenceOperator,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public definitionName?: string,
    public definitionId?: number,
    public conditionId?: number,
    public definition?: ISequenceStepConditionDefinitionParameter
  ) {
    this.valueBoolean = this.valueBoolean || false;
  }
}
