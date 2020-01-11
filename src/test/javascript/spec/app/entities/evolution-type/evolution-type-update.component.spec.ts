import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EvolutionTypeUpdateComponent } from 'app/entities/evolution-type/evolution-type-update.component';
import { EvolutionTypeService } from 'app/entities/evolution-type/evolution-type.service';
import { EvolutionType } from 'app/shared/model/evolution-type.model';

describe('Component Tests', () => {
  describe('EvolutionType Management Update Component', () => {
    let comp: EvolutionTypeUpdateComponent;
    let fixture: ComponentFixture<EvolutionTypeUpdateComponent>;
    let service: EvolutionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EvolutionTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EvolutionTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvolutionTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EvolutionTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EvolutionType(123);
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
        const entity = new EvolutionType();
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
