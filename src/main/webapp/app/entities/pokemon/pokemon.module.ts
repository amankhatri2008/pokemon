import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { PokemonComponent } from './pokemon.component';
import { PokemonDetailComponent } from './pokemon-detail.component';
import { PokemonUpdateComponent } from './pokemon-update.component';
import { PokemonDeleteDialogComponent } from './pokemon-delete-dialog.component';
import { pokemonRoute } from './pokemon.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(pokemonRoute)],
  declarations: [PokemonComponent, PokemonDetailComponent, PokemonUpdateComponent, PokemonDeleteDialogComponent],
  entryComponents: [PokemonDeleteDialogComponent]
})
export class PokemonPokemonModule {}
