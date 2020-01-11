import { IBuddySize } from 'app/shared/model/buddy-size.model';
import { ICinematicMoves } from 'app/shared/model/cinematic-moves.model';
import { IQuickMoves } from 'app/shared/model/quick-moves.model';
import { IFamily } from 'app/shared/model/family.model';
import { IForm } from 'app/shared/model/form.model';

export interface IPokemon {
  id?: number;
  name?: string;
  animationTime0?: number;
  animationTime1?: number;
  animationTime2?: number;
  animationTime3?: number;
  animationTime4?: number;
  animationTime5?: number;
  animationTime6?: number;
  animationTime7?: number;
  animationTime8?: number;
  height?: number;
  modelHeight?: number;
  kmBuddyDistance?: number;
  weight?: number;
  modelScale?: number;
  maxCP?: number;
  cylinderGround?: number;
  cylinderRadius?: number;
  diskRadius?: number;
  key?: string;
  cinematicMovesId?: number;
  baseAttack?: number;
  baseDefense?: number;
  baseStamina?: number;
  buddySize?: IBuddySize;
  cinematicMovesManies?: ICinematicMoves[];
  quickMovesManies?: IQuickMoves[];
  family?: IFamily;
  formManies?: IForm[];
}

export class Pokemon implements IPokemon {
  constructor(
    public id?: number,
    public name?: string,
    public animationTime0?: number,
    public animationTime1?: number,
    public animationTime2?: number,
    public animationTime3?: number,
    public animationTime4?: number,
    public animationTime5?: number,
    public animationTime6?: number,
    public animationTime7?: number,
    public animationTime8?: number,
    public height?: number,
    public modelHeight?: number,
    public kmBuddyDistance?: number,
    public weight?: number,
    public modelScale?: number,
    public maxCP?: number,
    public cylinderGround?: number,
    public cylinderRadius?: number,
    public diskRadius?: number,
    public key?: string,
    public cinematicMovesId?: number,
    public baseAttack?: number,
    public baseDefense?: number,
    public baseStamina?: number,
    public buddySize?: IBuddySize,
    public cinematicMovesManies?: ICinematicMoves[],
    public quickMovesManies?: IQuickMoves[],
    public family?: IFamily,
    public formManies?: IForm[]
  ) {}
}
