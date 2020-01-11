import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokemonSharedModule } from 'app/shared/shared.module';
import { TypesComponent } from './types.component';
import { TypesDetailComponent } from './types-detail.component';
import { TypesUpdateComponent } from './types-update.component';
import { TypesDeleteDialogComponent } from './types-delete-dialog.component';
import { typesRoute } from './types.route';

@NgModule({
  imports: [PokemonSharedModule, RouterModule.forChild(typesRoute)],
  declarations: [TypesComponent, TypesDetailComponent, TypesUpdateComponent, TypesDeleteDialogComponent],
  entryComponents: [TypesDeleteDialogComponent]
})
export class PokemonTypesModule {}
