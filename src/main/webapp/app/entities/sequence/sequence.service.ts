import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequence } from 'app/shared/model/sequence.model';

type EntityResponseType = HttpResponse<ISequence>;
type EntityArrayResponseType = HttpResponse<ISequence[]>;

@Injectable({ providedIn: 'root' })
export class SequenceService {
  public resourceUrl = SERVER_API_URL + 'api/sequences';

  constructor(protected http: HttpClient) {}

  create(sequence: ISequence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequence);
    return this.http
      .post<ISequence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequence: ISequence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequence);
    return this.http
      .put<ISequence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any, headers = {}): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequence[]>(this.resourceUrl, { params: options, observe: 'response', headers })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  clone(id: number, payload: ISequence | null = null): Observable<EntityResponseType> {
    return this.http
      .post<ISequence>(`${this.resourceUrl}/${id}/clone`, payload ? payload : {}, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  protected convertDateFromClient(sequence: ISequence): ISequence {
    const copy: ISequence = Object.assign({}, sequence, {
      createdDate: sequence.createdDate && sequence.createdDate.isValid() ? sequence.createdDate.toJSON() : undefined,
      lastModifiedDate: sequence.lastModifiedDate && sequence.lastModifiedDate.isValid() ? sequence.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
      if (res.body.steps) {
        res.body.steps.sort((s1, s2) => {
          return (s1.ordinal || 0) - (s2.ordinal || 0);
        });
        res.body.steps.forEach(step => {
          if (step.actions) {
            step.actions.sort((s1, s2) => {
              return (s1.ordinal || 0) - (s2.ordinal || 0);
            });
          }
        });
      }
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sequence: ISequence) => {
        sequence.createdDate = sequence.createdDate ? moment(sequence.createdDate) : undefined;
        sequence.lastModifiedDate = sequence.lastModifiedDate ? moment(sequence.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
