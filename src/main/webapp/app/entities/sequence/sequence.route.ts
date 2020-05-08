import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequence, Sequence } from 'app/shared/model/sequence.model';
import { SequenceService } from './sequence.service';
import { SequenceComponent } from './sequence.component';
import { SequenceUpdateComponent } from './sequence-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceResolve implements Resolve<ISequence> {
  constructor(private service: SequenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequence: HttpResponse<Sequence>) => {
          if (sequence.body) {
            return of(sequence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sequence());
  }
}

export const sequenceRoute: Routes = [
  {
    path: '',
    component: SequenceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceUpdateComponent,
    resolve: {
      sequence: SequenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequence.home.title',
      readOnly: true
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceUpdateComponent,
    resolve: {
      sequence: SequenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceUpdateComponent,
    resolve: {
      sequence: SequenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequence.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
