import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypes, Types } from 'app/shared/model/types.model';
import { TypesService } from './types.service';
import { TypesComponent } from './types.component';
import { TypesDetailComponent } from './types-detail.component';
import { TypesUpdateComponent } from './types-update.component';

@Injectable({ providedIn: 'root' })
export class TypesResolve implements Resolve<ITypes> {
  constructor(private service: TypesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((types: HttpResponse<Types>) => {
          if (types.body) {
            return of(types.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Types());
  }
}

export const typesRoute: Routes = [
  {
    path: '',
    component: TypesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Types'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypesDetailComponent,
    resolve: {
      types: TypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Types'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypesUpdateComponent,
    resolve: {
      types: TypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Types'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypesUpdateComponent,
    resolve: {
      types: TypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Types'
    },
    canActivate: [UserRouteAccessService]
  }
];
