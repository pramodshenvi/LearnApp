package com.ms.learnapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.ms.learnapp.domain.Course} entity.
 */
public class CourseDTO implements Serializable {

    private String id;

    @NotNull
    private String courseName;

    @NotNull
    private List<String> smeSkills;

    private String preRequisites;

    @NotNull
    private Integer smePoints;

    private Integer participantPoints;

    private String imagePath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getSmeSkills() {
        return smeSkills;
    }

    public void setSmeSkills(List<String> smeSkills) {
        this.smeSkills = smeSkills;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (courseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", smeSkills='" + getSmeSkills() + "'" +
            ", preRequisites='" + getPreRequisites() + "'" +
            ", smePoints=" + getSmePoints() +
            ", participantPoints=" + getParticipantPoints() +
            ", imagePath='" + getImagePath() + "'" +
            "}";
    }

    public Integer getSmePoints() {
        return smePoints;
    }

    public void setSmePoints(Integer smePoints) {
        this.smePoints = smePoints;
    }

    public Integer getParticipantPoints() {
        return participantPoints;
    }

    public void setParticipantPoints(Integer participantPoints) {
        this.participantPoints = participantPoints;
    }
}
