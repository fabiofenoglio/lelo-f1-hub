import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LeloHubSharedModule } from 'app/shared/shared.module';
import { LeloHubCoreModule } from 'app/core/core.module';
import { LeloHubAppRoutingModule } from './app-routing.module';
import { LeloHubHomeModule } from './home/home.module';
import { LeloHubEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { LeloHubApplicationsModule } from './applications/applications.module';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';


@NgModule({
  imports: [
    BrowserModule,
    LeloHubSharedModule,
    LeloHubCoreModule,
    LeloHubHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LeloHubEntityModule,
    LeloHubApplicationsModule,
    LeloHubAppRoutingModule,
    LoggerModule.forRoot({level: NgxLoggerLevel.DEBUG, })
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class LeloHubAppModule {}
