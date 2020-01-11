import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPokemon } from 'app/shared/model/pokemon.model';

@Component({
  selector: 'jhi-pokemon-detail',
  templateUrl: './pokemon-detail.component.html'
})
export class PokemonDetailComponent implements OnInit {
  pokemon: IPokemon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pokemon }) => {
      this.pokemon = pokemon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
