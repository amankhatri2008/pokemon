import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICinematicMoves, CinematicMoves } from 'app/shared/model/cinematic-moves.model';
import { CinematicMovesService } from './cinematic-moves.service';

@Component({
  selector: 'jhi-cinematic-moves-update',
  templateUrl: './cinematic-moves-update.component.html'
})
export class CinematicMovesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [null, [Validators.required]]
  });

  constructor(protected cinematicMovesService: CinematicMovesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cinematicMoves }) => {
      this.updateForm(cinematicMoves);
    });
  }

  updateForm(cinematicMoves: ICinematicMoves): void {
    this.editForm.patchValue({
      id: cinematicMoves.id,
      key: cinematicMoves.key,
      name: cinematicMoves.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cinematicMoves = this.createFromForm();
    if (cinematicMoves.id !== undefined) {
      this.subscribeToSaveResponse(this.cinematicMovesService.update(cinematicMoves));
    } else {
      this.subscribeToSaveResponse(this.cinematicMovesService.create(cinematicMoves));
    }
  }

  private createFromForm(): ICinematicMoves {
    return {
      ...new CinematicMoves(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICinematicMoves>>): void {
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
