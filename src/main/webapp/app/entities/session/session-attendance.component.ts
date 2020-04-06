import { Component, OnInit } from '@angular/core';

import { SessionParticipationService } from 'app/entities/session-participation/session-participation.service';
import { Input } from '@angular/core';
import { ISessionParticipation } from 'app/shared/model/session-participation.model';
import { HttpResponse } from '@angular/common/http';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'jhi-session-attendance',
  templateUrl: './session-attendance.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionAttendanceComponent implements OnInit {
  @Input() session: any;
  dataSource: MatTableDataSource<ISessionParticipation> | null = null;
  displayedColumns: string[] = ['name', 'email', 'registrationDateTime', 'attendanceDateTime'];

  constructor(
      protected sessionParticipationService: SessionParticipationService) {}

  ngOnInit(): void {
    if(this.session && this.session.sessionComplete) {
      this.sessionParticipationService
        .query({
          sessionId: this.session.id
        })
        .subscribe((res: HttpResponse<ISessionParticipation[]>) => {
          if(res && res.body) {
            this.dataSource = new MatTableDataSource(res.body);
          }
        });
    }
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    if(this.dataSource)
      this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  downloadAttendeeList(): void {
    /* ToDo complete this */
  }
}
