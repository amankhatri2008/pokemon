import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { CinematicMovesUpdateComponent } from 'app/entities/cinematic-moves/cinematic-moves-update.component';
import { CinematicMovesService } from 'app/entities/cinematic-moves/cinematic-moves.service';
import { CinematicMoves } from 'app/shared/model/cinematic-moves.model';

describe('Component Tests', () => {
  describe('CinematicMoves Management Update Component', () => {
    let comp: CinematicMovesUpdateComponent;
    let fixture: ComponentFixture<CinematicMovesUpdateComponent>;
    let service: CinematicMovesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [CinematicMovesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CinematicMovesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CinematicMovesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CinematicMovesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CinematicMoves(123);
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
        const entity = new CinematicMoves();
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
