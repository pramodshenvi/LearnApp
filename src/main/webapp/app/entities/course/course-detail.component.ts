import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourse } from 'app/shared/model/course.model';
import { MessengerService } from 'app/shared/util/messenger-service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CourseDeleteDialogComponent } from './course-delete-dialog.component';
import { CourseModifySMEDialogComponent } from './course-modify-sme-dialog.component'

@Component({
  selector: 'jhi-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseDetailComponent implements OnInit {
  course: ICourse | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected messengerService: MessengerService, protected modalService: NgbModal,
    ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.course = course;
      this.messengerService.setCourseDetails(course);
    });
  }

  delete(course: ICourse): void {
    const modalRef = this.modalService.open(CourseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.course = course;
  }

  editSMEs(course: ICourse): void {
    const modalRef = this.modalService.open(CourseModifySMEDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.course = course;
    modalRef.result.then((result) => {
      if (result)
        this.course = result;
    }).catch(()=>{});
  }

  previousState(): void {
    window.history.back();
  }
}
