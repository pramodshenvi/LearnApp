import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SessionParticipationService } from 'app/entities/session-participation/session-participation.service';
import { ISessionParticipation, SessionParticipation } from 'app/shared/model/session-participation.model';

describe('Service Tests', () => {
  describe('SessionParticipation Service', () => {
    let injector: TestBed;
    let service: SessionParticipationService;
    let httpMock: HttpTestingController;
    let elemDefault: ISessionParticipation;
    let expectedResult: ISessionParticipation | ISessionParticipation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SessionParticipationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SessionParticipation('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            registrationDateTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SessionParticipation', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            registrationDateTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDateTime: currentDate,
            attendanceDateTime: currentDate
          },
          returnedFromService
        );

        service.createOrUpdate(new SessionParticipation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SessionParticipation', () => {
        const returnedFromService = Object.assign(
          {
            sessionId: 'BBBBBB',
            userName: 'BBBBBB',
            userEmail: 'BBBBBB',
            registrationDateTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
            userId: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDateTime: currentDate,
            attendanceDateTime: currentDate
          },
          returnedFromService
        );

        service.createOrUpdate(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SessionParticipation', () => {
        const returnedFromService = Object.assign(
          {
            sessionId: 'BBBBBB',
            userName: 'BBBBBB',
            userEmail: 'BBBBBB',
            registrationDateTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
            userId: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDateTime: currentDate,
            attendanceDateTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SessionParticipation', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

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
