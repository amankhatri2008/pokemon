import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserPokemon } from 'app/shared/model/user-pokemon.model';
import { UserPokemonService } from './user-pokemon.service';

@Component({
  templateUrl: './user-pokemon-delete-dialog.component.html'
})
export class UserPokemonDeleteDialogComponent {
  userPokemon?: IUserPokemon;

  constructor(
    protected userPokemonService: UserPokemonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPokemonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userPokemonListModification');
      this.activeModal.close();
    });
  }
}
