import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { Account } from 'app/core/user/account.model';

@Injectable({ providedIn: 'root' })
export class SMESearchService {
  public usersMatchingSMESkillsUrl = SERVER_API_URL + 'api/users-matching-sme-skills';

  constructor(protected http: HttpClient) {}

  getUsersWithMatchingSMESkills(user: any): Observable<HttpResponse<Account[]>> {
    return this.http
      .post<Account[]>(this.usersMatchingSMESkillsUrl, user, { observe: 'response' });
  }
}
