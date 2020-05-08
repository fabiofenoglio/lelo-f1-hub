import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStepConditionParameter, SequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { SequenceStepConditionParameterService } from './sequence-step-condition-parameter.service';
import { SequenceStepConditionParameterComponent } from './sequence-step-condition-parameter.component';
import { SequenceStepConditionParameterDetailComponent } from './sequence-step-condition-parameter-detail.component';
import { SequenceStepConditionParameterUpdateComponent } from './sequence-step-condition-parameter-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionParameterResolve implements Resolve<ISequenceStepConditionParameter> {
  constructor(private service: SequenceStepConditionParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepConditionParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepConditionParameter: HttpResponse<SequenceStepConditionParameter>) => {
          if (sequenceStepConditionParameter.body) {
            return of(sequenceStepConditionParameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepConditionParameter());
  }
}

export const sequenceStepConditionParameterRoute: Routes = [
  {
    path: '',
    component: SequenceStepConditionParameterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepConditionParameterDetailComponent,
    resolve: {
      sequenceStepConditionParameter: SequenceStepConditionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepConditionParameterUpdateComponent,
    resolve: {
      sequenceStepConditionParameter: SequenceStepConditionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepConditionParameterUpdateComponent,
    resolve: {
      sequenceStepConditionParameter: SequenceStepConditionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
