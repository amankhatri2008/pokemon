import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICinematicMoves, CinematicMoves } from 'app/shared/model/cinematic-moves.model';
import { CinematicMovesService } from './cinematic-moves.service';
import { CinematicMovesComponent } from './cinematic-moves.component';
import { CinematicMovesDetailComponent } from './cinematic-moves-detail.component';
import { CinematicMovesUpdateComponent } from './cinematic-moves-update.component';

@Injectable({ providedIn: 'root' })
export class CinematicMovesResolve implements Resolve<ICinematicMoves> {
  constructor(private service: CinematicMovesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICinematicMoves> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cinematicMoves: HttpResponse<CinematicMoves>) => {
          if (cinematicMoves.body) {
            return of(cinematicMoves.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CinematicMoves());
  }
}

export const cinematicMovesRoute: Routes = [
  {
    path: '',
    component: CinematicMovesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CinematicMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CinematicMovesDetailComponent,
    resolve: {
      cinematicMoves: CinematicMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CinematicMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CinematicMovesUpdateComponent,
    resolve: {
      cinematicMoves: CinematicMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CinematicMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CinematicMovesUpdateComponent,
    resolve: {
      cinematicMoves: CinematicMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CinematicMoves'
    },
    canActivate: [UserRouteAccessService]
  }
];
