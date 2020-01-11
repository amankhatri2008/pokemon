import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { BranchsComponent } from './branchs.component';
import { BranchsDetailComponent } from './branchs-detail.component';
import { BranchsUpdateComponent } from './branchs-update.component';
import { BranchsDeleteDialogComponent } from './branchs-delete-dialog.component';
import { branchsRoute } from './branchs.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(branchsRoute)],
  declarations: [BranchsComponent, BranchsDetailComponent, BranchsUpdateComponent, BranchsDeleteDialogComponent],
  entryComponents: [BranchsDeleteDialogComponent]
})
export class PokemonBranchsModule {}
