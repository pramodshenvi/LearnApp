import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { UserHomeComponent } from './user-home.component';

import { RecommendationComponent } from 'app/shared/recommendations/recommendations.component';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, UserHomeComponent, RecommendationComponent]
})
export class LearnAppHomeModule {}
