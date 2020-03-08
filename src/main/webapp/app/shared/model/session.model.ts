import { Moment } from 'moment';
import { SessionLocation } from 'app/shared/model/enumerations/session-location.model';
import { AttendanceLocationTypes } from 'app/shared/model/enumerations/attendance-location-types.model';

export interface ISession {
  id?: string;
  courseId?: string;
  topic?: string;
  agenda?: string;
  sessionDateTime?: Moment;
  location?: SessionLocation;
  sessionPreRequisites?: string;
  assignedSMEs?: string;
  attendanceLocation?: AttendanceLocationTypes;
}

export class Session implements ISession {
  constructor(
    public id?: string,
    public courseId?: string,
    public topic?: string,
    public agenda?: string,
    public sessionDateTime?: Moment,
    public location?: SessionLocation,
    public sessionPreRequisites?: string,
    public assignedSMEs?: string,
    public attendanceLocation?: AttendanceLocationTypes
  ) {}
}
