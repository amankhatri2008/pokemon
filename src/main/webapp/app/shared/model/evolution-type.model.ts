export interface IEvolutionType {
  id?: number;
  key?: string;
  name?: string;
}

export class EvolutionType implements IEvolutionType {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
