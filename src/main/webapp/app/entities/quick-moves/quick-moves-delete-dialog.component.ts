import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuickMoves } from 'app/shared/model/quick-moves.model';
import { QuickMovesService } from './quick-moves.service';

@Component({
  templateUrl: './quick-moves-delete-dialog.component.html'
})
export class QuickMovesDeleteDialogComponent {
  quickMoves?: IQuickMoves;

  constructor(
    protected quickMovesService: QuickMovesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quickMovesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('quickMovesListModification');
      this.activeModal.close();
    });
  }
}
