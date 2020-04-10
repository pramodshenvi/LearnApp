import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';

import { UserService } from 'app/core/user/user.service';

@Component({
  templateUrl: './course-modify-sme-dialog.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseModifySMEDialogComponent implements OnInit {
  course?: ICourse;
  userDropDownKeyboardAction: String | undefined;
  courseSMEDropdownList: any[] = [];
  courseSMEList: string[] = [];

  editForm = this.fb.group({
    courseSME: []
  });

  constructor(protected courseService: CourseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager, protected router: Router, private fb: FormBuilder, private userService: UserService) {}

  ngOnInit(): void {
    if(this.course && this.course.courseSMEs)
      this.courseSMEList = [...this.course.courseSMEs];
  }

  cancel(): void {
    this.courseSMEList = [];
    if(this.course && this.course.courseSMEs)
      this.courseSMEList = this.course.courseSMEs;
    this.activeModal.dismiss();
  }

  saveSMEList(course: ICourse): void {
    if(this.course) {
      this.course.courseSMEs = this.courseSMEList;
      this.courseService.update(course).subscribe((updatedCourse) => {
        if(updatedCourse && updatedCourse.body)
          this.course = updatedCourse.body;
        this.activeModal.close(this.course);
      });
    }
  }

  deleteCourseSME(index: number): void {
    if(this.courseSMEList.length > 0)
      this.courseSMEList.splice(index, 1);
  }

  onDropdownValueSelection(selection: string): void {
    if(this.course) {
      this.courseSMEList = this.courseSMEList || [];
      if(!this.courseSMEList.includes(selection))
        this.courseSMEList.push(selection);
      this.editForm.controls['courseSME'].reset();
    }
  }

  loadMatchingUserNames(evt:any): void {
    this.userDropDownKeyboardAction = evt.key;
    setTimeout(()=>{
      this.userDropDownKeyboardAction = "reset";
    },200)
    const courseSME = this.editForm.get(['courseSME'])!.value;
    if (courseSME && courseSME !== "") {
      if(evt.key !== "Enter" && evt.key !== "ArrowUp"  && evt.key !== "ArrowDown"){
        this.userService.queryMatchingUsers(courseSME).subscribe((s) => {
          this.courseSMEDropdownList = [];
          const matchingUserList = (s.body) ? s.body.slice(0,5) : [];
          if(matchingUserList && matchingUserList.length > 0){
            matchingUserList.forEach((e)=>{
              this.courseSMEDropdownList.push({name : (e.firstName? e.firstName : '') + (e.lastName ? ' ' + e.lastName : '') + '|' + (e.login ? ' ' + e.login : '')});
            });
          }
        });
      }
    }
  }
}
