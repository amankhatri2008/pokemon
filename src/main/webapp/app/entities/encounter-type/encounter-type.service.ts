import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEncounterType } from 'app/shared/model/encounter-type.model';

type EntityResponseType = HttpResponse<IEncounterType>;
type EntityArrayResponseType = HttpResponse<IEncounterType[]>;

@Injectable({ providedIn: 'root' })
export class EncounterTypeService {
  public resourceUrl = SERVER_API_URL + 'api/encounter-types';

  constructor(protected http: HttpClient) {}

  create(encounterType: IEncounterType): Observable<EntityResponseType> {
    return this.http.post<IEncounterType>(this.resourceUrl, encounterType, { observe: 'response' });
  }

  update(encounterType: IEncounterType): Observable<EntityResponseType> {
    return this.http.put<IEncounterType>(this.resourceUrl, encounterType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEncounterType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEncounterType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
