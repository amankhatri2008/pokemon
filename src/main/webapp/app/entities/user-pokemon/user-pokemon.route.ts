import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserPokemon, UserPokemon } from 'app/shared/model/user-pokemon.model';
import { UserPokemonService } from './user-pokemon.service';
import { UserPokemonComponent } from './user-pokemon.component';
import { UserPokemonDetailComponent } from './user-pokemon-detail.component';
import { UserPokemonUpdateComponent } from './user-pokemon-update.component';

@Injectable({ providedIn: 'root' })
export class UserPokemonResolve implements Resolve<IUserPokemon> {
  constructor(private service: UserPokemonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPokemon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userPokemon: HttpResponse<UserPokemon>) => {
          if (userPokemon.body) {
            return of(userPokemon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPokemon());
  }
}

export const userPokemonRoute: Routes = [
  {
    path: '',
    component: UserPokemonComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'UserPokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserPokemonDetailComponent,
    resolve: {
      userPokemon: UserPokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserPokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserPokemonUpdateComponent,
    resolve: {
      userPokemon: UserPokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserPokemons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserPokemonUpdateComponent,
    resolve: {
      userPokemon: UserPokemonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserPokemons'
    },
    canActivate: [UserRouteAccessService]
  }
];
