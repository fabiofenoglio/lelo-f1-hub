import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { ApplicationBasicRoute } from './application-basic.route';
import { ApplicationBasicComponent } from './application-basic.component';
import { LeloF1SDKService } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.service';


@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild([...ApplicationBasicRoute])],
  declarations: [
    ApplicationBasicComponent
  ],
  entryComponents: [
    ApplicationBasicComponent
  ],
  providers: [
    LeloF1SDKService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeloHubApplicationBasicModule {}
