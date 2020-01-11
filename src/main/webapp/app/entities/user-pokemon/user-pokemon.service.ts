import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserPokemon } from 'app/shared/model/user-pokemon.model';

type EntityResponseType = HttpResponse<IUserPokemon>;
type EntityArrayResponseType = HttpResponse<IUserPokemon[]>;

@Injectable({ providedIn: 'root' })
export class UserPokemonService {
  public resourceUrl = SERVER_API_URL + 'api/user-pokemons';

  constructor(protected http: HttpClient) {}

  create(userPokemon: IUserPokemon): Observable<EntityResponseType> {
    return this.http.post<IUserPokemon>(this.resourceUrl, userPokemon, { observe: 'response' });
  }

  update(userPokemon: IUserPokemon): Observable<EntityResponseType> {
    return this.http.put<IUserPokemon>(this.resourceUrl, userPokemon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserPokemon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserPokemon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
