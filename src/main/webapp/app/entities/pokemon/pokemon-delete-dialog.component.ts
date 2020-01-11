import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPokemon } from 'app/shared/model/pokemon.model';
import { PokemonService } from './pokemon.service';

@Component({
  templateUrl: './pokemon-delete-dialog.component.html'
})
export class PokemonDeleteDialogComponent {
  pokemon?: IPokemon;

  constructor(protected pokemonService: PokemonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pokemonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pokemonListModification');
      this.activeModal.close();
    });
  }
}
