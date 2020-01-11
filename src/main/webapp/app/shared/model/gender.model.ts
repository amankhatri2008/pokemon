export interface IGender {
  id?: number;
  malePercent?: number;
  femalePercent?: number;
}

export class Gender implements IGender {
  constructor(public id?: number, public malePercent?: number, public femalePercent?: number) {}
}
