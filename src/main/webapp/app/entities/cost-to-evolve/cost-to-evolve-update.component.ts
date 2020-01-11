import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICostToEvolve, CostToEvolve } from 'app/shared/model/cost-to-evolve.model';
import { CostToEvolveService } from './cost-to-evolve.service';
import { IEvolutionType } from 'app/shared/model/evolution-type.model';
import { EvolutionTypeService } from 'app/entities/evolution-type/evolution-type.service';

@Component({
  selector: 'jhi-cost-to-evolve-update',
  templateUrl: './cost-to-evolve-update.component.html'
})
export class CostToEvolveUpdateComponent implements OnInit {
  isSaving = false;

  evolutiontypes: IEvolutionType[] = [];

  editForm = this.fb.group({
    id: [],
    candyCost: [],
    kmBuddyDistance: [],
    evolutionType: []
  });

  constructor(
    protected costToEvolveService: CostToEvolveService,
    protected evolutionTypeService: EvolutionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ costToEvolve }) => {
      this.updateForm(costToEvolve);

      this.evolutionTypeService
        .query()
        .pipe(
          map((res: HttpResponse<IEvolutionType[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEvolutionType[]) => (this.evolutiontypes = resBody));
    });
  }

  updateForm(costToEvolve: ICostToEvolve): void {
    this.editForm.patchValue({
      id: costToEvolve.id,
      candyCost: costToEvolve.candyCost,
      kmBuddyDistance: costToEvolve.kmBuddyDistance,
      evolutionType: costToEvolve.evolutionType
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const costToEvolve = this.createFromForm();
    if (costToEvolve.id !== undefined) {
      this.subscribeToSaveResponse(this.costToEvolveService.update(costToEvolve));
    } else {
      this.subscribeToSaveResponse(this.costToEvolveService.create(costToEvolve));
    }
  }

  private createFromForm(): ICostToEvolve {
    return {
      ...new CostToEvolve(),
      id: this.editForm.get(['id'])!.value,
      candyCost: this.editForm.get(['candyCost'])!.value,
      kmBuddyDistance: this.editForm.get(['kmBuddyDistance'])!.value,
      evolutionType: this.editForm.get(['evolutionType'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICostToEvolve>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEvolutionType): any {
    return item.id;
  }
}
