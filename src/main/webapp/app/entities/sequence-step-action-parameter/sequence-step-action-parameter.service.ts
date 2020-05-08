import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

type EntityResponseType = HttpResponse<ISequenceStepActionParameter>;
type EntityArrayResponseType = HttpResponse<ISequenceStepActionParameter[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepActionParameterService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-action-parameters';

  constructor(protected http: HttpClient) {}

  create(sequenceStepActionParameter: ISequenceStepActionParameter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepActionParameter);
    return this.http
      .post<ISequenceStepActionParameter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequenceStepActionParameter: ISequenceStepActionParameter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStepActionParameter);
    return this.http
      .put<ISequenceStepActionParameter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequenceStepActionParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequenceStepActionParameter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sequenceStepActionParameter: ISequenceStepActionParameter): ISequenceStepActionParameter {
    const copy: ISequenceStepActionParameter = Object.assign({}, sequenceStepActionParameter, {
      createdDate:
        sequenceStepActionParameter.createdDate && sequenceStepActionParameter.createdDate.isValid()
          ? sequenceStepActionParameter.createdDate.toJSON()
          : undefined,
      lastModifiedDate:
        sequenceStepActionParameter.lastModifiedDate && sequenceStepActionParameter.lastModifiedDate.isValid()
          ? sequenceStepActionParameter.lastModifiedDate.toJSON()
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
      res.body.forEach((sequenceStepActionParameter: ISequenceStepActionParameter) => {
        sequenceStepActionParameter.createdDate = sequenceStepActionParameter.createdDate
          ? moment(sequenceStepActionParameter.createdDate)
          : undefined;
        sequenceStepActionParameter.lastModifiedDate = sequenceStepActionParameter.lastModifiedDate
          ? moment(sequenceStepActionParameter.lastModifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
