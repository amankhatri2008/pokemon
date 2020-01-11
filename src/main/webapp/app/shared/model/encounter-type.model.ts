export interface IEncounterType {
  id?: number;
  key?: string;
  name?: string;
}

export class EncounterType implements IEncounterType {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
