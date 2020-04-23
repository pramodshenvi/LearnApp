import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { ISkill } from 'app/shared/model/skill.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { SkillService } from '../skill/skill.service';

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileUpdateComponent implements OnInit {
  private account!: Account;

  isSaving = false;
  interestedInSkills: string[] = [];
  expertInSkills: string[] = [];
  skillsDropdownList1: ISkill[] = [];
  skillsDropdownList2: ISkill[] = [];
  skillDropDownKeyboardAction1: String | undefined;
  skillDropDownKeyboardAction2: String | undefined;

  editForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    imageUrl: [],
    newInterestedSkills: [],
    newExpertSkills: []
  });

  constructor(protected accountService: AccountService, protected skillService: SkillService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.updateForm(account);
      }
    });
  }

  updateForm(account: Account): void {
    this.interestedInSkills = account.interestedInSkills;
    this.expertInSkills = account.expertInSkills;
    this.editForm.patchValue({
      firstName: account.firstName,
      lastName: account.lastName,
      email: account.email,
      imageUrl: account.imageUrl
    });
  }

  loadSkills(evt: any, field: string): void {
    if (field === 'interested') {
      this.skillDropDownKeyboardAction1 = evt.key;
    } else {
      this.skillDropDownKeyboardAction2 = evt.key;
    }
    setTimeout(() => {
      if (field === 'interested') {
        this.skillDropDownKeyboardAction1 = 'reset';
      } else {
        this.skillDropDownKeyboardAction2 = 'reset';
      }
    }, 200);
    const skillVal = this.editForm.get(['newInterestedSkills'])!.value;
    if (skillVal && skillVal !== '') {
      if (evt.key !== 'Enter' && evt.key !== 'ArrowUp' && evt.key !== 'ArrowDown') {
        this.skillsDropdownList1 = [];
        this.skillsDropdownList2 = [];
        const skill: ISkill = { name: skillVal };
        this.skillService.query(skill).subscribe(s => {
          if (field === 'interested') {
            this.skillsDropdownList1 = s.body ? s.body.slice(0, 5) : [];
          } else {
            this.skillsDropdownList2 = s.body ? s.body.slice(0, 5) : [];
          }
        });
      }
    }
  }

  onInterestedValueSelection(selection: string): void {
    this.onDropdownValueSelection(selection, 'interested');
  }

  onExpertValueSelection(selection: string): void {
    this.onDropdownValueSelection(selection, 'expert');
  }

  onDropdownValueSelection(selection: string, field: string): void {
    if (field === 'interested') {
      this.interestedInSkills = this.interestedInSkills || [];
      this.interestedInSkills.push(selection);
      this.editForm.controls['newInterestedSkills'].reset();
    } else {
      this.expertInSkills = this.expertInSkills || [];
      this.expertInSkills.push(selection);
      this.editForm.controls['newExpertSkills'].reset();
    }
  }

  deleteSkill(index: number, field: string): void {
    if (field === 'interested') {
      this.interestedInSkills.splice(index, 1);
    } else {
      this.expertInSkills.splice(index, 1);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const account = this.createFromForm();

    this.subscribeToSaveResponse(this.accountService.save(account));
  }

  private createFromForm(): Account {
    this.account.firstName = this.editForm.get(['firstName'])!.value;
    this.account.lastName = this.editForm.get(['lastName'])!.value;
    this.account.email = this.editForm.get(['email'])!.value;
    this.account.imageUrl = this.editForm.get(['imageUrl'])!.value;
    this.account.interestedInSkills = this.interestedInSkills;
    this.account.expertInSkills = this.expertInSkills;

    return this.account;
  }

  protected subscribeToSaveResponse(result: Observable<{}>): void {
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
