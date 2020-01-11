import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypes, Types } from 'app/shared/model/types.model';
import { TypesService } from './types.service';

@Component({
  selector: 'jhi-types-update',
  templateUrl: './types-update.component.html'
})
export class TypesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    nameId: []
  });

  constructor(protected typesService: TypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ types }) => {
      this.updateForm(types);
    });
  }

  updateForm(types: ITypes): void {
    this.editForm.patchValue({
      id: types.id,
      name: types.name,
      nameId: types.nameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const types = this.createFromForm();
    if (types.id !== undefined) {
      this.subscribeToSaveResponse(this.typesService.update(types));
    } else {
      this.subscribeToSaveResponse(this.typesService.create(types));
    }
  }

  private createFromForm(): ITypes {
    return {
      ...new Types(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameId: this.editForm.get(['nameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypes>>): void {
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
