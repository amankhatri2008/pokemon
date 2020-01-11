import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuickMoves, QuickMoves } from 'app/shared/model/quick-moves.model';
import { QuickMovesService } from './quick-moves.service';
import { QuickMovesComponent } from './quick-moves.component';
import { QuickMovesDetailComponent } from './quick-moves-detail.component';
import { QuickMovesUpdateComponent } from './quick-moves-update.component';

@Injectable({ providedIn: 'root' })
export class QuickMovesResolve implements Resolve<IQuickMoves> {
  constructor(private service: QuickMovesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuickMoves> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((quickMoves: HttpResponse<QuickMoves>) => {
          if (quickMoves.body) {
            return of(quickMoves.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuickMoves());
  }
}

export const quickMovesRoute: Routes = [
  {
    path: '',
    component: QuickMovesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'QuickMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuickMovesDetailComponent,
    resolve: {
      quickMoves: QuickMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuickMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuickMovesUpdateComponent,
    resolve: {
      quickMoves: QuickMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuickMoves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuickMovesUpdateComponent,
    resolve: {
      quickMoves: QuickMovesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'QuickMoves'
    },
    canActivate: [UserRouteAccessService]
  }
];
