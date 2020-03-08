import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourse } from 'app/shared/model/course.model';
import { MessengerService } from 'app/shared/util/messenger-service';

@Component({
  selector: 'jhi-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseDetailComponent implements OnInit {
  course: ICourse | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected messengerService: MessengerService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.course = course;
      this.messengerService.setCourseDetails(course);
    });
  }

  previousState(): void {
    window.history.back();
  }
}
