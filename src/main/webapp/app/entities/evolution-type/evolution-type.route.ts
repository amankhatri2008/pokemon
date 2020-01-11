import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEvolutionType, EvolutionType } from 'app/shared/model/evolution-type.model';
import { EvolutionTypeService } from './evolution-type.service';
import { EvolutionTypeComponent } from './evolution-type.component';
import { EvolutionTypeDetailComponent } from './evolution-type-detail.component';
import { EvolutionTypeUpdateComponent } from './evolution-type-update.component';

@Injectable({ providedIn: 'root' })
export class EvolutionTypeResolve implements Resolve<IEvolutionType> {
  constructor(private service: EvolutionTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvolutionType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((evolutionType: HttpResponse<EvolutionType>) => {
          if (evolutionType.body) {
            return of(evolutionType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvolutionType());
  }
}

export const evolutionTypeRoute: Routes = [
  {
    path: '',
    component: EvolutionTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'EvolutionTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EvolutionTypeDetailComponent,
    resolve: {
      evolutionType: EvolutionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvolutionTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EvolutionTypeUpdateComponent,
    resolve: {
      evolutionType: EvolutionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvolutionTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EvolutionTypeUpdateComponent,
    resolve: {
      evolutionType: EvolutionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvolutionTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
