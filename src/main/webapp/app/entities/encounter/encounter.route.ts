import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEncounter, Encounter } from 'app/shared/model/encounter.model';
import { EncounterService } from './encounter.service';
import { EncounterComponent } from './encounter.component';
import { EncounterDetailComponent } from './encounter-detail.component';
import { EncounterUpdateComponent } from './encounter-update.component';

@Injectable({ providedIn: 'root' })
export class EncounterResolve implements Resolve<IEncounter> {
  constructor(private service: EncounterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncounter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((encounter: HttpResponse<Encounter>) => {
          if (encounter.body) {
            return of(encounter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Encounter());
  }
}

export const encounterRoute: Routes = [
  {
    path: '',
    component: EncounterComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Encounters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EncounterDetailComponent,
    resolve: {
      encounter: EncounterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Encounters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EncounterUpdateComponent,
    resolve: {
      encounter: EncounterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Encounters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EncounterUpdateComponent,
    resolve: {
      encounter: EncounterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Encounters'
    },
    canActivate: [UserRouteAccessService]
  }
];
