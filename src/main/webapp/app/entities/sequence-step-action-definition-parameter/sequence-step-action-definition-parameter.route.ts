import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  ISequenceStepActionDefinitionParameter,
  SequenceStepActionDefinitionParameter
} from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionDefinitionParameterService } from './sequence-step-action-definition-parameter.service';
import { SequenceStepActionDefinitionParameterComponent } from './sequence-step-action-definition-parameter.component';
import { SequenceStepActionDefinitionParameterDetailComponent } from './sequence-step-action-definition-parameter-detail.component';
import { SequenceStepActionDefinitionParameterUpdateComponent } from './sequence-step-action-definition-parameter-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepActionDefinitionParameterResolve implements Resolve<ISequenceStepActionDefinitionParameter> {
  constructor(private service: SequenceStepActionDefinitionParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepActionDefinitionParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepActionDefinitionParameter: HttpResponse<SequenceStepActionDefinitionParameter>) => {
          if (sequenceStepActionDefinitionParameter.body) {
            return of(sequenceStepActionDefinitionParameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepActionDefinitionParameter());
  }
}

export const sequenceStepActionDefinitionParameterRoute: Routes = [
  {
    path: '',
    component: SequenceStepActionDefinitionParameterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepActionDefinitionParameterDetailComponent,
    resolve: {
      sequenceStepActionDefinitionParameter: SequenceStepActionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepActionDefinitionParameterUpdateComponent,
    resolve: {
      sequenceStepActionDefinitionParameter: SequenceStepActionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepActionDefinitionParameterUpdateComponent,
    resolve: {
      sequenceStepActionDefinitionParameter: SequenceStepActionDefinitionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinitionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
