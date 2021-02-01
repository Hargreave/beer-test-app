import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'beer',
        loadChildren: () => import('./beer/beer.module').then(m => m.BeerTestAppBeerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BeerTestAppEntityModule {}
