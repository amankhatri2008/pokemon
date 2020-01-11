export interface IBuddySize {
  id?: number;
  key?: string;
  name?: string;
}

export class BuddySize implements IBuddySize {
  constructor(public id?: number, public key?: string, public name?: string) {}
}
