import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { FamilyComponent } from './family.component';
import { FamilyDetailComponent } from './family-detail.component';
import { FamilyUpdateComponent } from './family-update.component';
import { FamilyDeleteDialogComponent } from './family-delete-dialog.component';
import { familyRoute } from './family.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(familyRoute)],
  declarations: [FamilyComponent, FamilyDetailComponent, FamilyUpdateComponent, FamilyDeleteDialogComponent],
  entryComponents: [FamilyDeleteDialogComponent]
})
export class PokemonFamilyModule {}
