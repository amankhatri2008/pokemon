import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICostToEvolve, CostToEvolve } from 'app/shared/model/cost-to-evolve.model';
import { CostToEvolveService } from './cost-to-evolve.service';
import { CostToEvolveComponent } from './cost-to-evolve.component';
import { CostToEvolveDetailComponent } from './cost-to-evolve-detail.component';
import { CostToEvolveUpdateComponent } from './cost-to-evolve-update.component';

@Injectable({ providedIn: 'root' })
export class CostToEvolveResolve implements Resolve<ICostToEvolve> {
  constructor(private service: CostToEvolveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICostToEvolve> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((costToEvolve: HttpResponse<CostToEvolve>) => {
          if (costToEvolve.body) {
            return of(costToEvolve.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CostToEvolve());
  }
}

export const costToEvolveRoute: Routes = [
  {
    path: '',
    component: CostToEvolveComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CostToEvolves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CostToEvolveDetailComponent,
    resolve: {
      costToEvolve: CostToEvolveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CostToEvolves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CostToEvolveUpdateComponent,
    resolve: {
      costToEvolve: CostToEvolveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CostToEvolves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CostToEvolveUpdateComponent,
    resolve: {
      costToEvolve: CostToEvolveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CostToEvolves'
    },
    canActivate: [UserRouteAccessService]
  }
];
