import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBuddySize } from 'app/shared/model/buddy-size.model';

type EntityResponseType = HttpResponse<IBuddySize>;
type EntityArrayResponseType = HttpResponse<IBuddySize[]>;

@Injectable({ providedIn: 'root' })
export class BuddySizeService {
  public resourceUrl = SERVER_API_URL + 'api/buddy-sizes';

  constructor(protected http: HttpClient) {}

  create(buddySize: IBuddySize): Observable<EntityResponseType> {
    return this.http.post<IBuddySize>(this.resourceUrl, buddySize, { observe: 'response' });
  }

  update(buddySize: IBuddySize): Observable<EntityResponseType> {
    return this.http.put<IBuddySize>(this.resourceUrl, buddySize, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBuddySize>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBuddySize[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
