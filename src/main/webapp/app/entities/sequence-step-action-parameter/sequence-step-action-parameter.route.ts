import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStepActionParameter, SequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { SequenceStepActionParameterService } from './sequence-step-action-parameter.service';
import { SequenceStepActionParameterComponent } from './sequence-step-action-parameter.component';
import { SequenceStepActionParameterDetailComponent } from './sequence-step-action-parameter-detail.component';
import { SequenceStepActionParameterUpdateComponent } from './sequence-step-action-parameter-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepActionParameterResolve implements Resolve<ISequenceStepActionParameter> {
  constructor(private service: SequenceStepActionParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepActionParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepActionParameter: HttpResponse<SequenceStepActionParameter>) => {
          if (sequenceStepActionParameter.body) {
            return of(sequenceStepActionParameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepActionParameter());
  }
}

export const sequenceStepActionParameterRoute: Routes = [
  {
    path: '',
    component: SequenceStepActionParameterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepActionParameterDetailComponent,
    resolve: {
      sequenceStepActionParameter: SequenceStepActionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepActionParameterUpdateComponent,
    resolve: {
      sequenceStepActionParameter: SequenceStepActionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepActionParameterUpdateComponent,
    resolve: {
      sequenceStepActionParameter: SequenceStepActionParameterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
