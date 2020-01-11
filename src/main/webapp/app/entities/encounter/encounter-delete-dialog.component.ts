import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncounter } from 'app/shared/model/encounter.model';
import { EncounterService } from './encounter.service';

@Component({
  templateUrl: './encounter-delete-dialog.component.html'
})
export class EncounterDeleteDialogComponent {
  encounter?: IEncounter;

  constructor(protected encounterService: EncounterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encounterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encounterListModification');
      this.activeModal.close();
    });
  }
}
