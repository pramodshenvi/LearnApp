import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { SessionDetailComponent } from './session-detail.component';
import { SessionUpdateComponent } from './session-update.component';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';
import { sessionRoute } from './session.route';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(sessionRoute)],
  declarations: [SessionDetailComponent, SessionUpdateComponent, SessionDeleteDialogComponent],
  entryComponents: [SessionDeleteDialogComponent]
})
export class LearnAppSessionModule {}
