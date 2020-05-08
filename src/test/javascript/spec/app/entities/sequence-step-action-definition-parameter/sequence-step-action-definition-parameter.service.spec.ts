import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SequenceStepActionDefinitionParameterService } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.service';
import {
  ISequenceStepActionDefinitionParameter,
  SequenceStepActionDefinitionParameter
} from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';
import { SequenceStepActionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-action-definition-parameter-type.model';
import { SequenceStepActionEvaluationEngine } from 'app/shared/model/enumerations/sequence-step-action-evaluation-engine.model';

describe('Service Tests', () => {
  describe('SequenceStepActionDefinitionParameter Service', () => {
    let injector: TestBed;
    let service: SequenceStepActionDefinitionParameterService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepActionDefinitionParameter;
    let expectedResult: ISequenceStepActionDefinitionParameter | ISequenceStepActionDefinitionParameter[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepActionDefinitionParameterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SequenceStepActionDefinitionParameter(
        0,
        SequenceStepActionGeneration.GEN1,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        SequenceStepActionDefinitionParameterType.STRING,
        SequenceStepActionEvaluationEngine.DEFAULT_ENGINE,
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

      it('should create a SequenceStepActionDefinitionParameter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SequenceStepActionDefinitionParameter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepActionDefinitionParameter', () => {
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

      it('should return a list of SequenceStepActionDefinitionParameter', () => {
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

      it('should delete a SequenceStepActionDefinitionParameter', () => {
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
