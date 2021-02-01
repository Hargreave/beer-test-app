import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeerTestAppSharedModule } from 'app/shared/shared.module';
import { BeerComponent } from './beer.component';
import { BeerDetailComponent } from './beer-detail.component';
import { BeerUpdateComponent } from './beer-update.component';
import { BeerDeleteDialogComponent } from './beer-delete-dialog.component';
import { beerRoute } from './beer.route';

@NgModule({
  imports: [BeerTestAppSharedModule, RouterModule.forChild(beerRoute)],
  declarations: [BeerComponent, BeerDetailComponent, BeerUpdateComponent, BeerDeleteDialogComponent],
  entryComponents: [BeerDeleteDialogComponent],
})
export class BeerTestAppBeerModule {}
