import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPokemon, Pokemon } from 'app/shared/model/pokemon.model';
import { PokemonService } from './pokemon.service';
import { IBuddySize } from 'app/shared/model/buddy-size.model';
import { BuddySizeService } from 'app/entities/buddy-size/buddy-size.service';
import { ICinematicMoves } from 'app/shared/model/cinematic-moves.model';
import { CinematicMovesService } from 'app/entities/cinematic-moves/cinematic-moves.service';
import { IQuickMoves } from 'app/shared/model/quick-moves.model';
import { QuickMovesService } from 'app/entities/quick-moves/quick-moves.service';
import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from 'app/entities/family/family.service';
import { IForm } from 'app/shared/model/form.model';
import { FormService } from 'app/entities/form/form.service';

type SelectableEntity = IBuddySize | ICinematicMoves | IQuickMoves | IFamily | IForm;

type SelectableManyToManyEntity = ICinematicMoves | IQuickMoves | IForm;

@Component({
  selector: 'jhi-pokemon-update',
  templateUrl: './pokemon-update.component.html'
})
export class PokemonUpdateComponent implements OnInit {
  isSaving = false;

  buddysizes: IBuddySize[] = [];

  cinematicmoves: ICinematicMoves[] = [];

  quickmoves: IQuickMoves[] = [];

  families: IFamily[] = [];

  forms: IForm[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    animationTime0: [],
    animationTime1: [],
    animationTime2: [],
    animationTime3: [],
    animationTime4: [],
    animationTime5: [],
    animationTime6: [],
    animationTime7: [],
    animationTime8: [],
    height: [],
    modelHeight: [],
    kmBuddyDistance: [],
    weight: [],
    modelScale: [],
    maxCP: [],
    cylinderGround: [],
    cylinderRadius: [],
    diskRadius: [],
    key: [],
    cinematicMovesId: [],
    baseAttack: [],
    baseDefense: [],
    baseStamina: [],
    buddySize: [],
    cinematicMovesManies: [],
    quickMovesManies: [],
    family: [],
    formManies: []
  });

  constructor(
    protected pokemonService: PokemonService,
    protected buddySizeService: BuddySizeService,
    protected cinematicMovesService: CinematicMovesService,
    protected quickMovesService: QuickMovesService,
    protected familyService: FamilyService,
    protected formService: FormService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pokemon }) => {
      this.updateForm(pokemon);

      this.buddySizeService
        .query()
        .pipe(
          map((res: HttpResponse<IBuddySize[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBuddySize[]) => (this.buddysizes = resBody));

      this.cinematicMovesService
        .query()
        .pipe(
          map((res: HttpResponse<ICinematicMoves[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICinematicMoves[]) => (this.cinematicmoves = resBody));

      this.quickMovesService
        .query()
        .pipe(
          map((res: HttpResponse<IQuickMoves[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IQuickMoves[]) => (this.quickmoves = resBody));

      this.familyService
        .query()
        .pipe(
          map((res: HttpResponse<IFamily[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IFamily[]) => (this.families = resBody));

      this.formService
        .query()
        .pipe(
          map((res: HttpResponse<IForm[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IForm[]) => (this.forms = resBody));
    });
  }

  updateForm(pokemon: IPokemon): void {
    this.editForm.patchValue({
      id: pokemon.id,
      name: pokemon.name,
      animationTime0: pokemon.animationTime0,
      animationTime1: pokemon.animationTime1,
      animationTime2: pokemon.animationTime2,
      animationTime3: pokemon.animationTime3,
      animationTime4: pokemon.animationTime4,
      animationTime5: pokemon.animationTime5,
      animationTime6: pokemon.animationTime6,
      animationTime7: pokemon.animationTime7,
      animationTime8: pokemon.animationTime8,
      height: pokemon.height,
      modelHeight: pokemon.modelHeight,
      kmBuddyDistance: pokemon.kmBuddyDistance,
      weight: pokemon.weight,
      modelScale: pokemon.modelScale,
      maxCP: pokemon.maxCP,
      cylinderGround: pokemon.cylinderGround,
      cylinderRadius: pokemon.cylinderRadius,
      diskRadius: pokemon.diskRadius,
      key: pokemon.key,
      cinematicMovesId: pokemon.cinematicMovesId,
      baseAttack: pokemon.baseAttack,
      baseDefense: pokemon.baseDefense,
      baseStamina: pokemon.baseStamina,
      buddySize: pokemon.buddySize,
      cinematicMovesManies: pokemon.cinematicMovesManies,
      quickMovesManies: pokemon.quickMovesManies,
      family: pokemon.family,
      formManies: pokemon.formManies
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pokemon = this.createFromForm();
    if (pokemon.id !== undefined) {
      this.subscribeToSaveResponse(this.pokemonService.update(pokemon));
    } else {
      this.subscribeToSaveResponse(this.pokemonService.create(pokemon));
    }
  }

  private createFromForm(): IPokemon {
    return {
      ...new Pokemon(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      animationTime0: this.editForm.get(['animationTime0'])!.value,
      animationTime1: this.editForm.get(['animationTime1'])!.value,
      animationTime2: this.editForm.get(['animationTime2'])!.value,
      animationTime3: this.editForm.get(['animationTime3'])!.value,
      animationTime4: this.editForm.get(['animationTime4'])!.value,
      animationTime5: this.editForm.get(['animationTime5'])!.value,
      animationTime6: this.editForm.get(['animationTime6'])!.value,
      animationTime7: this.editForm.get(['animationTime7'])!.value,
      animationTime8: this.editForm.get(['animationTime8'])!.value,
      height: this.editForm.get(['height'])!.value,
      modelHeight: this.editForm.get(['modelHeight'])!.value,
      kmBuddyDistance: this.editForm.get(['kmBuddyDistance'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      modelScale: this.editForm.get(['modelScale'])!.value,
      maxCP: this.editForm.get(['maxCP'])!.value,
      cylinderGround: this.editForm.get(['cylinderGround'])!.value,
      cylinderRadius: this.editForm.get(['cylinderRadius'])!.value,
      diskRadius: this.editForm.get(['diskRadius'])!.value,
      key: this.editForm.get(['key'])!.value,
      cinematicMovesId: this.editForm.get(['cinematicMovesId'])!.value,
      baseAttack: this.editForm.get(['baseAttack'])!.value,
      baseDefense: this.editForm.get(['baseDefense'])!.value,
      baseStamina: this.editForm.get(['baseStamina'])!.value,
      buddySize: this.editForm.get(['buddySize'])!.value,
      cinematicMovesManies: this.editForm.get(['cinematicMovesManies'])!.value,
      quickMovesManies: this.editForm.get(['quickMovesManies'])!.value,
      family: this.editForm.get(['family'])!.value,
      formManies: this.editForm.get(['formManies'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPokemon>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
