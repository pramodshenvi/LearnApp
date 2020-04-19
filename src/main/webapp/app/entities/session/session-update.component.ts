import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { MessengerService } from 'app/shared/util/messenger-service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  courseDetails: any = {};
  allowableSMEs: string[] = [];
  assignedSMEs: string[] = [];

  editForm = this.fb.group({
    id: [],
    topic: [null, [Validators.required]],
    agenda: [null, [Validators.required]],
    sessionDateTime: [],
    location: [null, [Validators.required]],
    sessionPreRequisites: [],
    attendanceLocation: []
  });

  constructor(
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected messengerService: MessengerService,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.courseDetails = this.messengerService.getCourseDetails();
    if (!this.courseDetails) this.router.navigate(['/course']);
    else {
      this.allowableSMEs = this.courseDetails.courseSMEs;
      this.activatedRoute.data.subscribe(({ session }) => {
        if (!session.id) {
          const today = moment().startOf('day');
          session.sessionDateTime = today;
        }

        this.updateForm(session);
      });
    }
  }

  updateForm(session: ISession): void {
    this.assignedSMEs = session.assignedSMEs || [];
    this.editForm.patchValue({
      id: session.id,
      topic: session.topic,
      agenda: session.agenda,
      sessionDateTime: session.sessionDateTime ? session.sessionDateTime.format(DATE_TIME_FORMAT) : null,
      location: session.location,
      sessionPreRequisites: session.sessionPreRequisites,
      attendanceLocation: session.attendanceLocation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      courseId: this.courseDetails['id'],
      topic: this.editForm.get(['topic'])!.value,
      agenda: this.editForm.get(['agenda'])!.value,
      sessionDateTime: this.editForm.get(['sessionDateTime'])!.value
        ? moment(this.editForm.get(['sessionDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sessionComplete: false,
      location: this.editForm.get(['location'])!.value,
      sessionPreRequisites: this.editForm.get(['sessionPreRequisites'])!.value,
      assignedSMEs: this.assignedSMEs,
      attendanceLocation: this.editForm.get(['attendanceLocation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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
