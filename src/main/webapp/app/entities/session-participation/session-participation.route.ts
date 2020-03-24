import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISessionParticipation, SessionParticipation } from 'app/shared/model/session-participation.model';
import { SessionParticipationService } from './session-participation.service';
import { SessionParticipationComponent } from './session-participation.component';

@Injectable({ providedIn: 'root' })
export class SessionParticipationResolve implements Resolve<ISessionParticipation> {
  constructor(private service: SessionParticipationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISessionParticipation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sessionParticipation: HttpResponse<SessionParticipation>) => {
          if (sessionParticipation.body) {
            return of(sessionParticipation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SessionParticipation());
  }
}

export const sessionParticipationRoute: Routes = [
  {
    path: '',
    component: SessionParticipationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SessionParticipations'
    },
    canActivate: [UserRouteAccessService]
  }
];
