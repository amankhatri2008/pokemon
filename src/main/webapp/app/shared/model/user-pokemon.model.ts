import { IUser } from 'app/core/user/user.model';
import { IPokemon } from 'app/shared/model/pokemon.model';

export interface IUserPokemon {
  id?: number;
  userManies?: IUser[];
  pokemonManies?: IPokemon[];
}

export class UserPokemon implements IUserPokemon {
  constructor(public id?: number, public userManies?: IUser[], public pokemonManies?: IPokemon[]) {}
}
