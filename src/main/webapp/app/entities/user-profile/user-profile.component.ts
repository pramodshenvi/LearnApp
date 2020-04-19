import { Component, OnDestroy, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ISession } from 'app/shared/model/session.model';
import { UserProfileService } from './user-profile.service';
import { Moment } from 'moment';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnDestroy {
  userProfile!: Account;
  registeredSessions: ISession[] = [];

  constructor(private accountService: AccountService, private userProfileService: UserProfileService) {}

  ngOnInit(): void {
    this.accountService.identity(true).subscribe(account => {
      console.log(account);
      if (account) {
        this.userProfile = account;
      } else {
        throw Error('Unable to fetch user account');
      }
    });
    this.userProfileService.registeredSessions().subscribe(sessions => {
      console.log(sessions);
      if (sessions) {
        const dateTimeSort = (a: ISession, b: ISession) => {
          if (a.sessionDateTime && b.sessionDateTime) {
            return a.sessionDateTime.valueOf() - b.sessionDateTime.valueOf();
          } else {
            return 0;
          }
        };
        this.registeredSessions = sessions.sort(dateTimeSort);
      }
    });
  }

  ngOnDestroy(): void {}
}
