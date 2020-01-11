import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { UserPokemonComponent } from './user-pokemon.component';
import { UserPokemonDetailComponent } from './user-pokemon-detail.component';
import { UserPokemonUpdateComponent } from './user-pokemon-update.component';
import { UserPokemonDeleteDialogComponent } from './user-pokemon-delete-dialog.component';
import { userPokemonRoute } from './user-pokemon.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(userPokemonRoute)],
  declarations: [UserPokemonComponent, UserPokemonDetailComponent, UserPokemonUpdateComponent, UserPokemonDeleteDialogComponent],
  entryComponents: [UserPokemonDeleteDialogComponent]
})
export class PokemonUserPokemonModule {}
