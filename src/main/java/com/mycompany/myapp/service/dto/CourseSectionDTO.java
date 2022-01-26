package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseSection} entity.
 */
public class CourseSectionDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 255)
    private String sectionTitle;

    @Size(max = 255)
    private String sectionDescription;

    @NotNull
    private Integer sectionOrder;

    @NotNull
    private Boolean isDraft;

    @NotNull
    private Boolean isApproved;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionDescription() {
        return sectionDescription;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public Integer getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(Integer sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSectionDTO)) {
            return false;
        }

        CourseSectionDTO courseSectionDTO = (CourseSectionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseSectionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSectionDTO{" +
            "id=" + getId() +
            ", sectionTitle='" + getSectionTitle() + "'" +
            ", sectionDescription='" + getSectionDescription() + "'" +
            ", sectionOrder=" + getSectionOrder() +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", course=" + getCourse() +
            "}";
    }
}
