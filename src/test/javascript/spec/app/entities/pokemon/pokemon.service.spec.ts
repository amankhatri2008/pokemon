import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PokemonService } from 'app/entities/pokemon/pokemon.service';
import { IPokemon, Pokemon } from 'app/shared/model/pokemon.model';

describe('Service Tests', () => {
  describe('Pokemon Service', () => {
    let injector: TestBed;
    let service: PokemonService;
    let httpMock: HttpTestingController;
    let elemDefault: IPokemon;
    let expectedResult: IPokemon | IPokemon[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PokemonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Pokemon(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pokemon', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Pokemon())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pokemon', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            animationTime0: 1,
            animationTime1: 1,
            animationTime2: 1,
            animationTime3: 1,
            animationTime4: 1,
            animationTime5: 1,
            animationTime6: 1,
            animationTime7: 1,
            animationTime8: 1,
            height: 1,
            modelHeight: 1,
            kmBuddyDistance: 1,
            weight: 1,
            modelScale: 1,
            maxCP: 1,
            cylinderGround: 1,
            cylinderRadius: 1,
            diskRadius: 1,
            key: 'BBBBBB',
            cinematicMovesId: 1,
            baseAttack: 1,
            baseDefense: 1,
            baseStamina: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pokemon', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            animationTime0: 1,
            animationTime1: 1,
            animationTime2: 1,
            animationTime3: 1,
            animationTime4: 1,
            animationTime5: 1,
            animationTime6: 1,
            animationTime7: 1,
            animationTime8: 1,
            height: 1,
            modelHeight: 1,
            kmBuddyDistance: 1,
            weight: 1,
            modelScale: 1,
            maxCP: 1,
            cylinderGround: 1,
            cylinderRadius: 1,
            diskRadius: 1,
            key: 'BBBBBB',
            cinematicMovesId: 1,
            baseAttack: 1,
            baseDefense: 1,
            baseStamina: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pokemon', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
