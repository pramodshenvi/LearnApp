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
        path: 'participant',
        loadChildren: () => import('./session-participation/session-participation.module').then(m => m.LearnAppSessionParticipationModule)
      },
      {
        path: 'user-points',
        loadChildren: () => import('./user-points/user-points.module').then(m => m.LearnAppUserPointsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LearnAppEntityModule {}
