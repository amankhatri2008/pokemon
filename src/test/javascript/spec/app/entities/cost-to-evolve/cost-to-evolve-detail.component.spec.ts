import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { CostToEvolveDetailComponent } from 'app/entities/cost-to-evolve/cost-to-evolve-detail.component';
import { CostToEvolve } from 'app/shared/model/cost-to-evolve.model';

describe('Component Tests', () => {
  describe('CostToEvolve Management Detail Component', () => {
    let comp: CostToEvolveDetailComponent;
    let fixture: ComponentFixture<CostToEvolveDetailComponent>;
    const route = ({ data: of({ costToEvolve: new CostToEvolve(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [CostToEvolveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CostToEvolveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CostToEvolveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load costToEvolve on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.costToEvolve).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
