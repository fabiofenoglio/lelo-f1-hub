import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  ISequenceStepConditionDefinitionParameter,
  SequenceStepConditionDefinitionParameter
} from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionDefinitionParameterService } from './sequence-step-condition-definition-parameter.service';
import { SequenceStepConditionDefinitionParameterComponent } from './sequence-step-condition-definition-parameter.component';
import { SequenceStepConditionDefinitionParameterDetailComponent } from './sequence-step-condition-definition-parameter-detail.component';
import { SequenceStepConditionDefinitionParameterUpdateComponent } from './sequence-step-condition-definition-parameter-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionDefinitionParameterResolve implements Resolve<ISequenceStepConditionDefinitionParameter> {
  constructor(private service: SequenceStepConditionDefinitionParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepConditionDefinitionParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepConditionDefinitionParameter: HttpResponse<SequenceStepConditionDefinitionParameter>) => {
          if (sequenceStepConditionDefinitionParameter.body) {
            return of(sequenceStepConditionDefinitionParameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepConditionDefinitionParameter());
  }
}

export const sequenceStepConditionDefinitionParameterRoute: Routes = [
  {
    path: '',
    component: SequenceStepConditionDefinitionParameterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepConditionDefinitionParameterDetailComponent,
    resolve: {
      sequenceStepConditionDefinitionParameter: SequenceStepConditionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepConditionDefinitionParameterUpdateComponent,
    resolve: {
      sequenceStepConditionDefinitionParameter: SequenceStepConditionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepConditionDefinitionParameterUpdateComponent,
    resolve: {
      sequenceStepConditionDefinitionParameter: SequenceStepConditionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
