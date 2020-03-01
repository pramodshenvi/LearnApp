import { SkillTypeValues } from 'app/shared/model/enumerations/skill-type-values.model';

export interface ISkill {
  id?: string;
  name?: string;
  skillType?: SkillTypeValues;
  skillAliases?: string[];
}

export class Skill implements ISkill {
  constructor(public id?: string, public name?: string, public skillType?: SkillTypeValues, public skillAliases?: string[]) {}
}
