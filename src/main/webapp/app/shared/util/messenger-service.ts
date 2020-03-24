import { Injectable } from '@angular/core';
import { ICourse } from 'app/shared/model/course.model';
import { Account } from 'app/core/user/account.model';

@Injectable({ providedIn: 'root' })
export class MessengerService {
  private _courseDetails: ICourse | null = null;
  private _account: Account | null = null;

  constructor() {}

  getCourseDetails() : ICourse | null{
    return this._courseDetails;
  }
  
  setCourseDetails(value: ICourse | null): void{
    this._courseDetails = value;
  }

  getAccount() : Account | null {
    return this._account;
  }

  setAccount(account: Account) : void {
    this._account = account;
  }
}
