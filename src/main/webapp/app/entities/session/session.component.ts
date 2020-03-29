import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISession } from 'app/shared/model/session.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SessionService } from './session.service';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';
import { MessengerService } from 'app/shared/util/messenger-service';
import { SessionParticipationService } from 'app/entities/session-participation/session-participation.service';
import { ISessionParticipation, SessionParticipation} from 'app/shared/model/session-participation.model';
import * as moment from 'moment';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-session',
  templateUrl: './session.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionComponent implements OnInit, OnDestroy {
  sessions: ISession[];
  sessionParticipationDetails: any = {};
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  courseDetails: any = {};

  constructor(
    protected sessionService: SessionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected messengerService: MessengerService,
    protected participationService: SessionParticipationService
  ) {
    this.sessions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sessionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
        courseId: this.courseDetails.id
      })
      .subscribe((res: HttpResponse<ISession[]>) => {
        this.paginateSessions(res.body, res.headers);
        this.getSessionParticipationDetails(res.body);
      });
  }

  reset(): void {
    this.page = 0;
    this.sessions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.courseDetails = this.messengerService.getCourseDetails();
    this.loadAll();
    this.registerChangeInSessions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISession): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSessions(): void {
    this.eventSubscriber = this.eventManager.subscribe('sessionListModification', () => this.reset());
  }

  delete(session: ISession): void {
    const modalRef = this.modalService.open(SessionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
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
        registrationDateTime: moment(),
        userId: acct.login
      };
    }
    return returnObj;
  }

  registerForSession(session: ISession): void {
    const partObj = this.createParticipation(session.id);
    if(partObj)
      this.participationService.createOrUpdate(partObj).subscribe(() => {
        this.getSessionParticipationDetails(this.sessions);
      });
  }

  unRegisterFromSession(sessionParticipation: any): void {
    this.participationService.delete(sessionParticipation.id).subscribe(() => {
      this.getSessionParticipationDetails(this.sessions);
    });
  }

  protected paginateSessions(data: ISession[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sessions.push(data[i]);
      }
    }
  }

  protected getSessionParticipationDetails(data: ISession[] | null): void {
    const acct : Account | null = this.messengerService.getAccount();
    let sessionIds = "";
    if (data && acct) {
      for (let i = 0; i < data.length; i++) {
        sessionIds+=data[i].id;
        if(i<data.length-1)
          sessionIds+=",";
      }
      const participationObj = {
        ...new SessionParticipation(),
        sessionId: sessionIds,
        userId: acct.login
      };
      this.participationService.getMatchingSessionParticipationsForUser(participationObj)
      .subscribe((res: HttpResponse<ISessionParticipation[]>) => {
        const partDetails = res.body;
        this.sessionParticipationDetails = {};
        if (partDetails && partDetails.length > 0) {
          for (let i = 0; i < partDetails.length; i++) {
            const sessionId: string | undefined = partDetails[i].sessionId;
            if(sessionId) {
              this.sessionParticipationDetails[sessionId]= {
                registrationDateTime: partDetails[i].registrationDateTime,
                id: partDetails[i].id
              };
            }
          }
        }
      });
    }
  }
}
