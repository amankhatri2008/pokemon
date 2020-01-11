import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncounterType } from 'app/shared/model/encounter-type.model';
import { EncounterTypeService } from './encounter-type.service';

@Component({
  templateUrl: './encounter-type-delete-dialog.component.html'
})
export class EncounterTypeDeleteDialogComponent {
  encounterType?: IEncounterType;

  constructor(
    protected encounterTypeService: EncounterTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encounterTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encounterTypeListModification');
      this.activeModal.close();
    });
  }
}
