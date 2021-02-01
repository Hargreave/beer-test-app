import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBeer, Beer } from 'app/shared/model/beer.model';
import { BeerService } from './beer.service';

@Component({
  selector: 'jhi-beer-update',
  templateUrl: './beer-update.component.html',
})
export class BeerUpdateComponent implements OnInit {
  isSaving = false;
  addedDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    alcoholPercentage: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    description: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(2000)]],
    addedDate: [null, [Validators.required]],
    adressOfOrigin: [],
    style: [],
    comment: [],
  });

  constructor(protected beerService: BeerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beer }) => {
      this.updateForm(beer);
    });
  }

  updateForm(beer: IBeer): void {
    this.editForm.patchValue({
      id: beer.id,
      name: beer.name,
      alcoholPercentage: beer.alcoholPercentage,
      description: beer.description,
      addedDate: beer.addedDate,
      adressOfOrigin: beer.adressOfOrigin,
      style: beer.style,
      comment: beer.comment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beer = this.createFromForm();
    if (beer.id !== undefined) {
      this.subscribeToSaveResponse(this.beerService.update(beer));
    } else {
      this.subscribeToSaveResponse(this.beerService.create(beer));
    }
  }

  private createFromForm(): IBeer {
    return {
      ...new Beer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      alcoholPercentage: this.editForm.get(['alcoholPercentage'])!.value,
      description: this.editForm.get(['description'])!.value,
      addedDate: this.editForm.get(['addedDate'])!.value,
      adressOfOrigin: this.editForm.get(['adressOfOrigin'])!.value,
      style: this.editForm.get(['style'])!.value,
      comment: this.editForm.get(['comment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeer>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
