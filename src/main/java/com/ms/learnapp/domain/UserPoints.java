package com.ms.learnapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.ms.learnapp.domain.enumeration.PointsFor;

/**
 * A UserPoints.
 */
@Document(collection = "user_points")
public class UserPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

    @NotNull
    @Field("session_id")
    private String sessionId;

    @NotNull
    @Field("session_topic")
    private String sessionTopic;

    @NotNull
    @Field("session_date_time")
    private ZonedDateTime sessionDateTime;

    @NotNull
    @Field("points_for")
    private PointsFor pointsFor;

    @NotNull
    @Field("points")
    private Integer points;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserPoints userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UserPoints sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTopic() {
        return sessionTopic;
    }

    public UserPoints sessionTopic(String sessionTopic) {
        this.sessionTopic = sessionTopic;
        return this;
    }

    public void setSessionTopic(String sessionTopic) {
        this.sessionTopic = sessionTopic;
    }

    public ZonedDateTime getSessionDateTime() {
        return sessionDateTime;
    }

    public UserPoints sessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
        return this;
    }

    public void setSessionDateTime(ZonedDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public PointsFor getPointsFor() {
        return pointsFor;
    }

    public UserPoints pointsFor(PointsFor pointsFor) {
        this.pointsFor = pointsFor;
        return this;
    }

    public void setPointsFor(PointsFor pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Integer getPoints() {
        return points;
    }

    public UserPoints points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPoints)) {
            return false;
        }
        return id != null && id.equals(((UserPoints) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserPoints{" +
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
