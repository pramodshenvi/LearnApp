import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISession } from 'app/shared/model/session.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';
import { MessengerService } from 'app/shared/util/messenger-service';
import { Router } from "@angular/router";

@Component({
  selector: 'jhi-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionDetailComponent implements OnInit {
  session: ISession | null = null;
  courseDetails: any = {};

  constructor(
      protected activatedRoute: ActivatedRoute, 
      protected modalService: NgbModal, 
      protected messengerService: MessengerService, 
      protected router: Router) {}

  ngOnInit(): void {
    this.courseDetails = this.messengerService.getCourseDetails();
    if(!this.courseDetails)
      this.router.navigate(["/course"])
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
