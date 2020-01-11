import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { CostToEvolveComponent } from './cost-to-evolve.component';
import { CostToEvolveDetailComponent } from './cost-to-evolve-detail.component';
import { CostToEvolveUpdateComponent } from './cost-to-evolve-update.component';
import { CostToEvolveDeleteDialogComponent } from './cost-to-evolve-delete-dialog.component';
import { costToEvolveRoute } from './cost-to-evolve.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(costToEvolveRoute)],
  declarations: [CostToEvolveComponent, CostToEvolveDetailComponent, CostToEvolveUpdateComponent, CostToEvolveDeleteDialogComponent],
  entryComponents: [CostToEvolveDeleteDialogComponent]
})
export class PokemonCostToEvolveModule {}
