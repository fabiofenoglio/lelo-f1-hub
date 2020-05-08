import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceStepActionDefinition, SequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from './sequence-step-action-definition.service';
import { SequenceStepActionDefinitionComponent } from './sequence-step-action-definition.component';
import { SequenceStepActionDefinitionDetailComponent } from './sequence-step-action-definition-detail.component';
import { SequenceStepActionDefinitionUpdateComponent } from './sequence-step-action-definition-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceStepActionDefinitionResolve implements Resolve<ISequenceStepActionDefinition> {
  constructor(private service: SequenceStepActionDefinitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceStepActionDefinition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceStepActionDefinition: HttpResponse<SequenceStepActionDefinition>) => {
          if (sequenceStepActionDefinition.body) {
            return of(sequenceStepActionDefinition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceStepActionDefinition());
  }
}

export const sequenceStepActionDefinitionRoute: Routes = [
  {
    path: '',
    component: SequenceStepActionDefinitionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceStepActionDefinitionDetailComponent,
    resolve: {
      sequenceStepActionDefinition: SequenceStepActionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceStepActionDefinitionUpdateComponent,
    resolve: {
      sequenceStepActionDefinition: SequenceStepActionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceStepActionDefinitionUpdateComponent,
    resolve: {
      sequenceStepActionDefinition: SequenceStepActionDefinitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceStepActionDefinition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
