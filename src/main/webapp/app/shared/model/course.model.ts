export interface ICourse {
  id?: string;
  courseName?: string;
  smeSkills?: string[];
  preRequisites?: string;
  points?: number;
  imagePath?: string;
}

export class Course implements ICourse {
  constructor(
    public id?: string,
    public courseName?: string,
    public smeSkills?: string[],
    public preRequisites?: string,
    public points?: number,
    public imagePath?: string
  ) {}
}
