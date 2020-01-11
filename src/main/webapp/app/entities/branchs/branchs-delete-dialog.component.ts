import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBranchs } from 'app/shared/model/branchs.model';
import { BranchsService } from './branchs.service';

@Component({
  templateUrl: './branchs-delete-dialog.component.html'
})
export class BranchsDeleteDialogComponent {
  branchs?: IBranchs;

  constructor(protected branchsService: BranchsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.branchsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('branchsListModification');
      this.activeModal.close();
    });
  }
}
