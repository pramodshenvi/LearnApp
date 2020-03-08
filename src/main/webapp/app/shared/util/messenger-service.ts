import { Injectable } from '@angular/core';
import { ICourse } from 'app/shared/model/course.model';

@Injectable({ providedIn: 'root' })
export class MessengerService {
  private _courseDetails: ICourse | null = null;

  constructor() {}

  getCourseDetails() : ICourse | null{
    return this._courseDetails;
  }
  
  setCourseDetails(value: ICourse | null): void{
    this._courseDetails = value;
  }
}
