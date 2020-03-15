import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { Router } from "@angular/router";

@Component({
  templateUrl: './course-delete-dialog.component.html'
})
export class CourseDeleteDialogComponent {
  course?: ICourse;

  constructor(protected courseService: CourseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager, protected router: Router) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.courseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courseListModification');
      this.activeModal.close();
      window.history.back();
    });
  }
}
