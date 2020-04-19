package com.ms.learnapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ms.learnapp.domain.SessionParticipation} entity.
 */
public class SessionParticipationDTO implements Serializable {

    private String id;

    @NotNull
    private String sessionId;

    @NotNull
    private String courseId;

    @NotNull
    private String userName;

    @NotNull
    private String userEmail;

    private ZonedDateTime registrationDateTime;

    private ZonedDateTime attendanceDateTime;

    @NotNull
    private String userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ZonedDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(ZonedDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public ZonedDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public void setAttendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionParticipationDTO sessionParticipationDTO = (SessionParticipationDTO) o;
        if (sessionParticipationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionParticipationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionParticipationDTO{" +
            "id=" + getId() +
            ", sessionId='" + getSessionId() + "'" +
            ", courseId='" + getCourseId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", registrationDateTime='" + getRegistrationDateTime() + "'" +
            ", attendanceDateTime='" + getAttendanceDateTime() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
