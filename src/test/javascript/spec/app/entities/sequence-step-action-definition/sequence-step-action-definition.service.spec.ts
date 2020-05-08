import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SequenceStepActionDefinitionService } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition.service';
import { ISequenceStepActionDefinition, SequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';

describe('Service Tests', () => {
  describe('SequenceStepActionDefinition Service', () => {
    let injector: TestBed;
    let service: SequenceStepActionDefinitionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepActionDefinition;
    let expectedResult: ISequenceStepActionDefinition | ISequenceStepActionDefinition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepActionDefinitionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SequenceStepActionDefinition(0, SequenceStepActionGeneration.GEN1, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SequenceStepActionDefinition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SequenceStepActionDefinition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepActionDefinition', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            code: 'BBBBBB',
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SequenceStepActionDefinition', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            code: 'BBBBBB',
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SequenceStepActionDefinition', () => {
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
