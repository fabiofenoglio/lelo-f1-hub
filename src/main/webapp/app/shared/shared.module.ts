import { NgModule } from '@angular/core';
import { LeloHubSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { TruncatePipe } from './components/truncate.pipe';
import { RatingComponent } from './components/rating/rating.component';
import { TimeAgoPipe } from './components/time-ago.pipe';
import { RatingModalComponent } from './components/rating/rating-modal/rating-modal.component';


@NgModule({
  imports: [LeloHubSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    TruncatePipe,
    TimeAgoPipe,
    RatingComponent,
    RatingModalComponent
  ],
  entryComponents: [
    LoginModalComponent,
    RatingModalComponent
  ],
  exports: [
    LeloHubSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    TruncatePipe,
    TimeAgoPipe,
    RatingComponent,
    RatingModalComponent
  ]
})
export class LeloHubSharedModule {}
