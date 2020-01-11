export interface IQuickMoves {
  id?: number;
  key?: string;
  name?: string;
}

export class QuickMoves implements IQuickMoves {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
