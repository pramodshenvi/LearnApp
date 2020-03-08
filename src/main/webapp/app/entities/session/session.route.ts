import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { SessionDetailComponent } from './session-detail.component';
import { SessionUpdateComponent } from './session-update.component';

@Injectable({ providedIn: 'root' })
export class SessionResolve implements Resolve<ISession> {
  constructor(private service: SessionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISession> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((session: HttpResponse<Session>) => {
          if (session.body) {
            return of(session.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Session());
  }
}

export const sessionRoute: Routes = [
  {
    path: ':id/view',
    component: SessionDetailComponent,
    resolve: {
      session: SessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sessions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SessionUpdateComponent,
    resolve: {
      session: SessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sessions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SessionUpdateComponent,
    resolve: {
      session: SessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sessions'
    },
    canActivate: [UserRouteAccessService]
  }
];
