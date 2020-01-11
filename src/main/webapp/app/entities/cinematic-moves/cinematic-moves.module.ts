import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { CinematicMovesComponent } from './cinematic-moves.component';
import { CinematicMovesDetailComponent } from './cinematic-moves-detail.component';
import { CinematicMovesUpdateComponent } from './cinematic-moves-update.component';
import { CinematicMovesDeleteDialogComponent } from './cinematic-moves-delete-dialog.component';
import { cinematicMovesRoute } from './cinematic-moves.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(cinematicMovesRoute)],
  declarations: [
    CinematicMovesComponent,
    CinematicMovesDetailComponent,
    CinematicMovesUpdateComponent,
    CinematicMovesDeleteDialogComponent
  ],
  entryComponents: [CinematicMovesDeleteDialogComponent]
})
export class PokemonCinematicMovesModule {}
