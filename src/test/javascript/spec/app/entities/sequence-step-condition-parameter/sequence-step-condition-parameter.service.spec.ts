import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SequenceStepConditionParameterService } from 'app/entities/sequence-step-condition-parameter/sequence-step-condition-parameter.service';
import { ISequenceStepConditionParameter, SequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { SequenceStepConditionGeneration } from 'app/shared/model/enumerations/sequence-step-condition-generation.model';
import { SequenceVariable } from 'app/shared/model/enumerations/sequence-variable.model';
import { SequenceOperator } from 'app/shared/model/enumerations/sequence-operator.model';

describe('Service Tests', () => {
  describe('SequenceStepConditionParameter Service', () => {
    let injector: TestBed;
    let service: SequenceStepConditionParameterService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepConditionParameter;
    let expectedResult: ISequenceStepConditionParameter | ISequenceStepConditionParameter[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepConditionParameterService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SequenceStepConditionParameter(
        0,
        SequenceStepConditionGeneration.GEN1,
        'AAAAAAA',
        0,
        false,
        SequenceVariable.USER_VARIABLE,
        SequenceOperator.EQUALS,
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SequenceStepConditionParameter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.create(new SequenceStepConditionParameter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepConditionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            valueString: 'BBBBBB',
            valueNumber: 1,
            valueBoolean: true,
            valueVariable: 'BBBBBB',
            valueOperator: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SequenceStepConditionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            valueString: 'BBBBBB',
            valueNumber: 1,
            valueBoolean: true,
            valueVariable: 'BBBBBB',
            valueOperator: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            lastModifiedDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SequenceStepConditionParameter', () => {
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
