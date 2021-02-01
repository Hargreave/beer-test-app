import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBeer, Beer } from 'app/shared/model/beer.model';
import { BeerService } from './beer.service';
import { BeerComponent } from './beer.component';
import { BeerDetailComponent } from './beer-detail.component';
import { BeerUpdateComponent } from './beer-update.component';

@Injectable({ providedIn: 'root' })
export class BeerResolve implements Resolve<IBeer> {
  constructor(private service: BeerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBeer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((beer: HttpResponse<Beer>) => {
          if (beer.body) {
            return of(beer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Beer());
  }
}

export const beerRoute: Routes = [
  {
    path: '',
    component: BeerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beerTestApp.beer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BeerDetailComponent,
    resolve: {
      beer: BeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beerTestApp.beer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BeerUpdateComponent,
    resolve: {
      beer: BeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beerTestApp.beer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BeerUpdateComponent,
    resolve: {
      beer: BeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beerTestApp.beer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
