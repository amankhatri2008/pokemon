import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPokemon } from 'app/shared/model/user-pokemon.model';

@Component({
  selector: 'jhi-user-pokemon-detail',
  templateUrl: './user-pokemon-detail.component.html'
})
export class UserPokemonDetailComponent implements OnInit {
  userPokemon: IUserPokemon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPokemon }) => {
      this.userPokemon = userPokemon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
