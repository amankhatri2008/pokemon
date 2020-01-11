import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { BuddySizeComponent } from './buddy-size.component';
import { BuddySizeDetailComponent } from './buddy-size-detail.component';
import { BuddySizeUpdateComponent } from './buddy-size-update.component';
import { BuddySizeDeleteDialogComponent } from './buddy-size-delete-dialog.component';
import { buddySizeRoute } from './buddy-size.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(buddySizeRoute)],
  declarations: [BuddySizeComponent, BuddySizeDetailComponent, BuddySizeUpdateComponent, BuddySizeDeleteDialogComponent],
  entryComponents: [BuddySizeDeleteDialogComponent]
})
export class PokemonBuddySizeModule {}
