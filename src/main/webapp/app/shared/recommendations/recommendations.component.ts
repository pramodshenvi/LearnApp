import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ICourse } from 'app/shared/model/course.model';
import { RecommendationService } from './recommendations.service';
import { RecommendationTypes } from 'app/shared/model/enumerations/recommendation-types'

@Component({
  selector: 'jhi-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.scss']
})
export class RecommendationComponent implements OnInit, OnDestroy {
  recommendationParticipant?: ICourse[];
  recommendationSME?: ICourse[];
  eventSubscriber?: Subscription;

  constructor(protected recommendationService: RecommendationService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.recommendationService.getRecommendations(RecommendationTypes.SME).subscribe((res: HttpResponse<ICourse[]>) => {
      const reco = res.body || [];
      this.recommendationSME = reco.slice(0,6);
    });
    this.recommendationService.getRecommendations(RecommendationTypes.PARTICIPANT).subscribe((res: HttpResponse<ICourse[]>) => {
      const reco = res.body || [];
      this.recommendationParticipant = reco.slice(0,8);
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSkills();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  registerChangeInSkills(): void {
    this.eventSubscriber = this.eventManager.subscribe('skillListModification', () => this.loadAll());
  }
}
