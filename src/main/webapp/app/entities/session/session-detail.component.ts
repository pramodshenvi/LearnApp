import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISession } from 'app/shared/model/session.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';
import { SessionMarkCompleteDialogComponent } from './session-mark-complete-dialog.component';
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
  sessionQRUrl: string | null = null;

  constructor(
      protected activatedRoute: ActivatedRoute, 
      protected modalService: NgbModal, 
      protected messengerService: MessengerService, 
      protected router: Router) {}

  ngOnInit(): void {
    this.courseDetails = this.messengerService.getCourseDetails();
    if(!this.courseDetails)
      this.router.navigate(["/course"])
    this.activatedRoute.data.subscribe(({ session }) => {
      this.session = session;
      const qrCodeUrl = window.location.origin+'/participant?q='+escape(session.id);
      this.sessionQRUrl = 'https://chart.googleapis.com/chart?cht=qr&chl='+qrCodeUrl+'&chs=400x400&choe=UTF-8&chld=L|2'
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(session: ISession): void {
    const modalRef = this.modalService.open(SessionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
  }

  printQRCode(): void {
    const popup = window.open();
    if(popup){
      const topic = this.session? this.session.topic : "";
      const dateTime = this.session && this.session.sessionDateTime? this.session.sessionDateTime.toDate().toUTCString() : "";
      popup.document.write("<html><body><h1>"+topic+"</h1><h3>"+dateTime+"</h3><br><br><br><div style=\"text-align:center;\"><img src="+this.sessionQRUrl+"></div></body></html>");
      popup.focus();
      popup.print();
      popup.close();
    }
  }

  markSessionAsComplete(session: ISession): void {
    const modalRef = this.modalService.open(SessionMarkCompleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
  }
}
