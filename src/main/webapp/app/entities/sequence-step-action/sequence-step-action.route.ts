import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStepAction, SequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceStepActionService } from './sequence-step-action.service';
import { SequenceStepActionComponent } from './sequence-step-action.component';
import { SequenceStepActionDetailComponent } from './sequence-step-action-detail.component';
import { SequenceStepActionUpdateComponent } from './sequence-step-action-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepActionResolve implements Resolve<ISequenceStepAction> {
  constructor(private service: SequenceStepActionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepAction: HttpResponse<SequenceStepAction>) => {
          if (sequenceStepAction.body) {
            return of(sequenceStepAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepAction());
  }
}

export const sequenceStepActionRoute: Routes = [
  {
    path: '',
    component: SequenceStepActionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepAction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepActionDetailComponent,
    resolve: {
      sequenceStepAction: SequenceStepActionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepAction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepActionUpdateComponent,
    resolve: {
      sequenceStepAction: SequenceStepActionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepAction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepActionUpdateComponent,
    resolve: {
      sequenceStepAction: SequenceStepActionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepAction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
