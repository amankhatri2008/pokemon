import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypes } from 'app/shared/model/types.model';
import { TypesService } from './types.service';

@Component({
  templateUrl: './types-delete-dialog.component.html'
})
export class TypesDeleteDialogComponent {
  types?: ITypes;

  constructor(protected typesService: TypesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typesListModification');
      this.activeModal.close();
    });
  }
}
