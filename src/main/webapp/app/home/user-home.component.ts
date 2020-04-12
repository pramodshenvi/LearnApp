import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

import { Account } from 'app/core/user/account.model';
import { MessengerService } from 'app/shared/util/messenger-service';
import { UserPointsService } from 'app/entities/user-points/user-points.service';
import { IUserPoints } from 'app/shared/model/user-points.model';

@Component({
  selector: 'jhi-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['home.scss']
})
export class UserHomeComponent implements OnInit {
  account: Account | null = null;
  userPoints = -1;

  constructor(protected messengerService: MessengerService, protected userPointsService: UserPointsService) {}

  ngOnInit(): void {
    this.account = this.messengerService.getAccount();
    if(this.account) {
      this.userPointsService
      .query({
        userId: this.account.login,
        sessionId: "AGGREGATED"
      })
      .subscribe((res: HttpResponse<IUserPoints[]>) => {
        if ( res && res.body && res.body.length > 0 && res.body[0].points)
          this.userPoints = res.body[0].points;
        else
          this.userPoints = 0;
      });
    }
  }
}
