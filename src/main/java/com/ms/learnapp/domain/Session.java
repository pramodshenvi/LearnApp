package com.ms.learnapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.ms.learnapp.domain.enumeration.SessionLocation;

import com.ms.learnapp.domain.enumeration.AttendanceLocationTypes;

/**
 * A Session.
 */
@Document(collection = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("topic")
    private String topic;

    @NotNull
    @Field("agenda")
    private String agenda;

    @Field("session_date_time")
    private ZonedDateTime sessionDateTime;

    @NotNull
    @Field("location")
    private SessionLocation location;

    @Field("session_pre_requisites")
    private String sessionPreRequisites;

    @Field("assigned_sm_es")
    private String assignedSMEs;

    @Field("attendance_location")
    private AttendanceLocationTypes attendanceLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public Session topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAgenda() {
        return agenda;
    }

    public Session agenda(String agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public ZonedDateTime getSessionDateTime() {
        return sessionDateTime;
    }

    public Session sessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
        return this;
    }

    public void setSessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public SessionLocation getLocation() {
        return location;
    }

    public Session location(SessionLocation location) {
        this.location = location;
        return this;
    }

    public void setLocation(SessionLocation location) {
        this.location = location;
    }

    public String getSessionPreRequisites() {
        return sessionPreRequisites;
    }

    public Session sessionPreRequisites(String sessionPreRequisites) {
        this.sessionPreRequisites = sessionPreRequisites;
        return this;
    }

    public void setSessionPreRequisites(String sessionPreRequisites) {
        this.sessionPreRequisites = sessionPreRequisites;
    }

    public String getAssignedSMEs() {
        return assignedSMEs;
    }

    public Session assignedSMEs(String assignedSMEs) {
        this.assignedSMEs = assignedSMEs;
        return this;
    }

    public void setAssignedSMEs(String assignedSMEs) {
        this.assignedSMEs = assignedSMEs;
    }

    public AttendanceLocationTypes getAttendanceLocation() {
        return attendanceLocation;
    }

    public Session attendanceLocation(AttendanceLocationTypes attendanceLocation) {
        this.attendanceLocation = attendanceLocation;
        return this;
    }

    public void setAttendanceLocation(AttendanceLocationTypes attendanceLocation) {
        this.attendanceLocation = attendanceLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            ", agenda='" + getAgenda() + "'" +
            ", sessionDateTime='" + getSessionDateTime() + "'" +
            ", location='" + getLocation() + "'" +
            ", sessionPreRequisites='" + getSessionPreRequisites() + "'" +
            ", assignedSMEs='" + getAssignedSMEs() + "'" +
            ", attendanceLocation='" + getAttendanceLocation() + "'" +
            "}";
    }
}
