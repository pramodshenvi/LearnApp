import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { SessionParticipationService } from './session-participation.service';
import { SessionService } from 'app/entities/session/session.service';
import { MessengerService } from 'app/shared/util/messenger-service';

import { ISession, Session } from 'app/shared/model/session.model';
import { ISessionParticipation, SessionParticipation } from 'app/shared/model/session-participation.model';
import * as moment from 'moment';
import { Account } from 'app/core/user/account.model';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-session-participation',
  templateUrl: './session-participation.component.html',
  styleUrls: ['./session-participation.component.scss']
})
export class SessionParticipationComponent implements OnInit, OnDestroy {
  eventSubscriber?: Subscription;
  links: any;

  sessionId: string | null;
  session: ISession = new Session();
  sessionLoadError = false;
  participationRecorded = false;

  constructor(
    protected sessionService: SessionService,
    protected messengerService: MessengerService,
    protected sessionParticipationService: SessionParticipationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected route: ActivatedRoute
  ) {
    this.sessionId = this.getUrlParam("q");
  }

  loadAll(): void {
    this.sessionService
      .query({
        id: this.sessionId
      })
      .subscribe((res: HttpResponse<ISession[]>) => {
        if ( res && res.body && res.body.length > 0) {
          this.session = res.body[0];
          this.registerSessionParticipation(this.session);
        } else
          this.sessionLoadError = true;
      });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  registerSessionParticipation(session: ISession): void {
    const partObj = this.createParticipation(session.id);
    if(partObj)
      this.sessionParticipationService.createOrUpdate(partObj).subscribe(() => {
        this.participationRecorded = true;
      });
  }

  private createParticipation(sessId: string | undefined): ISessionParticipation | null {
    const acct : Account | null = this.messengerService.getAccount();
    let returnObj : ISessionParticipation | null = null;
    if (acct) {
      returnObj = {
        ...new SessionParticipation(),
        sessionId: sessId,
        userName: acct.firstName + ' ' +acct.lastName,
        userEmail: acct.email,
        attendanceDateTime: moment(),
        userId: acct.login
      };
    }
    return returnObj;
  }

  private getUrlParam(param: string) : string | null {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
  }
}
