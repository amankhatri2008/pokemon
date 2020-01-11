import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { EvolutionTypeComponent } from './evolution-type.component';
import { EvolutionTypeDetailComponent } from './evolution-type-detail.component';
import { EvolutionTypeUpdateComponent } from './evolution-type-update.component';
import { EvolutionTypeDeleteDialogComponent } from './evolution-type-delete-dialog.component';
import { evolutionTypeRoute } from './evolution-type.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(evolutionTypeRoute)],
  declarations: [EvolutionTypeComponent, EvolutionTypeDetailComponent, EvolutionTypeUpdateComponent, EvolutionTypeDeleteDialogComponent],
  entryComponents: [EvolutionTypeDeleteDialogComponent]
})
export class PokemonEvolutionTypeModule {}
