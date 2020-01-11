import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEncounterType, EncounterType } from 'app/shared/model/encounter-type.model';
import { EncounterTypeService } from './encounter-type.service';

@Component({
  selector: 'jhi-encounter-type-update',
  templateUrl: './encounter-type-update.component.html'
})
export class EncounterTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected encounterTypeService: EncounterTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encounterType }) => {
      this.updateForm(encounterType);
    });
  }

  updateForm(encounterType: IEncounterType): void {
    this.editForm.patchValue({
      id: encounterType.id,
      key: encounterType.key,
      name: encounterType.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encounterType = this.createFromForm();
    if (encounterType.id !== undefined) {
      this.subscribeToSaveResponse(this.encounterTypeService.update(encounterType));
    } else {
      this.subscribeToSaveResponse(this.encounterTypeService.create(encounterType));
    }
  }

  private createFromForm(): IEncounterType {
    return {
      ...new EncounterType(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncounterType>>): void {
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
