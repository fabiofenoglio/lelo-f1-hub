import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';

type EntityResponseType = HttpResponse<ISequenceStepConditionDefinition>;
type EntityArrayResponseType = HttpResponse<ISequenceStepConditionDefinition[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionDefinitionService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-condition-definitions';

  constructor(protected http: HttpClient) {}

  create(sequenceStepConditionDefinition: ISequenceStepConditionDefinition): Observable<EntityResponseType> {
    return this.http.post<ISequenceStepConditionDefinition>(this.resourceUrl, sequenceStepConditionDefinition, { observe: 'response' });
  }

  update(sequenceStepConditionDefinition: ISequenceStepConditionDefinition): Observable<EntityResponseType> {
    return this.http.put<ISequenceStepConditionDefinition>(this.resourceUrl, sequenceStepConditionDefinition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISequenceStepConditionDefinition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISequenceStepConditionDefinition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryOptions(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption({
      page: 0,
      size: 1000,
      sort: ['description'],
      representation: 'options',
      ...req
    });
    return this.http.get<ISequenceStepConditionDefinition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
