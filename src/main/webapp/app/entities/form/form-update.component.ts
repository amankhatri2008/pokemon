import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IForm, Form } from 'app/shared/model/form.model';
import { FormService } from './form.service';

@Component({
  selector: 'jhi-form-update',
  templateUrl: './form-update.component.html'
})
export class FormUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: []
  });

  constructor(protected formService: FormService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ form }) => {
      this.updateForm(form);
    });
  }

  updateForm(form: IForm): void {
    this.editForm.patchValue({
      id: form.id,
      key: form.key,
      name: form.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const form = this.createFromForm();
    if (form.id !== undefined) {
      this.subscribeToSaveResponse(this.formService.update(form));
    } else {
      this.subscribeToSaveResponse(this.formService.create(form));
    }
  }

  private createFromForm(): IForm {
    return {
      ...new Form(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForm>>): void {
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
