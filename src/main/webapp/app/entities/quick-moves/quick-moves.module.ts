import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { QuickMovesComponent } from './quick-moves.component';
import { QuickMovesDetailComponent } from './quick-moves-detail.component';
import { QuickMovesUpdateComponent } from './quick-moves-update.component';
import { QuickMovesDeleteDialogComponent } from './quick-moves-delete-dialog.component';
import { quickMovesRoute } from './quick-moves.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(quickMovesRoute)],
  declarations: [QuickMovesComponent, QuickMovesDetailComponent, QuickMovesUpdateComponent, QuickMovesDeleteDialogComponent],
  entryComponents: [QuickMovesDeleteDialogComponent]
})
export class PokemonQuickMovesModule {}
