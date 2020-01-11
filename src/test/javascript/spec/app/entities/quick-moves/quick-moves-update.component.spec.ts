import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { QuickMovesUpdateComponent } from 'app/entities/quick-moves/quick-moves-update.component';
import { QuickMovesService } from 'app/entities/quick-moves/quick-moves.service';
import { QuickMoves } from 'app/shared/model/quick-moves.model';

describe('Component Tests', () => {
  describe('QuickMoves Management Update Component', () => {
    let comp: QuickMovesUpdateComponent;
    let fixture: ComponentFixture<QuickMovesUpdateComponent>;
    let service: QuickMovesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [QuickMovesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuickMovesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuickMovesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuickMovesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuickMoves(123);
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
        const entity = new QuickMoves();
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
