import { IEncounterType } from 'app/shared/model/encounter-type.model';
import { IGender } from 'app/shared/model/gender.model';

export interface IEncounter {
  id?: number;
  attackProbability?: number;
  attackTimer?: number;
  baseFleeRate?: number;
  baseCaptureRate?: number;
  cameraDistance?: number;
  collisionRadius?: number;
  dodgeDistance?: number;
  dodgeProbability?: number;
  jumpTime?: number;
  maxPokemonActionFrequency?: number;
  minPokemonActionFrequency?: number;
  encounterType?: IEncounterType;
  gender?: IGender;
}

export class Encounter implements IEncounter {
  constructor(
    public id?: number,
    public attackProbability?: number,
    public attackTimer?: number,
    public baseFleeRate?: number,
    public baseCaptureRate?: number,
    public cameraDistance?: number,
    public collisionRadius?: number,
    public dodgeDistance?: number,
    public dodgeProbability?: number,
    public jumpTime?: number,
    public maxPokemonActionFrequency?: number,
    public minPokemonActionFrequency?: number,
    public encounterType?: IEncounterType,
    public gender?: IGender
  ) {}
}
