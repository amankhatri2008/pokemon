export interface IBranchs {
  id?: number;
  key?: string;
  name?: string;
}

export class Branchs implements IBranchs {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
