import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserPointsService } from 'app/entities/user-points/user-points.service';
import { IUserPoints, UserPoints } from 'app/shared/model/user-points.model';
import { PointsFor } from 'app/shared/model/enumerations/points-for.model';

describe('Service Tests', () => {
  describe('UserPoints Service', () => {
    let injector: TestBed;
    let service: UserPointsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserPoints;
    let expectedResult: IUserPoints | IUserPoints[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserPointsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserPoints('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, PointsFor.HOST, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sessionDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserPoints', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            sessionDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sessionDateTime: currentDate
          },
          returnedFromService
        );

        service.create(new UserPoints()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserPoints', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            sessionId: 'BBBBBB',
            sessionTopic: 'BBBBBB',
            sessionDateTime: currentDate.format(DATE_TIME_FORMAT),
            pointsFor: 'BBBBBB',
            points: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sessionDateTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserPoints', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            sessionId: 'BBBBBB',
            sessionTopic: 'BBBBBB',
            sessionDateTime: currentDate.format(DATE_TIME_FORMAT),
            pointsFor: 'BBBBBB',
            points: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sessionDateTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserPoints', () => {
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
