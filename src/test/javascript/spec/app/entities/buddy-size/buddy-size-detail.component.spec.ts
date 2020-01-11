import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokemonTestModule } from '../../../test.module';
import { BuddySizeDetailComponent } from 'app/entities/buddy-size/buddy-size-detail.component';
import { BuddySize } from 'app/shared/model/buddy-size.model';

describe('Component Tests', () => {
  describe('BuddySize Management Detail Component', () => {
    let comp: BuddySizeDetailComponent;
    let fixture: ComponentFixture<BuddySizeDetailComponent>;
    const route = ({ data: of({ buddySize: new BuddySize(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokemonTestModule],
        declarations: [BuddySizeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BuddySizeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BuddySizeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load buddySize on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.buddySize).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
