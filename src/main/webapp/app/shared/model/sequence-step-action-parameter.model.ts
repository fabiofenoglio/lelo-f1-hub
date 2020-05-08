import { Moment } from 'moment';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';
import { SequenceVariable } from 'app/shared/model/enumerations/sequence-variable.model';
import { ISequenceStepActionDefinitionParameter } from './sequence-step-action-definition-parameter.model';

export interface ISequenceStepActionParameter {
  id?: number;
  generation?: SequenceStepActionGeneration;
  valueString?: string;
  valueNumber?: number;
  valueBoolean?: boolean;
  valueVariable?: SequenceVariable;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  definitionName?: string;
  definitionId?: number;
  actionId?: number;
  definition?: ISequenceStepActionDefinitionParameter;
}

export class SequenceStepActionParameter implements ISequenceStepActionParameter {
  constructor(
    public id?: number,
    public generation?: SequenceStepActionGeneration,
    public valueString?: string,
    public valueNumber?: number,
    public valueBoolean?: boolean,
    public valueVariable?: SequenceVariable,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public definitionName?: string,
    public definitionId?: number,
    public actionId?: number,
    public definition?: ISequenceStepActionDefinitionParameter
  ) {
    this.valueBoolean = this.valueBoolean || false;
  }
}
