import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISessionParticipation } from 'app/shared/model/session-participation.model';

type EntityResponseType = HttpResponse<ISessionParticipation>;
type EntityArrayResponseType = HttpResponse<ISessionParticipation[]>;

@Injectable({ providedIn: 'root' })
export class SessionParticipationService {
  public resourceUrl = SERVER_API_URL + 'api/session-participations';
  public userSessionParticipationsUrl = SERVER_API_URL + 'api/user-session-participations';

  constructor(protected http: HttpClient) {}

  createOrUpdate(sessionParticipation: ISessionParticipation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionParticipation);
    return this.http
      .post<ISessionParticipation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ISessionParticipation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISessionParticipation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMatchingSessionParticipationsForUser(sessionParticipation: ISessionParticipation): Observable<EntityArrayResponseType> {
    const copy = this.convertDateFromClient(sessionParticipation);
    return this.http
      .post<ISessionParticipation[]>(this.userSessionParticipationsUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(sessionParticipation: ISessionParticipation): ISessionParticipation {
    const copy: ISessionParticipation = Object.assign({}, sessionParticipation, {
      registrationDateTime:
        sessionParticipation.registrationDateTime && sessionParticipation.registrationDateTime.isValid()
          ? sessionParticipation.registrationDateTime.toJSON()
          : undefined,
      attendanceDateTime:
        sessionParticipation.attendanceDateTime && sessionParticipation.attendanceDateTime.isValid()
          ? sessionParticipation.attendanceDateTime.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registrationDateTime = res.body.registrationDateTime ? moment(res.body.registrationDateTime) : undefined;
      res.body.attendanceDateTime = res.body.attendanceDateTime ? moment(res.body.attendanceDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sessionParticipation: ISessionParticipation) => {
        sessionParticipation.registrationDateTime = sessionParticipation.registrationDateTime
          ? moment(sessionParticipation.registrationDateTime)
          : undefined;
        sessionParticipation.attendanceDateTime = sessionParticipation.attendanceDateTime
          ? moment(sessionParticipation.attendanceDateTime)
          : undefined;
      });
    }
    return res;
  }
}
