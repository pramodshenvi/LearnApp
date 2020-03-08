import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.LearnAppSkillModule)
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.LearnAppCourseModule)
      },
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.LearnAppSessionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LearnAppEntityModule {}