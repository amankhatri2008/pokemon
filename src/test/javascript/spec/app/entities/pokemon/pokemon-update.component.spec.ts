import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { PokemonUpdateComponent } from 'app/entities/pokemon/pokemon-update.component';
import { PokemonService } from 'app/entities/pokemon/pokemon.service';
import { Pokemon } from 'app/shared/model/pokemon.model';

describe('Component Tests', () => {
  describe('Pokemon Management Update Component', () => {
    let comp: PokemonUpdateComponent;
    let fixture: ComponentFixture<PokemonUpdateComponent>;
    let service: PokemonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [PokemonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PokemonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PokemonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PokemonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pokemon(123);
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
        const entity = new Pokemon();
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
