import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { CourseComponent } from './course.component';
import { CourseDetailComponent } from './course-detail.component';
import { CourseUpdateComponent } from './course-update.component';
import { CourseDeleteDialogComponent } from './course-delete-dialog.component';
import { CourseModifySMEDialogComponent } from './course-modify-sme-dialog.component';

import { courseRoute } from './course.route';

import { SessionComponent } from 'app/entities/session/session.component';
import { SessionDeleteDialogComponent } from 'app/entities/session/session-delete-dialog.component';
import { SessionMarkCompleteDialogComponent } from 'app/entities/session/session-mark-complete-dialog.component';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(courseRoute)],
  declarations: [
    CourseComponent,
    SessionComponent,
    SessionDeleteDialogComponent,
    SessionMarkCompleteDialogComponent,
    CourseDetailComponent,
    CourseUpdateComponent,
    CourseDeleteDialogComponent,
    CourseModifySMEDialogComponent
  ],
  entryComponents: [
    CourseDeleteDialogComponent,
    CourseModifySMEDialogComponent,
    SessionDeleteDialogComponent,
    SessionMarkCompleteDialogComponent
  ]
})
export class LearnAppCourseModule {}
