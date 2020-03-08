import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { CourseComponent } from './course.component';
import { CourseDetailComponent } from './course-detail.component';
import { CourseUpdateComponent } from './course-update.component';
import { CourseDeleteDialogComponent } from './course-delete-dialog.component';

import { courseRoute } from './course.route';

import { DynamicDropdownComponent } from 'app/shared/util/dynamic-dropdown'
import { SessionComponent } from 'app/entities/session/session.component';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(courseRoute)],
  declarations: [CourseComponent, SessionComponent, CourseDetailComponent, CourseUpdateComponent, CourseDeleteDialogComponent, DynamicDropdownComponent],
  entryComponents: [CourseDeleteDialogComponent]
})
export class LearnAppCourseModule {}
