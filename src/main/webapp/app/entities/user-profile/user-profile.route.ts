import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { UserProfileUpdateComponent } from './user-profile-update.component';
import { UserProfileComponent } from './user-profile.component';
import { UserProfileService } from './user-profile.service';

@Injectable({ providedIn: 'root' })
export class UserProfileResolve implements Resolve<IUserProfile> {
  constructor(private service: UserProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userProfile: HttpResponse<UserProfile>) => {
          if (userProfile.body) {
            return of(userProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserProfile());
  }
}

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
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
