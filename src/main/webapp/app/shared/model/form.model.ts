export interface IForm {
  id?: number;
  key?: string;
  name?: string;
}

export class Form implements IForm {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
