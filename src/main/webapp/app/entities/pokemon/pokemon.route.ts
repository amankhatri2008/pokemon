import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPokemon, Pokemon } from 'app/shared/model/pokemon.model';
import { PokemonService } from './pokemon.service';
import { PokemonComponent } from './pokemon.component';
import { PokemonDetailComponent } from './pokemon-detail.component';
import { PokemonUpdateComponent } from './pokemon-update.component';

@Injectable({ providedIn: 'root' })
export class PokemonResolve implements Resolve<IPokemon> {
  constructor(private service: PokemonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPokemon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pokemon: HttpResponse<Pokemon>) => {
          if (pokemon.body) {
            return of(pokemon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pokemon());
  }
}

export const pokemonRoute: Routes = [
  {
    path: '',
    component: PokemonComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Pokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PokemonDetailComponent,
    resolve: {
      pokemon: PokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PokemonUpdateComponent,
    resolve: {
      pokemon: PokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PokemonUpdateComponent,
    resolve: {
      pokemon: PokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pokemons'
    },
    canActivate: [UserRouteAccessService]
  }
];
