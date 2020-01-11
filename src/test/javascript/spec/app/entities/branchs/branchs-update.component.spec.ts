import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { BranchsUpdateComponent } from 'app/entities/branchs/branchs-update.component';
import { BranchsService } from 'app/entities/branchs/branchs.service';
import { Branchs } from 'app/shared/model/branchs.model';

describe('Component Tests', () => {
  describe('Branchs Management Update Component', () => {
    let comp: BranchsUpdateComponent;
    let fixture: ComponentFixture<BranchsUpdateComponent>;
    let service: BranchsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [BranchsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BranchsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BranchsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BranchsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Branchs(123);
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
        const entity = new Branchs();
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
