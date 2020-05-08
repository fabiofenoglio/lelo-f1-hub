import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';

type EntityResponseType = HttpResponse<ISequenceStepAction>;
type EntityArrayResponseType = HttpResponse<ISequenceStepAction[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepActionService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-actions';

  constructor(protected http: HttpClient) {}

  create(sequenceStepAction: ISequenceStepAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepAction);
    return this.http
      .post<ISequenceStepAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequenceStepAction: ISequenceStepAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepAction);
    return this.http
      .put<ISequenceStepAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequenceStepAction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequenceStepAction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sequenceStepAction: ISequenceStepAction): ISequenceStepAction {
    const copy: ISequenceStepAction = Object.assign({}, sequenceStepAction, {
      createdDate: sequenceStepAction.createdDate && sequenceStepAction.createdDate.isValid() ? sequenceStepAction.createdDate.toJSON() : undefined,
      lastModifiedDate:
        sequenceStepAction.lastModifiedDate && sequenceStepAction.lastModifiedDate.isValid() ? sequenceStepAction.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sequenceStepAction: ISequenceStepAction) => {
        sequenceStepAction.createdDate = sequenceStepAction.createdDate ? moment(sequenceStepAction.createdDate) : undefined;
        sequenceStepAction.lastModifiedDate = sequenceStepAction.lastModifiedDate ? moment(sequenceStepAction.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
