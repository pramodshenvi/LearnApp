import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { SessionParticipationComponent } from './session-participation.component';
import { sessionParticipationRoute } from './session-participation.route';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(sessionParticipationRoute)],
  declarations: [
    SessionParticipationComponent
  ],
  entryComponents: []
})
export class LearnAppSessionParticipationModule {}
