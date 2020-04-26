import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';

import { MessengerService } from 'app/shared/util/messenger-service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

import { SMESearchService } from './sme-search.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-sme-search',
  templateUrl: './sme-search.component.html',
  styleUrls: ['./sme-search.component.scss']
})
export class SMESearchComponent implements OnInit {
  eventSubscriber?: Subscription;
  links: any;
  smeList: Account[] = [];
  smeSkills: string[] = [];
  skillDropDownKeyboardAction: String | undefined;
  skillsDropdownList: ISkill[] = [];
  noResultsFound = false;

  editForm = this.fb.group({
    searchSMESkill: [null, [Validators.required]]
  });

  constructor(
    protected messengerService: MessengerService,
    protected searchService: SMESearchService,
    protected skillService: SkillService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    const course = this.messengerService.getCourseDetails();
    const courseInitiation = this.getUrlParam('ci');
    if(courseInitiation && courseInitiation === 'y' && course && course.smeSkills && course.smeSkills.length > 0) {
      this.smeSkills = course.smeSkills;
      this.getSMEUsers(course.smeSkills);
    }
  }

  private getUrlParam(param: string): string | null {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
  }

  loadSkills(evt: any): void {
    this.smeList = [];
    this.noResultsFound = false;
    this.skillDropDownKeyboardAction = evt.key;
    setTimeout(() => {
      this.skillDropDownKeyboardAction = 'reset';
    }, 200);
    const skillVal = this.editForm.get(['searchSMESkill'])!.value;
    if (skillVal && skillVal !== '') {
      if (evt.key !== 'Enter' && evt.key !== 'ArrowUp' && evt.key !== 'ArrowDown') {
        this.skillsDropdownList = [];
        const skill: ISkill = { name: skillVal };
        this.skillService.query(skill).subscribe(s => {
          this.skillsDropdownList = s.body ? s.body.slice(0, 5) : [];
        });
      }
    }
  }

  onDropdownValueSelection(selection: string): void {
    this.smeSkills = this.smeSkills || [];
    if(!this.smeSkills.includes(selection))
      this.smeSkills.push(selection);
    this.editForm.controls['searchSMESkill'].reset();
  }

  deleteSMESkill(index: number): void {
    this.smeSkills.splice(index, 1);
  }

  getSMEUsers(skills: string[]): void {
    if(skills && skills.length > 0){
      this.searchService
        .getUsersWithMatchingSMESkills({
          expertInSkills: skills
        })
        .subscribe((res) => {
          if (res && res.body && res.body.length > 0) {
            this.smeList = res.body;
          } else {
            this.smeList = [];
            this.noResultsFound = true;
          }
        });
    }
  }
}
