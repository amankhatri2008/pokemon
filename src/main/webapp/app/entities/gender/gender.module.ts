import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { GenderComponent } from './gender.component';
import { GenderDetailComponent } from './gender-detail.component';
import { GenderUpdateComponent } from './gender-update.component';
import { GenderDeleteDialogComponent } from './gender-delete-dialog.component';
import { genderRoute } from './gender.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(genderRoute)],
  declarations: [GenderComponent, GenderDetailComponent, GenderUpdateComponent, GenderDeleteDialogComponent],
  entryComponents: [GenderDeleteDialogComponent]
})
export class PokemonGenderModule {}
