import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEncounter } from 'app/shared/model/encounter.model';

type EntityResponseType = HttpResponse<IEncounter>;
type EntityArrayResponseType = HttpResponse<IEncounter[]>;

@Injectable({ providedIn: 'root' })
export class EncounterService {
  public resourceUrl = SERVER_API_URL + 'api/encounters';

  constructor(protected http: HttpClient) {}

  create(encounter: IEncounter): Observable<EntityResponseType> {
    return this.http.post<IEncounter>(this.resourceUrl, encounter, { observe: 'response' });
  }

  update(encounter: IEncounter): Observable<EntityResponseType> {
    return this.http.put<IEncounter>(this.resourceUrl, encounter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEncounter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEncounter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
