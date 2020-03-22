import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISession } from 'app/shared/model/session.model';
import { SessionService } from './session.service';

@Component({
  templateUrl: './session-mark-complete-dialog.component.html'
})
export class SessionMarkCompleteDialogComponent {
  session?: ISession;

  constructor(protected sessionService: SessionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmComplete(): void {
    if (this.session) {
      this.session.sessionComplete = true;
      this.sessionService.update(this.session).subscribe(() => {
        this.eventManager.broadcast('sessionListModification');
        this.activeModal.close();
        window.history.back();
      });
    }
  }
}
