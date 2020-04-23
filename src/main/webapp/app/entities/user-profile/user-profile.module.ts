import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { UserProfileUpdateComponent } from './user-profile-update.component';
import { UserProfileComponent } from './user-profile.component';
import { userProfileRoute } from './user-profile.route';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(userProfileRoute)],
  declarations: [UserProfileComponent, UserProfileUpdateComponent]
})
export class LearnAppUserProfileModule {}
