package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseAssignment.
 */
@Entity
@Table(name = "course_assignment")
public class CourseAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "assignment_title", length = 42, nullable = false)
    private String assignmentTitle;

    @Size(min = 10, max = 400)
    @Column(name = "assignment_description", length = 400)
    private String assignmentDescription;

    @NotNull
    @Column(name = "assignment_order", nullable = false)
    private Integer assignmentOrder;

    @Size(min = 10, max = 42)
    @Column(name = "assignment_resource", length = 42)
    private String assignmentResource;

    @NotNull
    @Column(name = "is_preview", nullable = false)
    private Boolean isPreview;

    @NotNull
    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft;

    @NotNull
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @NotNull
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSection" }, allowSetters = true)
    private CourseSession courseSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignmentTitle() {
        return this.assignmentTitle;
    }

    public CourseAssignment assignmentTitle(String assignmentTitle) {
        this.setAssignmentTitle(assignmentTitle);
        return this;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentDescription() {
        return this.assignmentDescription;
    }

    public CourseAssignment assignmentDescription(String assignmentDescription) {
        this.setAssignmentDescription(assignmentDescription);
        return this;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public Integer getAssignmentOrder() {
        return this.assignmentOrder;
    }

    public CourseAssignment assignmentOrder(Integer assignmentOrder) {
        this.setAssignmentOrder(assignmentOrder);
        return this;
    }

    public void setAssignmentOrder(Integer assignmentOrder) {
        this.assignmentOrder = assignmentOrder;
    }

    public String getAssignmentResource() {
        return this.assignmentResource;
    }

    public CourseAssignment assignmentResource(String assignmentResource) {
        this.setAssignmentResource(assignmentResource);
        return this;
    }

    public void setAssignmentResource(String assignmentResource) {
        this.assignmentResource = assignmentResource;
    }

    public Boolean getIsPreview() {
        return this.isPreview;
    }

    public CourseAssignment isPreview(Boolean isPreview) {
        this.setIsPreview(isPreview);
        return this;
    }

    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public CourseAssignment isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public CourseAssignment isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public CourseAssignment isPublished(Boolean isPublished) {
        this.setIsPublished(isPublished);
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public CourseSession getCourseSession() {
        return this.courseSession;
    }

    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }

    public CourseAssignment courseSession(CourseSession courseSession) {
        this.setCourseSession(courseSession);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignment)) {
            return false;
        }
        return id != null && id.equals(((CourseAssignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignment{" +
            "id=" + getId() +
            ", assignmentTitle='" + getAssignmentTitle() + "'" +
            ", assignmentDescription='" + getAssignmentDescription() + "'" +
            ", assignmentOrder=" + getAssignmentOrder() +
            ", assignmentResource='" + getAssignmentResource() + "'" +
            ", isPreview='" + getIsPreview() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            "}";
    }
}
