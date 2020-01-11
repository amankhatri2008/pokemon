import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { UserPokemonDetailComponent } from 'app/entities/user-pokemon/user-pokemon-detail.component';
import { UserPokemon } from 'app/shared/model/user-pokemon.model';

describe('Component Tests', () => {
  describe('UserPokemon Management Detail Component', () => {
    let comp: UserPokemonDetailComponent;
    let fixture: ComponentFixture<UserPokemonDetailComponent>;
    const route = ({ data: of({ userPokemon: new UserPokemon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [UserPokemonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserPokemonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserPokemonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userPokemon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userPokemon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
