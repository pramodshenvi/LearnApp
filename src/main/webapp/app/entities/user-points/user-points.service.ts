import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserPoints } from 'app/shared/model/user-points.model';

type EntityResponseType = HttpResponse<IUserPoints>;
type EntityArrayResponseType = HttpResponse<IUserPoints[]>;

@Injectable({ providedIn: 'root' })
export class UserPointsService {
  public resourceUrl = SERVER_API_URL + 'api/user-points';

  constructor(protected http: HttpClient) {}

  create(userPoints: IUserPoints): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPoints);
    return this.http
      .post<IUserPoints>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userPoints: IUserPoints): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPoints);
    return this.http
      .put<IUserPoints>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IUserPoints>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserPoints[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userPoints: IUserPoints): IUserPoints {
    const copy: IUserPoints = Object.assign({}, userPoints, {
      sessionDateTime: userPoints.sessionDateTime && userPoints.sessionDateTime.isValid() ? userPoints.sessionDateTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sessionDateTime = res.body.sessionDateTime ? moment(res.body.sessionDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userPoints: IUserPoints) => {
        userPoints.sessionDateTime = userPoints.sessionDateTime ? moment(userPoints.sessionDateTime) : undefined;
      });
    }
    return res;
  }
}
