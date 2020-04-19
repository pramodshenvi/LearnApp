import { Moment } from 'moment';

export interface ISessionParticipation {
  id?: string;
  sessionId?: string;
  courseId?: string;
  userName?: string;
  userEmail?: string;
  registrationDateTime?: Moment;
  attendanceDateTime?: Moment;
  userId?: string;
}

export class SessionParticipation implements ISessionParticipation {
  constructor(
    public id?: string,
    public sessionId?: string,
    public courseId?: string,
    public userName?: string,
    public userEmail?: string,
    public registrationDateTime?: Moment,
    public attendanceDateTime?: Moment,
    public userId?: string
  ) {}
}
