import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SequenceStepConditionDefinitionParameterService } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.service';
import {
  ISequenceStepConditionDefinitionParameter,
  SequenceStepConditionDefinitionParameter
} from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';
import { SequenceStepConditionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-condition-definition-parameter-type.model';
import { SequenceStepConditionEvaluationEngine } from 'app/shared/model/enumerations/sequence-step-condition-evaluation-engine.model';

describe('Service Tests', () => {
  describe('SequenceStepConditionDefinitionParameter Service', () => {
    let injector: TestBed;
    let service: SequenceStepConditionDefinitionParameterService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepConditionDefinitionParameter;
    let expectedResult: ISequenceStepConditionDefinitionParameter | ISequenceStepConditionDefinitionParameter[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepConditionDefinitionParameterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SequenceStepConditionDefinitionParameter(
        0,
        SequenceStepConditionGeneration.GEN1,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        SequenceStepConditionDefinitionParameterType.STRING,
        SequenceStepConditionEvaluationEngine.DEFAULT_ENGINE,
        0,
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SequenceStepConditionDefinitionParameter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SequenceStepConditionDefinitionParameter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepConditionDefinitionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
            evaluationEngine: 'BBBBBB',
            minValue: 1,
            maxValue: 1,
            stepValue: 1,
            minLength: 1,
            maxLength: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SequenceStepConditionDefinitionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
            evaluationEngine: 'BBBBBB',
            minValue: 1,
            maxValue: 1,
            stepValue: 1,
            minLength: 1,
            maxLength: 1
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

      it('should delete a SequenceStepConditionDefinitionParameter', () => {
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
