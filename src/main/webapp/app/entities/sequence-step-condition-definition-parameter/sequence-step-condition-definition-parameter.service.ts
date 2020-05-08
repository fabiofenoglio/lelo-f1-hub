import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

type EntityResponseType = HttpResponse<ISequenceStepConditionDefinitionParameter>;
type EntityArrayResponseType = HttpResponse<ISequenceStepConditionDefinitionParameter[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionDefinitionParameterService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-condition-definition-parameters';

  constructor(protected http: HttpClient) {}

  create(sequenceStepConditionDefinitionParameter: ISequenceStepConditionDefinitionParameter): Observable<EntityResponseType> {
    return this.http.post<ISequenceStepConditionDefinitionParameter>(this.resourceUrl, sequenceStepConditionDefinitionParameter, {
      observe: 'response'
    });
  }

  update(sequenceStepConditionDefinitionParameter: ISequenceStepConditionDefinitionParameter): Observable<EntityResponseType> {
    return this.http.put<ISequenceStepConditionDefinitionParameter>(this.resourceUrl, sequenceStepConditionDefinitionParameter, {
      observe: 'response'
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISequenceStepConditionDefinitionParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISequenceStepConditionDefinitionParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
