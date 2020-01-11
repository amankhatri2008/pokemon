import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'types',
        loadChildren: () => import('./types/types.module').then(m => m.PokemonTypesModule)
      },
      {
        path: 'buddy-size',
        loadChildren: () => import('./buddy-size/buddy-size.module').then(m => m.PokemonBuddySizeModule)
      },
      {
        path: 'cinematic-moves',
        loadChildren: () => import('./cinematic-moves/cinematic-moves.module').then(m => m.PokemonCinematicMovesModule)
      },
      {
        path: 'quick-moves',
        loadChildren: () => import('./quick-moves/quick-moves.module').then(m => m.PokemonQuickMovesModule)
      },
      {
        path: 'family',
        loadChildren: () => import('./family/family.module').then(m => m.PokemonFamilyModule)
      },
      {
        path: 'gender',
        loadChildren: () => import('./gender/gender.module').then(m => m.PokemonGenderModule)
      },
      {
        path: 'form',
        loadChildren: () => import('./form/form.module').then(m => m.PokemonFormModule)
      },
      {
        path: 'branchs',
        loadChildren: () => import('./branchs/branchs.module').then(m => m.PokemonBranchsModule)
      },
      {
        path: 'encounter-type',
        loadChildren: () => import('./encounter-type/encounter-type.module').then(m => m.PokemonEncounterTypeModule)
      },
      {
        path: 'encounter',
        loadChildren: () => import('./encounter/encounter.module').then(m => m.PokemonEncounterModule)
      },
      {
        path: 'evolution-type',
        loadChildren: () => import('./evolution-type/evolution-type.module').then(m => m.PokemonEvolutionTypeModule)
      },
      {
        path: 'cost-to-evolve',
        loadChildren: () => import('./cost-to-evolve/cost-to-evolve.module').then(m => m.PokemonCostToEvolveModule)
      },
      {
        path: 'pokemon',
        loadChildren: () => import('./pokemon/pokemon.module').then(m => m.PokemonPokemonModule)
      },
      {
        path: 'user-pokemon',
        loadChildren: () => import('./user-pokemon/user-pokemon.module').then(m => m.PokemonUserPokemonModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PokemonEntityModule {}
