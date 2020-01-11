import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuickMoves, QuickMoves } from 'app/shared/model/quick-moves.model';
import { QuickMovesService } from './quick-moves.service';

@Component({
  selector: 'jhi-quick-moves-update',
  templateUrl: './quick-moves-update.component.html'
})
export class QuickMovesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected quickMovesService: QuickMovesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quickMoves }) => {
      this.updateForm(quickMoves);
    });
  }

  updateForm(quickMoves: IQuickMoves): void {
    this.editForm.patchValue({
      id: quickMoves.id,
      key: quickMoves.key,
      name: quickMoves.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quickMoves = this.createFromForm();
    if (quickMoves.id !== undefined) {
      this.subscribeToSaveResponse(this.quickMovesService.update(quickMoves));
    } else {
      this.subscribeToSaveResponse(this.quickMovesService.create(quickMoves));
    }
  }

  private createFromForm(): IQuickMoves {
    return {
      ...new QuickMoves(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuickMoves>>): void {
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
