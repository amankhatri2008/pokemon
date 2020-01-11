import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEncounterType, EncounterType } from 'app/shared/model/encounter-type.model';
import { EncounterTypeService } from './encounter-type.service';
import { EncounterTypeComponent } from './encounter-type.component';
import { EncounterTypeDetailComponent } from './encounter-type-detail.component';
import { EncounterTypeUpdateComponent } from './encounter-type-update.component';

@Injectable({ providedIn: 'root' })
export class EncounterTypeResolve implements Resolve<IEncounterType> {
  constructor(private service: EncounterTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncounterType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((encounterType: HttpResponse<EncounterType>) => {
          if (encounterType.body) {
            return of(encounterType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EncounterType());
  }
}

export const encounterTypeRoute: Routes = [
  {
    path: '',
    component: EncounterTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'EncounterTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EncounterTypeDetailComponent,
    resolve: {
      encounterType: EncounterTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EncounterTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EncounterTypeUpdateComponent,
    resolve: {
      encounterType: EncounterTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EncounterTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EncounterTypeUpdateComponent,
    resolve: {
      encounterType: EncounterTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EncounterTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
