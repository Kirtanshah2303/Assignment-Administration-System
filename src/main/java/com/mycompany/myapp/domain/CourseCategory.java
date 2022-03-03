package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseCategory.
 */
@Entity
@Table(name = "course_category")
public class CourseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "course_category_title", length = 42, nullable = false)
    private String courseCategoryTitle;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "logo", length = 42, nullable = false)
    private String logo;

    @NotNull
    @Column(name = "is_parent", nullable = false)
    private Boolean isParent;

    @NotNull
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    @Size(min = 10, max = 400)
    @Column(name = "description", length = 400)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCategoryTitle() {
        return this.courseCategoryTitle;
    }

    public CourseCategory courseCategoryTitle(String courseCategoryTitle) {
        this.setCourseCategoryTitle(courseCategoryTitle);
        return this;
    }

    public void setCourseCategoryTitle(String courseCategoryTitle) {
        this.courseCategoryTitle = courseCategoryTitle;
    }

    public String getLogo() {
        return this.logo;
    }

    public CourseCategory logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIsParent() {
        return this.isParent;
    }

    public CourseCategory isParent(Boolean isParent) {
        this.setIsParent(isParent);
        return this;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public CourseCategory parentId(Integer parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return this.description;
    }

    public CourseCategory description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseCategory)) {
            return false;
        }
        return id != null && id.equals(((CourseCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCategory{" +
            "id=" + getId() +
            ", courseCategoryTitle='" + getCourseCategoryTitle() + "'" +
            ", logo='" + getLogo() + "'" +
            ", isParent='" + getIsParent() + "'" +
            ", parentId=" + getParentId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
