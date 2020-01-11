import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEncounter, Encounter } from 'app/shared/model/encounter.model';
import { EncounterService } from './encounter.service';
import { IEncounterType } from 'app/shared/model/encounter-type.model';
import { EncounterTypeService } from 'app/entities/encounter-type/encounter-type.service';
import { IGender } from 'app/shared/model/gender.model';
import { GenderService } from 'app/entities/gender/gender.service';

type SelectableEntity = IEncounterType | IGender;

@Component({
  selector: 'jhi-encounter-update',
  templateUrl: './encounter-update.component.html'
})
export class EncounterUpdateComponent implements OnInit {
  isSaving = false;

  encountertypes: IEncounterType[] = [];

  genders: IGender[] = [];

  editForm = this.fb.group({
    id: [],
    attackProbability: [],
    attackTimer: [],
    baseFleeRate: [],
    baseCaptureRate: [],
    cameraDistance: [],
    collisionRadius: [],
    dodgeDistance: [],
    dodgeProbability: [],
    jumpTime: [],
    maxPokemonActionFrequency: [],
    minPokemonActionFrequency: [],
    encounterType: [],
    gender: []
  });

  constructor(
    protected encounterService: EncounterService,
    protected encounterTypeService: EncounterTypeService,
    protected genderService: GenderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encounter }) => {
      this.updateForm(encounter);

      this.encounterTypeService
        .query()
        .pipe(
          map((res: HttpResponse<IEncounterType[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEncounterType[]) => (this.encountertypes = resBody));

      this.genderService
        .query()
        .pipe(
          map((res: HttpResponse<IGender[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGender[]) => (this.genders = resBody));
    });
  }

  updateForm(encounter: IEncounter): void {
    this.editForm.patchValue({
      id: encounter.id,
      attackProbability: encounter.attackProbability,
      attackTimer: encounter.attackTimer,
      baseFleeRate: encounter.baseFleeRate,
      baseCaptureRate: encounter.baseCaptureRate,
      cameraDistance: encounter.cameraDistance,
      collisionRadius: encounter.collisionRadius,
      dodgeDistance: encounter.dodgeDistance,
      dodgeProbability: encounter.dodgeProbability,
      jumpTime: encounter.jumpTime,
      maxPokemonActionFrequency: encounter.maxPokemonActionFrequency,
      minPokemonActionFrequency: encounter.minPokemonActionFrequency,
      encounterType: encounter.encounterType,
      gender: encounter.gender
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encounter = this.createFromForm();
    if (encounter.id !== undefined) {
      this.subscribeToSaveResponse(this.encounterService.update(encounter));
    } else {
      this.subscribeToSaveResponse(this.encounterService.create(encounter));
    }
  }

  private createFromForm(): IEncounter {
    return {
      ...new Encounter(),
      id: this.editForm.get(['id'])!.value,
      attackProbability: this.editForm.get(['attackProbability'])!.value,
      attackTimer: this.editForm.get(['attackTimer'])!.value,
      baseFleeRate: this.editForm.get(['baseFleeRate'])!.value,
      baseCaptureRate: this.editForm.get(['baseCaptureRate'])!.value,
      cameraDistance: this.editForm.get(['cameraDistance'])!.value,
      collisionRadius: this.editForm.get(['collisionRadius'])!.value,
      dodgeDistance: this.editForm.get(['dodgeDistance'])!.value,
      dodgeProbability: this.editForm.get(['dodgeProbability'])!.value,
      jumpTime: this.editForm.get(['jumpTime'])!.value,
      maxPokemonActionFrequency: this.editForm.get(['maxPokemonActionFrequency'])!.value,
      minPokemonActionFrequency: this.editForm.get(['minPokemonActionFrequency'])!.value,
      encounterType: this.editForm.get(['encounterType'])!.value,
      gender: this.editForm.get(['gender'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncounter>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
