import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPokemon } from 'app/shared/model/pokemon.model';

type EntityResponseType = HttpResponse<IPokemon>;
type EntityArrayResponseType = HttpResponse<IPokemon[]>;

@Injectable({ providedIn: 'root' })
export class PokemonService {
  public resourceUrl = SERVER_API_URL + 'api/pokemons';

  constructor(protected http: HttpClient) {}

  create(pokemon: IPokemon): Observable<EntityResponseType> {
    return this.http.post<IPokemon>(this.resourceUrl, pokemon, { observe: 'response' });
  }

  update(pokemon: IPokemon): Observable<EntityResponseType> {
    return this.http.put<IPokemon>(this.resourceUrl, pokemon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPokemon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPokemon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
