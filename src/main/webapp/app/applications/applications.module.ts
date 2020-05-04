import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'applications/basic',
        loadChildren: () => import('./basic/application-basic.module').then(m => m.LeloHubApplicationBasicModule)
      },
    ])
  ]
})
export class LeloHubApplicationsModule {}
