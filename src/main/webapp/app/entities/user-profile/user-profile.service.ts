import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISession } from 'app/shared/model/session.model';

@Injectable({ providedIn: 'root' })
export class UserProfileService {
  public resourceUrl = SERVER_API_URL + 'api/user-profile';

  constructor(protected http: HttpClient) {}

  registeredSessions(): Observable<ISession[] | null> {
    const endpoint = '/registered-sessions';
    return this.http.get<ISession[]>(this.resourceUrl + endpoint);
  }
}
