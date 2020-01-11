import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { CinematicMovesDetailComponent } from 'app/entities/cinematic-moves/cinematic-moves-detail.component';
import { CinematicMoves } from 'app/shared/model/cinematic-moves.model';

describe('Component Tests', () => {
  describe('CinematicMoves Management Detail Component', () => {
    let comp: CinematicMovesDetailComponent;
    let fixture: ComponentFixture<CinematicMovesDetailComponent>;
    const route = ({ data: of({ cinematicMoves: new CinematicMoves(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [CinematicMovesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CinematicMovesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CinematicMovesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cinematicMoves on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cinematicMoves).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
