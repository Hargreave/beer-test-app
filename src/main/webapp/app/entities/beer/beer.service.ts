import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBeer } from 'app/shared/model/beer.model';

type EntityResponseType = HttpResponse<IBeer>;
type EntityArrayResponseType = HttpResponse<IBeer[]>;

@Injectable({ providedIn: 'root' })
export class BeerService {
  public resourceUrl = SERVER_API_URL + 'api/beers';

  constructor(protected http: HttpClient) {}

  create(beer: IBeer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(beer);
    return this.http
      .post<IBeer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(beer: IBeer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(beer);
    return this.http
      .put<IBeer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBeer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(beer: IBeer): IBeer {
    const copy: IBeer = Object.assign({}, beer, {
      addedDate: beer.addedDate && beer.addedDate.isValid() ? beer.addedDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.addedDate = res.body.addedDate ? moment(res.body.addedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((beer: IBeer) => {
        beer.addedDate = beer.addedDate ? moment(beer.addedDate) : undefined;
      });
    }
    return res;
  }
}
