import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { EncounterComponent } from './encounter.component';
import { EncounterDetailComponent } from './encounter-detail.component';
import { EncounterUpdateComponent } from './encounter-update.component';
import { EncounterDeleteDialogComponent } from './encounter-delete-dialog.component';
import { encounterRoute } from './encounter.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(encounterRoute)],
  declarations: [EncounterComponent, EncounterDetailComponent, EncounterUpdateComponent, EncounterDeleteDialogComponent],
  entryComponents: [EncounterDeleteDialogComponent]
})
export class PokemonEncounterModule {}
