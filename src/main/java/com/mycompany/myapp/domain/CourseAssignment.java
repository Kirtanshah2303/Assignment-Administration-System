package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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
    @Size(max = 255)
    @Column(name = "assignment_title", length = 255, nullable = false)
    private String assignmentTitle;

    @Size(max = 255)
    @Column(name = "assignment_description", length = 255)
    private String assignmentDescription;

    @NotNull
    @Size(max = 300)
    @Column(name = "session_video", length = 300, nullable = false)
    private String sessionVideo;

    @NotNull
    @Column(name = "session_duration", nullable = false)
    private Instant sessionDuration;

    @NotNull
    @Column(name = "assignment_order", nullable = false)
    private Integer assignmentOrder;

    @Size(max = 300)
    @Column(name = "assignment_resource", length = 300)
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
    @JsonIgnoreProperties(value = { "course" }, allowSetters = true)
    private CourseSection courseSection;

    @OneToMany(mappedBy = "courseAssignment")
    @JsonIgnoreProperties(value = { "courseAssignment", "user" }, allowSetters = true)
    private Set<CourseAssignmentInput> courseAssignmentInputs = new HashSet<>();

    @OneToMany(mappedBy = "courseAssignment")
    @JsonIgnoreProperties(value = { "courseAssignment" }, allowSetters = true)
    private Set<CourseAssignmentOutput> courseAssignmentOutputs = new HashSet<>();

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

    public String getSessionVideo() {
        return this.sessionVideo;
    }

    public CourseAssignment sessionVideo(String sessionVideo) {
        this.setSessionVideo(sessionVideo);
        return this;
    }

    public void setSessionVideo(String sessionVideo) {
        this.sessionVideo = sessionVideo;
    }

    public Instant getSessionDuration() {
        return this.sessionDuration;
    }

    public CourseAssignment sessionDuration(Instant sessionDuration) {
        this.setSessionDuration(sessionDuration);
        return this;
    }

    public void setSessionDuration(Instant sessionDuration) {
        this.sessionDuration = sessionDuration;
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

    public CourseSection getCourseSection() {
        return this.courseSection;
    }

    public void setCourseSection(CourseSection courseSection) {
        this.courseSection = courseSection;
    }

    public CourseAssignment courseSection(CourseSection courseSection) {
        this.setCourseSection(courseSection);
        return this;
    }

    public Set<CourseAssignmentInput> getCourseAssignmentInputs() {
        return this.courseAssignmentInputs;
    }

    public void setCourseAssignmentInputs(Set<CourseAssignmentInput> courseAssignmentInputs) {
        if (this.courseAssignmentInputs != null) {
            this.courseAssignmentInputs.forEach(i -> i.setCourseAssignment(null));
        }
        if (courseAssignmentInputs != null) {
            courseAssignmentInputs.forEach(i -> i.setCourseAssignment(this));
        }
        this.courseAssignmentInputs = courseAssignmentInputs;
    }

    public CourseAssignment courseAssignmentInputs(Set<CourseAssignmentInput> courseAssignmentInputs) {
        this.setCourseAssignmentInputs(courseAssignmentInputs);
        return this;
    }

    public CourseAssignment addCourseAssignmentInput(CourseAssignmentInput courseAssignmentInput) {
        this.courseAssignmentInputs.add(courseAssignmentInput);
        courseAssignmentInput.setCourseAssignment(this);
        return this;
    }

    public CourseAssignment removeCourseAssignmentInput(CourseAssignmentInput courseAssignmentInput) {
        this.courseAssignmentInputs.remove(courseAssignmentInput);
        courseAssignmentInput.setCourseAssignment(null);
        return this;
    }

    public Set<CourseAssignmentOutput> getCourseAssignmentOutputs() {
        return this.courseAssignmentOutputs;
    }

    public void setCourseAssignmentOutputs(Set<CourseAssignmentOutput> courseAssignmentOutputs) {
        if (this.courseAssignmentOutputs != null) {
            this.courseAssignmentOutputs.forEach(i -> i.setCourseAssignment(null));
        }
        if (courseAssignmentOutputs != null) {
            courseAssignmentOutputs.forEach(i -> i.setCourseAssignment(this));
        }
        this.courseAssignmentOutputs = courseAssignmentOutputs;
    }

    public CourseAssignment courseAssignmentOutputs(Set<CourseAssignmentOutput> courseAssignmentOutputs) {
        this.setCourseAssignmentOutputs(courseAssignmentOutputs);
        return this;
    }

    public CourseAssignment addCourseAssignmentOutput(CourseAssignmentOutput courseAssignmentOutput) {
        this.courseAssignmentOutputs.add(courseAssignmentOutput);
        courseAssignmentOutput.setCourseAssignment(this);
        return this;
    }

    public CourseAssignment removeCourseAssignmentOutput(CourseAssignmentOutput courseAssignmentOutput) {
        this.courseAssignmentOutputs.remove(courseAssignmentOutput);
        courseAssignmentOutput.setCourseAssignment(null);
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
            ", sessionVideo='" + getSessionVideo() + "'" +
            ", sessionDuration='" + getSessionDuration() + "'" +
            ", assignmentOrder=" + getAssignmentOrder() +
            ", assignmentResource='" + getAssignmentResource() + "'" +
            ", isPreview='" + getIsPreview() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            "}";
    }
}
