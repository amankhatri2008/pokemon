import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { TypesUpdateComponent } from 'app/entities/types/types-update.component';
import { TypesService } from 'app/entities/types/types.service';
import { Types } from 'app/shared/model/types.model';

describe('Component Tests', () => {
  describe('Types Management Update Component', () => {
    let comp: TypesUpdateComponent;
    let fixture: ComponentFixture<TypesUpdateComponent>;
    let service: TypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [TypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Types(123);
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
        const entity = new Types();
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
