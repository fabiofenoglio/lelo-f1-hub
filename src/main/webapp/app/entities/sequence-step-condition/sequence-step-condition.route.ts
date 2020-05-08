import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStepCondition, SequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { SequenceStepConditionService } from './sequence-step-condition.service';
import { SequenceStepConditionComponent } from './sequence-step-condition.component';
import { SequenceStepConditionDetailComponent } from './sequence-step-condition-detail.component';
import { SequenceStepConditionUpdateComponent } from './sequence-step-condition-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionResolve implements Resolve<ISequenceStepCondition> {
  constructor(private service: SequenceStepConditionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepCondition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepCondition: HttpResponse<SequenceStepCondition>) => {
          if (sequenceStepCondition.body) {
            return of(sequenceStepCondition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepCondition());
  }
}

export const sequenceStepConditionRoute: Routes = [
  {
    path: '',
    component: SequenceStepConditionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepCondition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepConditionDetailComponent,
    resolve: {
      sequenceStepCondition: SequenceStepConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepCondition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepConditionUpdateComponent,
    resolve: {
      sequenceStepCondition: SequenceStepConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepCondition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepConditionUpdateComponent,
    resolve: {
      sequenceStepCondition: SequenceStepConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepCondition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
