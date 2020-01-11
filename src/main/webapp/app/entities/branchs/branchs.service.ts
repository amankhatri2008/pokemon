import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBranchs } from 'app/shared/model/branchs.model';

type EntityResponseType = HttpResponse<IBranchs>;
type EntityArrayResponseType = HttpResponse<IBranchs[]>;

@Injectable({ providedIn: 'root' })
export class BranchsService {
  public resourceUrl = SERVER_API_URL + 'api/branchs';

  constructor(protected http: HttpClient) {}

  create(branchs: IBranchs): Observable<EntityResponseType> {
    return this.http.post<IBranchs>(this.resourceUrl, branchs, { observe: 'response' });
  }

  update(branchs: IBranchs): Observable<EntityResponseType> {
    return this.http.put<IBranchs>(this.resourceUrl, branchs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBranchs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBranchs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
