import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { TypesDetailComponent } from 'app/entities/types/types-detail.component';
import { Types } from 'app/shared/model/types.model';

describe('Component Tests', () => {
  describe('Types Management Detail Component', () => {
    let comp: TypesDetailComponent;
    let fixture: ComponentFixture<TypesDetailComponent>;
    const route = ({ data: of({ types: new Types(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [TypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load types on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.types).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
