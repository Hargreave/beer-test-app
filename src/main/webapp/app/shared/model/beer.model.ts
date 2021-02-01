import { Moment } from 'moment';

export interface IBeer {
  id?: number;
  name?: string;
  alcoholPercentage?: number;
  description?: string;
  addedDate?: Moment;
  adressOfOrigin?: string;
  style?: string;
  comment?: string;
}

export class Beer implements IBeer {
  constructor(
    public id?: number,
    public name?: string,
    public alcoholPercentage?: number,
    public description?: string,
    public addedDate?: Moment,
    public adressOfOrigin?: string,
    public style?: string,
    public comment?: string
  ) {}
}
