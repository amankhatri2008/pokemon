import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBranchs, Branchs } from 'app/shared/model/branchs.model';
import { BranchsService } from './branchs.service';
import { BranchsComponent } from './branchs.component';
import { BranchsDetailComponent } from './branchs-detail.component';
import { BranchsUpdateComponent } from './branchs-update.component';

@Injectable({ providedIn: 'root' })
export class BranchsResolve implements Resolve<IBranchs> {
  constructor(private service: BranchsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBranchs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((branchs: HttpResponse<Branchs>) => {
          if (branchs.body) {
            return of(branchs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Branchs());
  }
}

export const branchsRoute: Routes = [
  {
    path: '',
    component: BranchsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Branchs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BranchsDetailComponent,
    resolve: {
      branchs: BranchsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Branchs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BranchsUpdateComponent,
    resolve: {
      branchs: BranchsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Branchs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BranchsUpdateComponent,
    resolve: {
      branchs: BranchsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Branchs'
    },
    canActivate: [UserRouteAccessService]
  }
];
