import { Routes } from '@angular/router';
import { ApplicationBasicComponent } from './application-basic.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const ApplicationBasicRoute: Routes = [
  {
    path: '',
    component: ApplicationBasicComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'global.menu.applications.basic'
    },
    canActivate: [UserRouteAccessService]
  }
];