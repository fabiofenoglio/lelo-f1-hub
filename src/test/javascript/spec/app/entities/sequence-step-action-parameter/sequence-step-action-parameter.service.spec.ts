import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SequenceStepActionParameterService } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter.service';
import { ISequenceStepActionParameter, SequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { SequenceStepActionGeneration } from 'app/shared/model/enumerations/sequence-step-action-generation.model';
import { SequenceVariable } from 'app/shared/model/enumerations/sequence-variable.model';

describe('Service Tests', () => {
  describe('SequenceStepActionParameter Service', () => {
    let injector: TestBed;
    let service: SequenceStepActionParameterService;
    let httpMock: HttpTestingController;
    let elemDefault: ISequenceStepActionParameter;
    let expectedResult: ISequenceStepActionParameter | ISequenceStepActionParameter[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SequenceStepActionParameterService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SequenceStepActionParameter(
        0,
        SequenceStepActionGeneration.GEN1,
        'AAAAAAA',
        0,
        false,
        SequenceVariable.USER_VARIABLE,
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

      it('should create a SequenceStepActionParameter', () => {
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

        service.create(new SequenceStepActionParameter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SequenceStepActionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            valueString: 'BBBBBB',
            valueNumber: 1,
            valueBoolean: true,
            valueVariable: 'BBBBBB',
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

      it('should return a list of SequenceStepActionParameter', () => {
        const returnedFromService = Object.assign(
          {
            generation: 'BBBBBB',
            valueString: 'BBBBBB',
            valueNumber: 1,
            valueBoolean: true,
            valueVariable: 'BBBBBB',
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

      it('should delete a SequenceStepActionParameter', () => {
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
