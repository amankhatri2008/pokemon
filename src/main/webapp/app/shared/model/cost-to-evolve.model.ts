import { IEvolutionType } from 'app/shared/model/evolution-type.model';

export interface ICostToEvolve {
  id?: number;
  candyCost?: number;
  kmBuddyDistance?: number;
  evolutionType?: IEvolutionType;
}

export class CostToEvolve implements ICostToEvolve {
  constructor(public id?: number, public candyCost?: number, public kmBuddyDistance?: number, public evolutionType?: IEvolutionType) {}
}
