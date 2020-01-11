import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { EvolutionTypeDetailComponent } from 'app/entities/evolution-type/evolution-type-detail.component';
import { EvolutionType } from 'app/shared/model/evolution-type.model';

describe('Component Tests', () => {
  describe('EvolutionType Management Detail Component', () => {
    let comp: EvolutionTypeDetailComponent;
    let fixture: ComponentFixture<EvolutionTypeDetailComponent>;
    const route = ({ data: of({ evolutionType: new EvolutionType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [EvolutionTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EvolutionTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvolutionTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load evolutionType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evolutionType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
