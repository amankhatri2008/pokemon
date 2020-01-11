import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { EncounterTypeComponent } from './encounter-type.component';
import { EncounterTypeDetailComponent } from './encounter-type-detail.component';
import { EncounterTypeUpdateComponent } from './encounter-type-update.component';
import { EncounterTypeDeleteDialogComponent } from './encounter-type-delete-dialog.component';
import { encounterTypeRoute } from './encounter-type.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(encounterTypeRoute)],
  declarations: [EncounterTypeComponent, EncounterTypeDetailComponent, EncounterTypeUpdateComponent, EncounterTypeDeleteDialogComponent],
  entryComponents: [EncounterTypeDeleteDialogComponent]
})
export class PokemonEncounterTypeModule {}
