export interface ICinematicMoves {
  id?: number;
  key?: string;
  name?: string;
}

export class CinematicMoves implements ICinematicMoves {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
