import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { QuickMovesDetailComponent } from 'app/entities/quick-moves/quick-moves-detail.component';
import { QuickMoves } from 'app/shared/model/quick-moves.model';

describe('Component Tests', () => {
  describe('QuickMoves Management Detail Component', () => {
    let comp: QuickMovesDetailComponent;
    let fixture: ComponentFixture<QuickMovesDetailComponent>;
    const route = ({ data: of({ quickMoves: new QuickMoves(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [QuickMovesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuickMovesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuickMovesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load quickMoves on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.quickMoves).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
