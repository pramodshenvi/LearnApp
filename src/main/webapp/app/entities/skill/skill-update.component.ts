import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISkill, Skill } from 'app/shared/model/skill.model';
import { SkillService } from './skill.service';

@Component({
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillUpdateComponent implements OnInit {
  isSaving = false;
  skillAliases: string[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    skillType: [null, [Validators.required]],
    newAlias: []
  });

  constructor(protected skillService: SkillService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.skillAliases = skill.skillAliases;
      this.updateForm(skill);
    });
  }

  addAlias(): void {
    this.skillAliases = this.skillAliases || [];
    this.skillAliases.push(this.editForm.get(['newAlias'])!.value);
    this.editForm.controls['newAlias'].reset();
  }

  deleteAlias(index: number): void {
    this.skillAliases.splice(index, 1);
  }

  updateForm(skill: ISkill): void {
    this.editForm.patchValue({
      id: skill.id,
      name: skill.name,
      skillType: skill.skillType
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skill = this.createFromForm();
    if (skill.id !== undefined) {
      this.subscribeToSaveResponse(this.skillService.update(skill));
    } else {
      this.subscribeToSaveResponse(this.skillService.create(skill));
    }
  }

  private createFromForm(): ISkill {
    return {
      ...new Skill(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      skillType: this.editForm.get(['skillType'])!.value,
      skillAliases: this.skillAliases
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>): void {
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
