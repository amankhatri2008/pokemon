import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvolutionType } from 'app/shared/model/evolution-type.model';
import { EvolutionTypeService } from './evolution-type.service';

@Component({
  templateUrl: './evolution-type-delete-dialog.component.html'
})
export class EvolutionTypeDeleteDialogComponent {
  evolutionType?: IEvolutionType;

  constructor(
    protected evolutionTypeService: EvolutionTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evolutionTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('evolutionTypeListModification');
      this.activeModal.close();
    });
  }
}
