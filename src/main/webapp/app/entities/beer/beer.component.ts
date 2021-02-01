import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeer } from 'app/shared/model/beer.model';
import { BeerService } from './beer.service';
import { BeerDeleteDialogComponent } from './beer-delete-dialog.component';

@Component({
  selector: 'jhi-beer',
  templateUrl: './beer.component.html',
})
export class BeerComponent implements OnInit, OnDestroy {
  beers?: IBeer[];
  eventSubscriber?: Subscription;

  constructor(protected beerService: BeerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.beerService.query().subscribe((res: HttpResponse<IBeer[]>) => (this.beers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBeers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBeer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeers(): void {
    this.eventSubscriber = this.eventManager.subscribe('beerListModification', () => this.loadAll());
  }

  delete(beer: IBeer): void {
    const modalRef = this.modalService.open(BeerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.beer = beer;
  }
}
