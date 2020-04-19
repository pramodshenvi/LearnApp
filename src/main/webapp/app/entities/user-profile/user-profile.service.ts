import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { ISession } from 'app/shared/model/session.model';

type EntityResponseType = HttpResponse<IUserProfile>;
type EntityArrayResponseType = HttpResponse<IUserProfile[]>;

@Injectable({ providedIn: 'root' })
export class UserProfileService {
  public resourceUrl = SERVER_API_URL + 'api/user-profile';

  constructor(protected http: HttpClient) {}

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUserProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  registeredSessions(): Observable<ISession[] | null> {
    const endpoint = '/registered-sessions';
    return this.http.get<ISession[]>(this.resourceUrl + endpoint);
  }
}
