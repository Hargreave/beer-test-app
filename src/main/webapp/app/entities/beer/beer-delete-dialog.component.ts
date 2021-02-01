import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeer } from 'app/shared/model/beer.model';
import { BeerService } from './beer.service';

@Component({
  templateUrl: './beer-delete-dialog.component.html',
})
export class BeerDeleteDialogComponent {
  beer?: IBeer;

  constructor(protected beerService: BeerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('beerListModification');
      this.activeModal.close();
    });
  }
}
