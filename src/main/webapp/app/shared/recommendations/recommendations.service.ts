import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICourse } from 'app/shared/model/course.model';
import { RecommendationTypes } from 'app/shared/model/enumerations/recommendation-types'

type EntityResponseType = HttpResponse<ICourse>;
type EntityArrayResponseType = HttpResponse<ICourse[]>;

@Injectable({ providedIn: 'root' })
export class RecommendationService {
  public resourceUrl = SERVER_API_URL + 'api/recommendation';

  constructor(protected http: HttpClient) {}

  getRecommendations(type: RecommendationTypes, req?: any): Observable<EntityArrayResponseType> {
    let url = this.resourceUrl;
    if(type === RecommendationTypes.SME)
      url += '/sme';
    else 
      url += '/participant';
    const options = createRequestOption(req);
    return this.http.get<ICourse[]>(url, { params: options, observe: 'response' });
  }
}
