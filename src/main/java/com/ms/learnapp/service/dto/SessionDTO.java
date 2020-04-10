package com.ms.learnapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import com.ms.learnapp.domain.enumeration.SessionLocation;
import com.ms.learnapp.domain.enumeration.AttendanceLocationTypes;

/**
 * A DTO for the {@link com.ms.learnapp.domain.Session} entity.
 */
public class SessionDTO implements Serializable {

    private String id;

    @NotNull
    private String courseId;

    @NotNull
    private String topic;

    @NotNull
    private String agenda;

    private ZonedDateTime sessionDateTime;

    private Boolean sessionComplete;

    @NotNull
    private SessionLocation location;

    private String sessionPreRequisites;

    private List<String> assignedSMEs;

    private AttendanceLocationTypes attendanceLocation;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public ZonedDateTime getSessionDateTime() {
        return sessionDateTime;
    }

    public void setSessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public SessionLocation getLocation() {
        return location;
    }

    public void setLocation(SessionLocation location) {
        this.location = location;
    }

    public String getSessionPreRequisites() {
        return sessionPreRequisites;
    }

    public void setSessionPreRequisites(String sessionPreRequisites) {
        this.sessionPreRequisites = sessionPreRequisites;
    }

    public List<String> getAssignedSMEs() {
        return assignedSMEs;
    }

    public void setAssignedSMEs(List<String> assignedSMEs) {
        this.assignedSMEs = assignedSMEs;
    }

    public AttendanceLocationTypes getAttendanceLocation() {
        return attendanceLocation;
    }

    public void setAttendanceLocation(AttendanceLocationTypes attendanceLocation) {
        this.attendanceLocation = attendanceLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (sessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", courseId='" + getCourseId() + "'" +
            ", topic='" + getTopic() + "'" +
            ", agenda='" + getAgenda() + "'" +
            ", sessionDateTime='" + getSessionDateTime() + "'" +
            ", location='" + getLocation() + "'" +
            ", sessionPreRequisites='" + getSessionPreRequisites() + "'" +
            ", assignedSMEs='" + getAssignedSMEs() + "'" +
            ", attendanceLocation='" + getAttendanceLocation() + "'" +
            "}";
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Boolean getSessionComplete() {
        return sessionComplete;
    }

    public void setSessionComplete(Boolean sessionComplete) {
        this.sessionComplete = sessionComplete;
    }
}
