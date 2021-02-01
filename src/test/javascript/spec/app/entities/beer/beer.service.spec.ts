import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BeerService } from 'app/entities/beer/beer.service';
import { IBeer, Beer } from 'app/shared/model/beer.model';

describe('Service Tests', () => {
  describe('Beer Service', () => {
    let injector: TestBed;
    let service: BeerService;
    let httpMock: HttpTestingController;
    let elemDefault: IBeer;
    let expectedResult: IBeer | IBeer[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BeerService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Beer(0, 'AAAAAAA', 0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            addedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Beer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            addedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Beer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Beer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            alcoholPercentage: 1,
            description: 'BBBBBB',
            addedDate: currentDate.format(DATE_FORMAT),
            adressOfOrigin: 'BBBBBB',
            style: 'BBBBBB',
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Beer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            alcoholPercentage: 1,
            description: 'BBBBBB',
            addedDate: currentDate.format(DATE_FORMAT),
            adressOfOrigin: 'BBBBBB',
            style: 'BBBBBB',
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            addedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Beer', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
