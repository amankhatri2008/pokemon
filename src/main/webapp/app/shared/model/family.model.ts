export interface IFamily {
  id?: number;
  key?: string;
  name?: string;
}

export class Family implements IFamily {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
