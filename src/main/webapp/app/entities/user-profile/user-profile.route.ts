import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { UserProfileUpdateComponent } from './user-profile-update.component';
import { UserProfileComponent } from './user-profile.component';

export const userProfileRoute: Routes = [
  {
    path: '',
    component: UserProfileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'edit',
    component: UserProfileUpdateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
