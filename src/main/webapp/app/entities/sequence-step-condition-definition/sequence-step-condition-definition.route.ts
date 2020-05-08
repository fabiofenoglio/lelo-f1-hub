import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  ISequenceStepConditionDefinition,
  SequenceStepConditionDefinition
} from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionDefinitionService } from './sequence-step-condition-definition.service';
import { SequenceStepConditionDefinitionComponent } from './sequence-step-condition-definition.component';
import { SequenceStepConditionDefinitionDetailComponent } from './sequence-step-condition-definition-detail.component';
import { SequenceStepConditionDefinitionUpdateComponent } from './sequence-step-condition-definition-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepConditionDefinitionResolve implements Resolve<ISequenceStepConditionDefinition> {
  constructor(private service: SequenceStepConditionDefinitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepConditionDefinition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepConditionDefinition: HttpResponse<SequenceStepConditionDefinition>) => {
          if (sequenceStepConditionDefinition.body) {
            return of(sequenceStepConditionDefinition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepConditionDefinition());
  }
}

export const sequenceStepConditionDefinitionRoute: Routes = [
  {
    path: '',
    component: SequenceStepConditionDefinitionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepConditionDefinitionDetailComponent,
    resolve: {
      sequenceStepConditionDefinition: SequenceStepConditionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepConditionDefinitionUpdateComponent,
    resolve: {
      sequenceStepConditionDefinition: SequenceStepConditionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepConditionDefinitionUpdateComponent,
    resolve: {
      sequenceStepConditionDefinition: SequenceStepConditionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepConditionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
