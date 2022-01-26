package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseSection.
 */
@Entity
@Table(name = "course_section")
public class CourseSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "section_title", length = 255, nullable = false)
    private String sectionTitle;

    @Size(max = 255)
    @Column(name = "section_description", length = 255)
    private String sectionDescription;

    @NotNull
    @Column(name = "section_order", nullable = false)
    private Integer sectionOrder;

    @NotNull
    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft;

    @NotNull
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseLevel", "courseCategory", "courseType", "user", "reviewer" }, allowSetters = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseSection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectionTitle() {
        return this.sectionTitle;
    }

    public CourseSection sectionTitle(String sectionTitle) {
        this.setSectionTitle(sectionTitle);
        return this;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionDescription() {
        return this.sectionDescription;
    }

    public CourseSection sectionDescription(String sectionDescription) {
        this.setSectionDescription(sectionDescription);
        return this;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public Integer getSectionOrder() {
        return this.sectionOrder;
    }

    public CourseSection sectionOrder(Integer sectionOrder) {
        this.setSectionOrder(sectionOrder);
        return this;
    }

    public void setSectionOrder(Integer sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public CourseSection isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public CourseSection isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseSection course(Course course) {
        this.setCourse(course);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSection)) {
            return false;
        }
        return id != null && id.equals(((CourseSection) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSection{" +
            "id=" + getId() +
            ", sectionTitle='" + getSectionTitle() + "'" +
            ", sectionDescription='" + getSectionDescription() + "'" +
            ", sectionOrder=" + getSectionOrder() +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            "}";
    }
}
