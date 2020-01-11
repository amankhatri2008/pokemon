import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { CostToEvolveUpdateComponent } from 'app/entities/cost-to-evolve/cost-to-evolve-update.component';
import { CostToEvolveService } from 'app/entities/cost-to-evolve/cost-to-evolve.service';
import { CostToEvolve } from 'app/shared/model/cost-to-evolve.model';

describe('Component Tests', () => {
  describe('CostToEvolve Management Update Component', () => {
    let comp: CostToEvolveUpdateComponent;
    let fixture: ComponentFixture<CostToEvolveUpdateComponent>;
    let service: CostToEvolveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [CostToEvolveUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CostToEvolveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CostToEvolveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CostToEvolveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CostToEvolve(123);
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
        const entity = new CostToEvolve();
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
