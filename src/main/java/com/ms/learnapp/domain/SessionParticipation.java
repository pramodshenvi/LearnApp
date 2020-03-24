package com.ms.learnapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A SessionParticipation.
 */
@Document(collection = "session_participation")
public class SessionParticipation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("session_id")
    private String sessionId;

    @NotNull
    @Field("user_name")
    private String userName;

    @NotNull
    @Field("user_email")
    private String userEmail;

    @Field("registration_date_time")
    private ZonedDateTime registrationDateTime;

    @Field("attendance_date_time")
    private ZonedDateTime attendanceDateTime;

    @NotNull
    @Field("user_id")
    private String userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public SessionParticipation sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public SessionParticipation userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public SessionParticipation userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ZonedDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public SessionParticipation registrationDateTime(ZonedDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
        return this;
    }

    public void setRegistrationDateTime(ZonedDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public ZonedDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public SessionParticipation attendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        return this;
    }

    public void setAttendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public SessionParticipation userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionParticipation)) {
            return false;
        }
        return id != null && id.equals(((SessionParticipation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SessionParticipation{" +
            "id=" + getId() +
            ", sessionId='" + getSessionId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", registrationDateTime='" + getRegistrationDateTime() + "'" +
            ", attendanceDateTime='" + getAttendanceDateTime() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
