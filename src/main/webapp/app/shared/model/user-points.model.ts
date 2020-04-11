import { Moment } from 'moment';
import { PointsFor } from 'app/shared/model/enumerations/points-for.model';

export interface IUserPoints {
  id?: string;
  userId?: string;
  sessionId?: string;
  sessionTopic?: string;
  sessionDateTime?: Moment;
  pointsFor?: PointsFor;
  points?: number;
}

export class UserPoints implements IUserPoints {
  constructor(
    public id?: string,
    public userId?: string,
    public sessionId?: string,
    public sessionTopic?: string,
    public sessionDateTime?: Moment,
    public pointsFor?: PointsFor,
    public points?: number
  ) {}
}
