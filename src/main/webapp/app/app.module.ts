import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PokemonSharedModule } from 'app/shared/shared.module';
import { PokemonCoreModule } from 'app/core/core.module';
import { PokemonAppRoutingModule } from './app-routing.module';
import { PokemonHomeModule } from './home/home.module';
import { PokemonEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PokemonSharedModule,
    PokemonCoreModule,
    PokemonHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PokemonEntityModule,
    PokemonAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class PokemonAppModule {}
