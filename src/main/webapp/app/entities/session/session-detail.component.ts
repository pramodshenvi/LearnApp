import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISession } from 'app/shared/model/session.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';

@Component({
  selector: 'jhi-session-detail',
  templateUrl: './session-detail.component.html'
})
export class SessionDetailComponent implements OnInit {
  session: ISession | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => (this.session = session));
  }

  previousState(): void {
    window.history.back();
  }

  delete(session: ISession): void {
    const modalRef = this.modalService.open(SessionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
  }
}
