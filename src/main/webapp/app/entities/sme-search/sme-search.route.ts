import { Injectable } from '@angular/core';
import { Routes, Router } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SMESearchService } from './sme-search.service';
import { SMESearchComponent } from './sme-search.component';

@Injectable({ providedIn: 'root' })
export class SMESearchResolve {
  constructor(private service: SMESearchService, private router: Router) {}
}

export const smeSearchRoute: Routes = [
  {
    path: '',
    component: SMESearchComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SessionParticipations'
    },
    canActivate: [UserRouteAccessService]
  }
];
