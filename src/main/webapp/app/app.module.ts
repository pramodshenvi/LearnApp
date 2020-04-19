import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LearnAppSharedModule } from 'app/shared/shared.module';
import { LearnAppCoreModule } from 'app/core/core.module';
import { LearnAppAppRoutingModule } from './app-routing.module';
import { LearnAppHomeModule } from './home/home.module';
import { LearnAppEntityModule } from './entities/entity.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LearnAppSharedModule,
    LearnAppCoreModule,
    LearnAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LearnAppEntityModule,
    LearnAppAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class LearnAppAppModule {}
