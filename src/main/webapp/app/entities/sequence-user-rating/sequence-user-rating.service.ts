import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceUserRating } from 'app/shared/model/sequence-user-rating.model';

type EntityResponseType = HttpResponse<ISequenceUserRating>;
type EntityArrayResponseType = HttpResponse<ISequenceUserRating[]>;

@Injectable({ providedIn: 'root' })
export class SequenceUserRatingService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-user-ratings';

  constructor(protected http: HttpClient) {}

  vote(sequenceId: number, vote: number): Observable<EntityResponseType> {
    const copy = {
      sequenceId,
      score: vote
    };
    return this.http
      .post<ISequenceUserRating>(this.resourceUrl + '/vote', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  unvote(sequenceId: number): Observable<void> {
    const copy = {
      sequenceId
    };
    return this.http
      .post<void>(this.resourceUrl + '/unvote', copy, { observe: 'response' })
      .pipe(map(() => {}));
  }

  create(sequenceUserRating: ISequenceUserRating): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceUserRating);
    return this.http
      .post<ISequenceUserRating>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sequenceUserRating: ISequenceUserRating): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenceUserRating);
    return this.http
      .put<ISequenceUserRating>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISequenceUserRating>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISequenceUserRating[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sequenceUserRating: ISequenceUserRating): ISequenceUserRating {
    const copy: ISequenceUserRating = Object.assign({}, sequenceUserRating, {
      createdDate:
        sequenceUserRating.createdDate && sequenceUserRating.createdDate.isValid() ? sequenceUserRating.createdDate.toJSON() : undefined,
      lastModifiedDate:
        sequenceUserRating.lastModifiedDate && sequenceUserRating.lastModifiedDate.isValid()
          ? sequenceUserRating.lastModifiedDate.toJSON()
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
      res.body.forEach((sequenceUserRating: ISequenceUserRating) => {
        sequenceUserRating.createdDate = sequenceUserRating.createdDate ? moment(sequenceUserRating.createdDate) : undefined;
        sequenceUserRating.lastModifiedDate = sequenceUserRating.lastModifiedDate ? moment(sequenceUserRating.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
