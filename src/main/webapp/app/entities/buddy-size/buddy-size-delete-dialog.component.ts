import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBuddySize } from 'app/shared/model/buddy-size.model';
import { BuddySizeService } from './buddy-size.service';

@Component({
  templateUrl: './buddy-size-delete-dialog.component.html'
})
export class BuddySizeDeleteDialogComponent {
  buddySize?: IBuddySize;

  constructor(protected buddySizeService: BuddySizeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.buddySizeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('buddySizeListModification');
      this.activeModal.close();
    });
  }
}
