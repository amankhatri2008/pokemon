import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuickMoves } from 'app/shared/model/quick-moves.model';

type EntityResponseType = HttpResponse<IQuickMoves>;
type EntityArrayResponseType = HttpResponse<IQuickMoves[]>;

@Injectable({ providedIn: 'root' })
export class QuickMovesService {
  public resourceUrl = SERVER_API_URL + 'api/quick-moves';

  constructor(protected http: HttpClient) {}

  create(quickMoves: IQuickMoves): Observable<EntityResponseType> {
    return this.http.post<IQuickMoves>(this.resourceUrl, quickMoves, { observe: 'response' });
  }

  update(quickMoves: IQuickMoves): Observable<EntityResponseType> {
    return this.http.put<IQuickMoves>(this.resourceUrl, quickMoves, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuickMoves>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuickMoves[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
