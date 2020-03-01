package com.ms.learnapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import com.ms.learnapp.domain.enumeration.SkillTypeValues;

/**
 * A DTO for the {@link com.ms.learnapp.domain.Skill} entity.
 */
public class SkillDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private SkillTypeValues skillType;

    private List<String> skillAliases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTypeValues getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillTypeValues skillType) {
        this.skillType = skillType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (skillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", skillType='" + getSkillType() + "'" +
            "}";
    }

    public List<String> getSkillAliases() {
        return skillAliases;
    }

    public void setSkillAliases(List<String> skillAliases) {
        this.skillAliases = skillAliases;
    }
}
