import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBuddySize, BuddySize } from 'app/shared/model/buddy-size.model';
import { BuddySizeService } from './buddy-size.service';

@Component({
  selector: 'jhi-buddy-size-update',
  templateUrl: './buddy-size-update.component.html'
})
export class BuddySizeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected buddySizeService: BuddySizeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buddySize }) => {
      this.updateForm(buddySize);
    });
  }

  updateForm(buddySize: IBuddySize): void {
    this.editForm.patchValue({
      id: buddySize.id,
      key: buddySize.key,
      name: buddySize.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const buddySize = this.createFromForm();
    if (buddySize.id !== undefined) {
      this.subscribeToSaveResponse(this.buddySizeService.update(buddySize));
    } else {
      this.subscribeToSaveResponse(this.buddySizeService.create(buddySize));
    }
  }

  private createFromForm(): IBuddySize {
    return {
      ...new BuddySize(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuddySize>>): void {
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
