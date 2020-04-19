package com.ms.learnapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.ms.learnapp.domain.enumeration.PointsFor;

/**
 * A DTO for the {@link com.ms.learnapp.domain.UserPoints} entity.
 */
public class UserPointsDTO implements Serializable {

    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String sessionId;

    @NotNull
    private String sessionTopic;

    @NotNull
    private ZonedDateTime sessionDateTime;

    @NotNull
    private PointsFor pointsFor;

    @NotNull
    private Integer points;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTopic() {
        return sessionTopic;
    }

    public void setSessionTopic(String sessionTopic) {
        this.sessionTopic = sessionTopic;
    }

    public ZonedDateTime getSessionDateTime() {
        return sessionDateTime;
    }

    public void setSessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public PointsFor getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(PointsFor pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPointsDTO userPointsDTO = (UserPointsDTO) o;
        if (userPointsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPointsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPointsDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", sessionId='" + getSessionId() + "'" +
            ", sessionTopic='" + getSessionTopic() + "'" +
            ", sessionDateTime='" + getSessionDateTime() + "'" +
            ", pointsFor='" + getPointsFor() + "'" +
            ", points=" + getPoints() +
            "}";
    }
}
