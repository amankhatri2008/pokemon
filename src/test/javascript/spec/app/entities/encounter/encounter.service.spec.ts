import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { EncounterService } from 'app/entities/encounter/encounter.service';
import { IEncounter, Encounter } from 'app/shared/model/encounter.model';

describe('Service Tests', () => {
  describe('Encounter Service', () => {
    let injector: TestBed;
    let service: EncounterService;
    let httpMock: HttpTestingController;
    let elemDefault: IEncounter;
    let expectedResult: IEncounter | IEncounter[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EncounterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Encounter(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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

      it('should create a Encounter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Encounter())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Encounter', () => {
        const returnedFromService = Object.assign(
          {
            attackProbability: 1,
            attackTimer: 1,
            baseFleeRate: 1,
            baseCaptureRate: 1,
            cameraDistance: 1,
            collisionRadius: 1,
            dodgeDistance: 1,
            dodgeProbability: 1,
            jumpTime: 1,
            maxPokemonActionFrequency: 1,
            minPokemonActionFrequency: 1
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

      it('should return a list of Encounter', () => {
        const returnedFromService = Object.assign(
          {
            attackProbability: 1,
            attackTimer: 1,
            baseFleeRate: 1,
            baseCaptureRate: 1,
            cameraDistance: 1,
            collisionRadius: 1,
            dodgeDistance: 1,
            dodgeProbability: 1,
            jumpTime: 1,
            maxPokemonActionFrequency: 1,
            minPokemonActionFrequency: 1
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

      it('should delete a Encounter', () => {
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
