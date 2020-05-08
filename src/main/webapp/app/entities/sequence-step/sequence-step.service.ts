import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';

type EntityResponseType = HttpResponse<ISequenceStep>;
type EntityArrayResponseType = HttpResponse<ISequenceStep[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-steps';

  constructor(protected http: HttpClient) {}

  create(sequenceStep: ISequenceStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStep);
    return this.http
      .post<ISequenceStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequenceStep: ISequenceStep): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceStep);
    return this.http
      .put<ISequenceStep>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequenceStep>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequenceStep[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sequenceStep: ISequenceStep): ISequenceStep {
    const copy: ISequenceStep = Object.assign({}, sequenceStep, {
      createdDate: sequenceStep.createdDate && sequenceStep.createdDate.isValid() ? sequenceStep.createdDate.toJSON() : undefined,
      lastModifiedDate: sequenceStep.lastModifiedDate && sequenceStep.lastModifiedDate.isValid() ? sequenceStep.lastModifiedDate.toJSON() : undefined
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
      res.body.forEach((sequenceStep: ISequenceStep) => {
        sequenceStep.createdDate = sequenceStep.createdDate ? moment(sequenceStep.createdDate) : undefined;
        sequenceStep.lastModifiedDate = sequenceStep.lastModifiedDate ? moment(sequenceStep.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
