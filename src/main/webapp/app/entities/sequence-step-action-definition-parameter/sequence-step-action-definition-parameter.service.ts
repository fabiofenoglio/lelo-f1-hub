import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

type EntityResponseType = HttpResponse<ISequenceStepActionDefinitionParameter>;
type EntityArrayResponseType = HttpResponse<ISequenceStepActionDefinitionParameter[]>;

@Injectable({ providedIn: 'root' })
export class SequenceStepActionDefinitionParameterService {
  public resourceUrl = SERVER_API_URL + 'api/sequence-step-action-definition-parameters';

  constructor(protected http: HttpClient) {}

  create(sequenceStepActionDefinitionParameter: ISequenceStepActionDefinitionParameter): Observable<EntityResponseType> {
    return this.http.post<ISequenceStepActionDefinitionParameter>(this.resourceUrl, sequenceStepActionDefinitionParameter, {
      observe: 'response'
    });
  }

  update(sequenceStepActionDefinitionParameter: ISequenceStepActionDefinitionParameter): Observable<EntityResponseType> {
    return this.http.put<ISequenceStepActionDefinitionParameter>(this.resourceUrl, sequenceStepActionDefinitionParameter, {
      observe: 'response'
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISequenceStepActionDefinitionParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISequenceStepActionDefinitionParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
