package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseAssignment} entity.
 */
public class CourseAssignmentDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    private String assignmentTitle;

    @Size(min = 10, max = 400)
    private String assignmentDescription;

    @NotNull
    private Integer assignmentOrder;

    @Size(min = 10, max = 42)
    private String assignmentResource;

    @NotNull
    private Boolean isPreview;

    @NotNull
    private Boolean isDraft;

    @NotNull
    private Boolean isApproved;

    @NotNull
    private Boolean isPublished;

    private CourseSessionDTO courseSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public Integer getAssignmentOrder() {
        return assignmentOrder;
    }

    public void setAssignmentOrder(Integer assignmentOrder) {
        this.assignmentOrder = assignmentOrder;
    }

    public String getAssignmentResource() {
        return assignmentResource;
    }

    public void setAssignmentResource(String assignmentResource) {
        this.assignmentResource = assignmentResource;
    }

    public Boolean getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
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

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public CourseSessionDTO getCourseSession() {
        return courseSession;
    }

    public void setCourseSession(CourseSessionDTO courseSession) {
        this.courseSession = courseSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentDTO)) {
            return false;
        }

        CourseAssignmentDTO courseAssignmentDTO = (CourseAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentDTO{" +
            "id=" + getId() +
            ", assignmentTitle='" + getAssignmentTitle() + "'" +
            ", assignmentDescription='" + getAssignmentDescription() + "'" +
            ", assignmentOrder=" + getAssignmentOrder() +
            ", assignmentResource='" + getAssignmentResource() + "'" +
            ", isPreview='" + getIsPreview() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            ", courseSession=" + getCourseSession() +
            "}";
    }
}
