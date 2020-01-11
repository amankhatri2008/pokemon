import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICostToEvolve } from 'app/shared/model/cost-to-evolve.model';

type EntityResponseType = HttpResponse<ICostToEvolve>;
type EntityArrayResponseType = HttpResponse<ICostToEvolve[]>;

@Injectable({ providedIn: 'root' })
export class CostToEvolveService {
  public resourceUrl = SERVER_API_URL + 'api/cost-to-evolves';

  constructor(protected http: HttpClient) {}

  create(costToEvolve: ICostToEvolve): Observable<EntityResponseType> {
    return this.http.post<ICostToEvolve>(this.resourceUrl, costToEvolve, { observe: 'response' });
  }

  update(costToEvolve: ICostToEvolve): Observable<EntityResponseType> {
    return this.http.put<ICostToEvolve>(this.resourceUrl, costToEvolve, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICostToEvolve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICostToEvolve[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
