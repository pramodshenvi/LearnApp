import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserPoints, UserPoints } from 'app/shared/model/user-points.model';
import { UserPointsService } from './user-points.service';
import { UserPointsComponent } from './user-points.component';

@Injectable({ providedIn: 'root' })
export class UserPointsResolve implements Resolve<IUserPoints> {
  constructor(private service: UserPointsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPoints> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userPoints: HttpResponse<UserPoints>) => {
          if (userPoints.body) {
            return of(userPoints.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPoints());
  }
}

export const userPointsRoute: Routes = [
  {
    path: '',
    component: UserPointsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserPoints'
    },
    canActivate: [UserRouteAccessService]
  }
];
