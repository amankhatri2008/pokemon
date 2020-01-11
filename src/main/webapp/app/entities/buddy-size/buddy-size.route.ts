import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBuddySize, BuddySize } from 'app/shared/model/buddy-size.model';
import { BuddySizeService } from './buddy-size.service';
import { BuddySizeComponent } from './buddy-size.component';
import { BuddySizeDetailComponent } from './buddy-size-detail.component';
import { BuddySizeUpdateComponent } from './buddy-size-update.component';

@Injectable({ providedIn: 'root' })
export class BuddySizeResolve implements Resolve<IBuddySize> {
  constructor(private service: BuddySizeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuddySize> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((buddySize: HttpResponse<BuddySize>) => {
          if (buddySize.body) {
            return of(buddySize.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BuddySize());
  }
}

export const buddySizeRoute: Routes = [
  {
    path: '',
    component: BuddySizeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'BuddySizes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BuddySizeDetailComponent,
    resolve: {
      buddySize: BuddySizeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BuddySizes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BuddySizeUpdateComponent,
    resolve: {
      buddySize: BuddySizeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BuddySizes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BuddySizeUpdateComponent,
    resolve: {
      buddySize: BuddySizeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'BuddySizes'
    },
    canActivate: [UserRouteAccessService]
  }
];
