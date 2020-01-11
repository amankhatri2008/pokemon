import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICostToEvolve } from 'app/shared/model/cost-to-evolve.model';
import { CostToEvolveService } from './cost-to-evolve.service';

@Component({
  templateUrl: './cost-to-evolve-delete-dialog.component.html'
})
export class CostToEvolveDeleteDialogComponent {
  costToEvolve?: ICostToEvolve;

  constructor(
    protected costToEvolveService: CostToEvolveService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.costToEvolveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('costToEvolveListModification');
      this.activeModal.close();
    });
  }
}
