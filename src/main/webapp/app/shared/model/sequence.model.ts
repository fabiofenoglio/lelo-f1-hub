import { Moment } from 'moment';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceGeneration } from 'app/shared/model/enumerations/sequence-generation.model';
import { SequenceVisibility } from 'app/shared/model/enumerations/sequence-visibility.model';

export interface ISequence {
  id?: number;
  generation?: SequenceGeneration;
  name?: string;
  description?: string;
  visibility?: SequenceVisibility;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  steps?: ISequenceStep[];
  userLogin?: string;
  userId?: number;
  authorizations?: {[authorization: string] : boolean};
  ratingNumber?: number;
  averageRating?: number;
}

export class Sequence implements ISequence {
  constructor(
    public id?: number,
    public generation?: SequenceGeneration,
    public name?: string,
    public description?: string,
    public visibility?: SequenceVisibility,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public steps?: ISequenceStep[],
    public userLogin?: string,
    public userId?: number,
    public ratingNumber?: number,
    public averageRating?: number,
  ) {}
}
