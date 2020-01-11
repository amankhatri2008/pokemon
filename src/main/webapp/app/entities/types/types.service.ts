import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITypes } from 'app/shared/model/types.model';

type EntityResponseType = HttpResponse<ITypes>;
type EntityArrayResponseType = HttpResponse<ITypes[]>;

@Injectable({ providedIn: 'root' })
export class TypesService {
  public resourceUrl = SERVER_API_URL + 'api/types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/types';

  constructor(protected http: HttpClient) {}

  create(types: ITypes): Observable<EntityResponseType> {
    return this.http.post<ITypes>(this.resourceUrl, types, { observe: 'response' });
  }

  update(types: ITypes): Observable<EntityResponseType> {
    return this.http.put<ITypes>(this.resourceUrl, types, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypes[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
