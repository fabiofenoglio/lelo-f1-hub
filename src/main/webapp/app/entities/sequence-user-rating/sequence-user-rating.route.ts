import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISequenceUserRating, SequenceUserRating } from 'app/shared/model/sequence-user-rating.model';
import { SequenceUserRatingService } from './sequence-user-rating.service';
import { SequenceUserRatingComponent } from './sequence-user-rating.component';
import { SequenceUserRatingDetailComponent } from './sequence-user-rating-detail.component';
import { SequenceUserRatingUpdateComponent } from './sequence-user-rating-update.component';

@Injectable({ providedIn: 'root' })
export class SequenceUserRatingResolve implements Resolve<ISequenceUserRating> {
  constructor(private service: SequenceUserRatingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceUserRating> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sequenceUserRating: HttpResponse<SequenceUserRating>) => {
          if (sequenceUserRating.body) {
            return of(sequenceUserRating.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SequenceUserRating());
  }
}

export const sequenceUserRatingRoute: Routes = [
  {
    path: '',
    component: SequenceUserRatingComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceUserRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SequenceUserRatingDetailComponent,
    resolve: {
      sequenceUserRating: SequenceUserRatingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceUserRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SequenceUserRatingUpdateComponent,
    resolve: {
      sequenceUserRating: SequenceUserRatingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceUserRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SequenceUserRatingUpdateComponent,
    resolve: {
      sequenceUserRating: SequenceUserRatingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'leloHubApp.sequenceUserRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
