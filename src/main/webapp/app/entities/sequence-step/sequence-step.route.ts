import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStep, SequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepService } from './sequence-step.service';
import { SequenceStepComponent } from './sequence-step.component';
import { SequenceStepDetailComponent } from './sequence-step-detail.component';
import { SequenceStepUpdateComponent } from './sequence-step-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepResolve implements Resolve<ISequenceStep> {
  constructor(private service: SequenceStepService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStep: HttpResponse<SequenceStep>) => {
          if (sequenceStep.body) {
            return of(sequenceStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStep());
  }
}

export const sequenceStepRoute: Routes = [
  {
    path: '',
    component: SequenceStepComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepDetailComponent,
    resolve: {
      sequenceStep: SequenceStepResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepUpdateComponent,
    resolve: {
      sequenceStep: SequenceStepResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepUpdateComponent,
    resolve: {
      sequenceStep: SequenceStepResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStep.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
