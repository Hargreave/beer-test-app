import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BeerTestAppTestModule } from '../../../test.module';
import { BeerUpdateComponent } from 'app/entities/beer/beer-update.component';
import { BeerService } from 'app/entities/beer/beer.service';
import { Beer } from 'app/shared/model/beer.model';

describe('Component Tests', () => {
  describe('Beer Management Update Component', () => {
    let comp: BeerUpdateComponent;
    let fixture: ComponentFixture<BeerUpdateComponent>;
    let service: BeerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeerTestAppTestModule],
        declarations: [BeerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BeerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Beer(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Beer();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
