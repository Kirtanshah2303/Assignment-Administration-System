package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseSession.
 */
@Entity
@Table(name = "course_session")
public class CourseSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "session_title", length = 42, nullable = false)
    private String sessionTitle;

    @Size(min = 10, max = 400)
    @Column(name = "session_description", length = 400)
    private String sessionDescription;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "session_video", length = 42, nullable = false)
    private String sessionVideo;

    @NotNull
    @Column(name = "session_duration", nullable = false)
    private Long sessionDuration;

    @NotNull
    @Column(name = "session_order", nullable = false)
    private Integer sessionOrder;

    @Size(min = 10, max = 42)
    @Column(name = "session_resource", length = 42)
    private String sessionResource;

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

    @Size(min = 10, max = 42)
    @Column(name = "session_location", length = 42)
    private String sessionLocation;

    @Size(min = 10, max = 42)
    @Column(name = "quiz_link", length = 42)
    private String quizLink;

    @ManyToOne
    @JsonIgnoreProperties(value = { "course" }, allowSetters = true)
    private CourseSection courseSection;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseSession id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionTitle() {
        return this.sessionTitle;
    }

    public CourseSession sessionTitle(String sessionTitle) {
        this.setSessionTitle(sessionTitle);
        return this;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionDescription() {
        return this.sessionDescription;
    }

    public CourseSession sessionDescription(String sessionDescription) {
        this.setSessionDescription(sessionDescription);
        return this;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionVideo() {
        return this.sessionVideo;
    }

    public CourseSession sessionVideo(String sessionVideo) {
        this.setSessionVideo(sessionVideo);
        return this;
    }

    public void setSessionVideo(String sessionVideo) {
        this.sessionVideo = sessionVideo;
    }

    public Long getSessionDuration() {
        return this.sessionDuration;
    }

    public CourseSession sessionDuration(Long sessionDuration) {
        this.setSessionDuration(sessionDuration);
        return this;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public Integer getSessionOrder() {
        return this.sessionOrder;
    }

    public CourseSession sessionOrder(Integer sessionOrder) {
        this.setSessionOrder(sessionOrder);
        return this;
    }

    public void setSessionOrder(Integer sessionOrder) {
        this.sessionOrder = sessionOrder;
    }

    public String getSessionResource() {
        return this.sessionResource;
    }

    public CourseSession sessionResource(String sessionResource) {
        this.setSessionResource(sessionResource);
        return this;
    }

    public void setSessionResource(String sessionResource) {
        this.sessionResource = sessionResource;
    }

    public Boolean getIsPreview() {
        return this.isPreview;
    }

    public CourseSession isPreview(Boolean isPreview) {
        this.setIsPreview(isPreview);
        return this;
    }

    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public CourseSession isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public CourseSession isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public CourseSession isPublished(Boolean isPublished) {
        this.setIsPublished(isPublished);
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getSessionLocation() {
        return this.sessionLocation;
    }

    public CourseSession sessionLocation(String sessionLocation) {
        this.setSessionLocation(sessionLocation);
        return this;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public String getQuizLink() {
        return this.quizLink;
    }

    public CourseSession quizLink(String quizLink) {
        this.setQuizLink(quizLink);
        return this;
    }

    public void setQuizLink(String quizLink) {
        this.quizLink = quizLink;
    }

    public CourseSection getCourseSection() {
        return this.courseSection;
    }

    public void setCourseSection(CourseSection courseSection) {
        this.courseSection = courseSection;
    }

    public CourseSession courseSection(CourseSection courseSection) {
        this.setCourseSection(courseSection);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSession)) {
            return false;
        }
        return id != null && id.equals(((CourseSession) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSession{" +
            "id=" + getId() +
            ", sessionTitle='" + getSessionTitle() + "'" +
            ", sessionDescription='" + getSessionDescription() + "'" +
            ", sessionVideo='" + getSessionVideo() + "'" +
            ", sessionDuration=" + getSessionDuration() +
            ", sessionOrder=" + getSessionOrder() +
            ", sessionResource='" + getSessionResource() + "'" +
            ", isPreview='" + getIsPreview() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            ", sessionLocation='" + getSessionLocation() + "'" +
            ", quizLink='" + getQuizLink() + "'" +
            "}";
    }
}
