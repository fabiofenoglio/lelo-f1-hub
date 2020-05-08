import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';

type EntityResponseType = HttpResponse<ISequenceStepActionDefinition>;
type EntityArrayResponseType = HttpResponse<ISequenceStepActionDefinition[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepActionDefinitionService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-action-definitions';

  constructor(protected http: HttpClient) {}

  create(sequenceStepActionDefinition: ISequenceStepActionDefinition): Observable<EntityResponseType> {
    return this.http.post<ISequenceStepActionDefinition>(this.resourceUrl, sequenceStepActionDefinition, { observe: 'response' });
  }

  update(sequenceStepActionDefinition: ISequenceStepActionDefinition): Observable<EntityResponseType> {
    return this.http.put<ISequenceStepActionDefinition>(this.resourceUrl, sequenceStepActionDefinition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISequenceStepActionDefinition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISequenceStepActionDefinition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryOptions(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption({
      page: 0,
      size: 1000,
      sort: ['description'],
      representation: 'options',
      ...req
    });
    return this.http.get<ISequenceStepActionDefinition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
