import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EncounterDetailComponent } from 'app/entities/encounter/encounter-detail.component';
import { Encounter } from 'app/shared/model/encounter.model';

describe('Component Tests', () => {
  describe('Encounter Management Detail Component', () => {
    let comp: EncounterDetailComponent;
    let fixture: ComponentFixture<EncounterDetailComponent>;
    const route = ({ data: of({ encounter: new Encounter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EncounterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EncounterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncounterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encounter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encounter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
