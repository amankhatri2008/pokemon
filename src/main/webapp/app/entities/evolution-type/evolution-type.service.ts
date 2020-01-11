import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEvolutionType } from 'app/shared/model/evolution-type.model';

type EntityResponseType = HttpResponse<IEvolutionType>;
type EntityArrayResponseType = HttpResponse<IEvolutionType[]>;

@Injectable({ providedIn: 'root' })
export class EvolutionTypeService {
  public resourceUrl = SERVER_API_URL + 'api/evolution-types';

  constructor(protected http: HttpClient) {}

  create(evolutionType: IEvolutionType): Observable<EntityResponseType> {
    return this.http.post<IEvolutionType>(this.resourceUrl, evolutionType, { observe: 'response' });
  }

  update(evolutionType: IEvolutionType): Observable<EntityResponseType> {
    return this.http.put<IEvolutionType>(this.resourceUrl, evolutionType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvolutionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvolutionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
