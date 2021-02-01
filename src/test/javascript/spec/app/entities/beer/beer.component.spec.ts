import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BeerTestAppTestModule } from '../../../test.module';
import { BeerComponent } from 'app/entities/beer/beer.component';
import { BeerService } from 'app/entities/beer/beer.service';
import { Beer } from 'app/shared/model/beer.model';

describe('Component Tests', () => {
  describe('Beer Management Component', () => {
    let comp: BeerComponent;
    let fixture: ComponentFixture<BeerComponent>;
    let service: BeerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeerTestAppTestModule],
        declarations: [BeerComponent],
      })
        .overrideTemplate(BeerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Beer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beers && comp.beers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
