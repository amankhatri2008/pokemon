import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { PokemonDetailComponent } from 'app/entities/pokemon/pokemon-detail.component';
import { Pokemon } from 'app/shared/model/pokemon.model';

describe('Component Tests', () => {
  describe('Pokemon Management Detail Component', () => {
    let comp: PokemonDetailComponent;
    let fixture: ComponentFixture<PokemonDetailComponent>;
    const route = ({ data: of({ pokemon: new Pokemon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [PokemonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PokemonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PokemonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pokemon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pokemon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
