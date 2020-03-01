package com.ms.learnapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

import com.ms.learnapp.domain.enumeration.SkillTypeValues;

/**
 * A Skill.
 */
@Document(collection = "skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("skill_type")
    private SkillTypeValues skillType;

    @Field("skill_aliases")
    private List<String> skillAliases;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTypeValues getSkillType() {
        return skillType;
    }

    public Skill skillType(SkillTypeValues skillType) {
        this.skillType = skillType;
        return this;
    }

    public void setSkillType(SkillTypeValues skillType) {
        this.skillType = skillType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Skill{" +
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
