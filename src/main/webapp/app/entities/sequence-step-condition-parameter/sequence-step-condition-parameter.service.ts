import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';

type EntityResponseType = HttpResponse<ISequenceStepConditionParameter>;
type EntityArrayResponseType = HttpResponse<ISequenceStepConditionParameter[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionParameterService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-condition-parameters';

  constructor(protected http: HttpClient) {}

  create(sequenceStepConditionParameter: ISequenceStepConditionParameter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepConditionParameter);
    return this.http
      .post<ISequenceStepConditionParameter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequenceStepConditionParameter: ISequenceStepConditionParameter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepConditionParameter);
    return this.http
      .put<ISequenceStepConditionParameter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequenceStepConditionParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequenceStepConditionParameter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sequenceStepConditionParameter: ISequenceStepConditionParameter): ISequenceStepConditionParameter {
    const copy: ISequenceStepConditionParameter = Object.assign({}, sequenceStepConditionParameter, {
      createdDate:
        sequenceStepConditionParameter.createdDate && sequenceStepConditionParameter.createdDate.isValid()
          ? sequenceStepConditionParameter.createdDate.toJSON()
          : undefined,
      lastModifiedDate:
        sequenceStepConditionParameter.lastModifiedDate && sequenceStepConditionParameter.lastModifiedDate.isValid()
          ? sequenceStepConditionParameter.lastModifiedDate.toJSON()
          : undefined
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
      res.body.forEach((sequenceStepConditionParameter: ISequenceStepConditionParameter) => {
        sequenceStepConditionParameter.createdDate = sequenceStepConditionParameter.createdDate
          ? moment(sequenceStepConditionParameter.createdDate)
          : undefined;
        sequenceStepConditionParameter.lastModifiedDate = sequenceStepConditionParameter.lastModifiedDate
          ? moment(sequenceStepConditionParameter.lastModifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
