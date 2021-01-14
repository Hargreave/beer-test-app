import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeerTestAppSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [BeerTestAppSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class BeerTestAppHomeModule {}
