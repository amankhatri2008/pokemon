import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EncounterTypeDetailComponent } from 'app/entities/encounter-type/encounter-type-detail.component';
import { EncounterType } from 'app/shared/model/encounter-type.model';

describe('Component Tests', () => {
  describe('EncounterType Management Detail Component', () => {
    let comp: EncounterTypeDetailComponent;
    let fixture: ComponentFixture<EncounterTypeDetailComponent>;
    const route = ({ data: of({ encounterType: new EncounterType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EncounterTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EncounterTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncounterTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encounterType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encounterType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
