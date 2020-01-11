import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EncounterUpdateComponent } from 'app/entities/encounter/encounter-update.component';
import { EncounterService } from 'app/entities/encounter/encounter.service';
import { Encounter } from 'app/shared/model/encounter.model';

describe('Component Tests', () => {
  describe('Encounter Management Update Component', () => {
    let comp: EncounterUpdateComponent;
    let fixture: ComponentFixture<EncounterUpdateComponent>;
    let service: EncounterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EncounterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EncounterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncounterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncounterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Encounter(123);
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
        const entity = new Encounter();
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
