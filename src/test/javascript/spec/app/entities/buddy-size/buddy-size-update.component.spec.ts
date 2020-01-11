import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { BuddySizeUpdateComponent } from 'app/entities/buddy-size/buddy-size-update.component';
import { BuddySizeService } from 'app/entities/buddy-size/buddy-size.service';
import { BuddySize } from 'app/shared/model/buddy-size.model';

describe('Component Tests', () => {
  describe('BuddySize Management Update Component', () => {
    let comp: BuddySizeUpdateComponent;
    let fixture: ComponentFixture<BuddySizeUpdateComponent>;
    let service: BuddySizeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [BuddySizeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BuddySizeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BuddySizeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BuddySizeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BuddySize(123);
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
        const entity = new BuddySize();
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
