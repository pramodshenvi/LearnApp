export interface ICourse {
  id?: string;
  courseName?: string;
  smeSkills?: string[];
  preRequisites?: string;
  smePoints?: number;
  participantPoints?: number;
  imagePath?: string;
}

export class Course implements ICourse {
  constructor(
    public id?: string,
    public courseName?: string,
    public smeSkills?: string[],
    public preRequisites?: string,
    public smePoints?: number,
    public participantPoints?: number,
    public imagePath?: string
  ) {}
}
