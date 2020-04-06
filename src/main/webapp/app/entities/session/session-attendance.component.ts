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
  sessionParticipationList: ISessionParticipation[] = [];
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
            this.sessionParticipationList = res.body;
            this.dataSource = new MatTableDataSource(this.sessionParticipationList);
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
    if(this.sessionParticipationList) {
      const items = this.sessionParticipationList;
      const replacer = (key: any, value: any) => value === null ? '' : value;
      const header = ['userName', 'userEmail', 'registrationDateTime', 'attendanceDateTime'];
      const displayHeader = ['User Name','E-Mail Address','Registration Time','Attendance Time'];
      let csv = items.map((row: any) => header.map(fieldName => JSON.stringify(row[fieldName], replacer)).join(','));
      csv.unshift(displayHeader.join(','))
      csv.unshift('');
      csv.unshift(this.session.sessionDateTime);
      csv.unshift(this.session.topic);
      csv = [csv.join('\r\n')]

      const a = document.createElement("a");
      a.setAttribute('style', 'display:none;');
      document.body.appendChild(a);
      const blob = new Blob(csv, { type: 'text/csv' });
      const url= window.URL.createObjectURL(blob);
      a.href = url;
      const link:string ="Attendence_Report_" + this.session.topic.replace(/[^a-zA-Z0-9\s]/g,'').replace(/\s/g,'_') + '.csv';
      a.download = link.toLocaleLowerCase();
      a.click();
    }

  }
}
