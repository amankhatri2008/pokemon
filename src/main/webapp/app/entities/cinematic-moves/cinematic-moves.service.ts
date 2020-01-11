import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICinematicMoves } from 'app/shared/model/cinematic-moves.model';

type EntityResponseType = HttpResponse<ICinematicMoves>;
type EntityArrayResponseType = HttpResponse<ICinematicMoves[]>;

@Injectable({ providedIn: 'root' })
export class CinematicMovesService {
  public resourceUrl = SERVER_API_URL + 'api/cinematic-moves';

  constructor(protected http: HttpClient) {}

  create(cinematicMoves: ICinematicMoves): Observable<EntityResponseType> {
    return this.http.post<ICinematicMoves>(this.resourceUrl, cinematicMoves, { observe: 'response' });
  }

  update(cinematicMoves: ICinematicMoves): Observable<EntityResponseType> {
    return this.http.put<ICinematicMoves>(this.resourceUrl, cinematicMoves, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICinematicMoves>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICinematicMoves[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
