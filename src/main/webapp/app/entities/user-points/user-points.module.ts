import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { UserPointsComponent } from './user-points.component';
import { userPointsRoute } from './user-points.route';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(userPointsRoute)],
  declarations: [UserPointsComponent],
  entryComponents: []
})
export class LearnAppUserPointsModule {}
