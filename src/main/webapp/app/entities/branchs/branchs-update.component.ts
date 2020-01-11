import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBranchs, Branchs } from 'app/shared/model/branchs.model';
import { BranchsService } from './branchs.service';

@Component({
  selector: 'jhi-branchs-update',
  templateUrl: './branchs-update.component.html'
})
export class BranchsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected branchsService: BranchsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branchs }) => {
      this.updateForm(branchs);
    });
  }

  updateForm(branchs: IBranchs): void {
    this.editForm.patchValue({
      id: branchs.id,
      key: branchs.key,
      name: branchs.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const branchs = this.createFromForm();
    if (branchs.id !== undefined) {
      this.subscribeToSaveResponse(this.branchsService.update(branchs));
    } else {
      this.subscribeToSaveResponse(this.branchsService.create(branchs));
    }
  }

  private createFromForm(): IBranchs {
    return {
      ...new Branchs(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranchs>>): void {
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
