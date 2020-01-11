import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEvolutionType, EvolutionType } from 'app/shared/model/evolution-type.model';
import { EvolutionTypeService } from './evolution-type.service';

@Component({
  selector: 'jhi-evolution-type-update',
  templateUrl: './evolution-type-update.component.html'
})
export class EvolutionTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected evolutionTypeService: EvolutionTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evolutionType }) => {
      this.updateForm(evolutionType);
    });
  }

  updateForm(evolutionType: IEvolutionType): void {
    this.editForm.patchValue({
      id: evolutionType.id,
      key: evolutionType.key,
      name: evolutionType.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evolutionType = this.createFromForm();
    if (evolutionType.id !== undefined) {
      this.subscribeToSaveResponse(this.evolutionTypeService.update(evolutionType));
    } else {
      this.subscribeToSaveResponse(this.evolutionTypeService.create(evolutionType));
    }
  }

  private createFromForm(): IEvolutionType {
    return {
      ...new EvolutionType(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvolutionType>>): void {
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
}
