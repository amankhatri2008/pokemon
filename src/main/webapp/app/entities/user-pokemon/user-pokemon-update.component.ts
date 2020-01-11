import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserPokemon, UserPokemon } from 'app/shared/model/user-pokemon.model';
import { UserPokemonService } from './user-pokemon.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPokemon } from 'app/shared/model/pokemon.model';
import { PokemonService } from 'app/entities/pokemon/pokemon.service';

type SelectableEntity = IUser | IPokemon;

@Component({
  selector: 'jhi-user-pokemon-update',
  templateUrl: './user-pokemon-update.component.html'
})
export class UserPokemonUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  pokemons: IPokemon[] = [];

  editForm = this.fb.group({
    id: [],
    userManies: [],
    pokemonManies: []
  });

  constructor(
    protected userPokemonService: UserPokemonService,
    protected userService: UserService,
    protected pokemonService: PokemonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPokemon }) => {
      this.updateForm(userPokemon);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.pokemonService
        .query()
        .pipe(
          map((res: HttpResponse<IPokemon[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPokemon[]) => (this.pokemons = resBody));
    });
  }

  updateForm(userPokemon: IUserPokemon): void {
    this.editForm.patchValue({
      id: userPokemon.id,
      userManies: userPokemon.userManies,
      pokemonManies: userPokemon.pokemonManies
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userPokemon = this.createFromForm();
    if (userPokemon.id !== undefined) {
      this.subscribeToSaveResponse(this.userPokemonService.update(userPokemon));
    } else {
      this.subscribeToSaveResponse(this.userPokemonService.create(userPokemon));
    }
  }

  private createFromForm(): IUserPokemon {
    return {
      ...new UserPokemon(),
      id: this.editForm.get(['id'])!.value,
      userManies: this.editForm.get(['userManies'])!.value,
      pokemonManies: this.editForm.get(['pokemonManies'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPokemon>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
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
