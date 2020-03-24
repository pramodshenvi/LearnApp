import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISessionParticipation } from 'app/shared/model/session-participation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SessionParticipationService } from './session-participation.service';

@Component({
  selector: 'jhi-session-participation',
  templateUrl: './session-participation.component.html'
})
export class SessionParticipationComponent implements OnInit, OnDestroy {
  sessionParticipations: ISessionParticipation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sessionParticipationService: SessionParticipationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sessionParticipations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sessionParticipationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISessionParticipation[]>) => this.paginateSessionParticipations(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sessionParticipations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSessionParticipations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISessionParticipation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSessionParticipations(): void {
    this.eventSubscriber = this.eventManager.subscribe('sessionParticipationListModification', () => this.reset());
  }

  delete(): void {
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }
  
  protected paginateSessionParticipations(data: ISessionParticipation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sessionParticipations.push(data[i]);
      }
    }
  }
}
