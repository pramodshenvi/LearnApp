import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';

import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;
  smeSkills: string[] = [];
  skillsDropdownList: ISkill[] = [];
  skillDropDownKeyboardAction: String | undefined;

  editForm = this.fb.group({
    id: [],
    courseName: [null, [Validators.required]],
    preRequisites: [],
    points: [null, [Validators.required]],
    imagePath: [],
    newSMESkill: []
  });

  constructor(protected courseService: CourseService, protected skillService: SkillService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.smeSkills = course.smeSkills;
      this.updateForm(course);
    });
  }

  deleteSMESkill(index: number): void {
    this.smeSkills.splice(index, 1);
  }

  loadSkills(evt:any): void {
    this.skillDropDownKeyboardAction = evt.key;
    setTimeout(()=>{
      this.skillDropDownKeyboardAction = "reset";
    },200)
    const skillVal = this.editForm.get(['newSMESkill'])!.value;
    if (skillVal && skillVal !== "") {
      if(evt.key !== "Enter" && evt.key !== "ArrowUp"  && evt.key !== "ArrowDown"){
        this.skillsDropdownList = [];
        const skill:ISkill = {name:skillVal};
        this.skillService.query(skill).subscribe((s) => {
          this.skillsDropdownList = (s.body) ? s.body.slice(0,5) : [];
        });
      }
    }
  }

  onDropdownValueSelection(selection: string): void {
    this.smeSkills = this.smeSkills || [];
    this.smeSkills.push(selection);
    this.editForm.controls['newSMESkill'].reset();
  }

  updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      courseName: course.courseName,
      smeSkills: course.smeSkills,
      preRequisites: course.preRequisites,
      points: course.points,
      imagePath: course.imagePath
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      courseName: this.editForm.get(['courseName'])!.value,
      smeSkills: this.smeSkills,
      preRequisites: this.editForm.get(['preRequisites'])!.value,
      points: this.editForm.get(['points'])!.value,
      imagePath: this.editForm.get(['imagePath'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
