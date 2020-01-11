import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamily, Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html'
})
export class FamilyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected familyService: FamilyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.updateForm(family);
    });
  }

  updateForm(family: IFamily): void {
    this.editForm.patchValue({
      id: family.id,
      key: family.key,
      name: family.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
  }

  private createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>): void {
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
