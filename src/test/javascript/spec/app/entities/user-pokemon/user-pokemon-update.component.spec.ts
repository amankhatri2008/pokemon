import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { UserPokemonUpdateComponent } from 'app/entities/user-pokemon/user-pokemon-update.component';
import { UserPokemonService } from 'app/entities/user-pokemon/user-pokemon.service';
import { UserPokemon } from 'app/shared/model/user-pokemon.model';

describe('Component Tests', () => {
  describe('UserPokemon Management Update Component', () => {
    let comp: UserPokemonUpdateComponent;
    let fixture: ComponentFixture<UserPokemonUpdateComponent>;
    let service: UserPokemonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [UserPokemonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserPokemonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserPokemonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserPokemonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserPokemon(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserPokemon();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
