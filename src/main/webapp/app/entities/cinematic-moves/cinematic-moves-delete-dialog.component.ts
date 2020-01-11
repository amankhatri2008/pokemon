import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICinematicMoves } from 'app/shared/model/cinematic-moves.model';
import { CinematicMovesService } from './cinematic-moves.service';

@Component({
  templateUrl: './cinematic-moves-delete-dialog.component.html'
})
export class CinematicMovesDeleteDialogComponent {
  cinematicMoves?: ICinematicMoves;

  constructor(
    protected cinematicMovesService: CinematicMovesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cinematicMovesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cinematicMovesListModification');
      this.activeModal.close();
    });
  }
}
