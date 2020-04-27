import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourse } from 'app/shared/model/course.model';
import { MessengerService } from 'app/shared/util/messenger-service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CourseDeleteDialogComponent } from './course-delete-dialog.component';
import { CourseModifySMEDialogComponent } from './course-modify-sme-dialog.component';
import { CourseService } from './course.service';

@Component({
  selector: 'jhi-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseDetailComponent implements OnInit {
  course: ICourse | null = null;
  userName = '';

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected courseService: CourseService,
    protected messengerService: MessengerService,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    const account = this.messengerService.getAccount();
    if (account)
      this.userName =
        (account.firstName ? account.firstName : '') +
        (account.lastName ? ' ' + account.lastName : '') +
        '|' +
        (account.login ? ' ' + account.login : '');

    this.activatedRoute.data.subscribe(({ course }) => {
      this.course = course;
      if (this.course) this.messengerService.setCourseDetails(course);
    });
  }

  delete(course: ICourse): void {
    const modalRef = this.modalService.open(CourseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.course = course;
  }

  editSMEs(course: ICourse): void {
    const modalRef = this.modalService.open(CourseModifySMEDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.course = course;
    modalRef.result
      .then(result => {
        if (result) {
          this.course = result;
          this.messengerService.setCourseDetails(this.course);
        }
      })
      .catch(() => {});
  }

  iAmSME(): void {
    if (this.course && ((this.course.courseSMEs && !this.course.courseSMEs.includes(this.userName)) || !this.course.courseSMEs)) {
      this.course.courseSMEs = this.course.courseSMEs || [];
      this.course.courseSMEs.push(this.userName);
      this.courseService.update(this.course).subscribe(updatedCourse => {
        if (updatedCourse && updatedCourse.body) {
          this.course = updatedCourse.body;
          this.messengerService.setCourseDetails(this.course);
        }
      });
    }
  }

  previousState(): void {
    window.history.back();
  }
}
