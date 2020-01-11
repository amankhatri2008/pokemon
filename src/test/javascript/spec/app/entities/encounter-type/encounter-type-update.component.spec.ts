import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EncounterTypeUpdateComponent } from 'app/entities/encounter-type/encounter-type-update.component';
import { EncounterTypeService } from 'app/entities/encounter-type/encounter-type.service';
import { EncounterType } from 'app/shared/model/encounter-type.model';

describe('Component Tests', () => {
  describe('EncounterType Management Update Component', () => {
    let comp: EncounterTypeUpdateComponent;
    let fixture: ComponentFixture<EncounterTypeUpdateComponent>;
    let service: EncounterTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EncounterTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EncounterTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncounterTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncounterTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EncounterType(123);
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
        const entity = new EncounterType();
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
