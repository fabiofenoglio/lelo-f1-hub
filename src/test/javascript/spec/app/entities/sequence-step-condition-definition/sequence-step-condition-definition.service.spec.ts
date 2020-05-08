import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SequenceStepConditionDefinitionService } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition.service';
import {
  ISequenceStepConditionDefinition,
  SequenceStepConditionDefinition
} from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';

describe('Service Tests', () => {
  describe('SequenceStepConditionDefinition Service', () => {
    let injector: TestBed;
    let service: SequenceStepConditionDefinitionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepConditionDefinition;
    let expectedResult: ISequenceStepConditionDefinition | ISequenceStepConditionDefinition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepConditionDefinitionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SequenceStepConditionDefinition(0, SequenceStepConditionGeneration.GEN1, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SequenceStepConditionDefinition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SequenceStepConditionDefinition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepConditionDefinition', () => {
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

      it('should return a list of SequenceStepConditionDefinition', () => {
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

      it('should delete a SequenceStepConditionDefinition', () => {
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
