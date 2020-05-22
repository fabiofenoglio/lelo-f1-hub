import { Moment } from 'moment';

export interface ISequenceUserRating {
  id?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  score?: number;
  userLogin?: string;
  userId?: number;
  sequenceName?: string;
  sequenceId?: number;
}

export class SequenceUserRating implements ISequenceUserRating {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public score?: number,
    public userLogin?: string,
    public userId?: number,
    public sequenceName?: string,
    public sequenceId?: number
  ) {}
}
