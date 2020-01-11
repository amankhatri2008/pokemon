import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { BranchsDetailComponent } from 'app/entities/branchs/branchs-detail.component';
import { Branchs } from 'app/shared/model/branchs.model';

describe('Component Tests', () => {
  describe('Branchs Management Detail Component', () => {
    let comp: BranchsDetailComponent;
    let fixture: ComponentFixture<BranchsDetailComponent>;
    const route = ({ data: of({ branchs: new Branchs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [BranchsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BranchsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BranchsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load branchs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.branchs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
