package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseSession} entity.
 */
public class CourseSessionDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    private String sessionTitle;

    @Size(min = 10, max = 400)
    private String sessionDescription;

    @NotNull
    @Size(min = 10, max = 42)
    private String sessionVideo;

    @NotNull
    private Instant sessionDuration;

    @NotNull
    private Integer sessionOrder;

    @Size(min = 10, max = 42)
    private String sessionResource;

    @NotNull
    private Boolean isPreview;

    @NotNull
    private Boolean isDraft;

    @NotNull
    private Boolean isApproved;

    @NotNull
    private Boolean isPublished;

    @Size(min = 10, max = 42)
    private String sessionLocation;

    @Size(min = 10, max = 42)
    private String quizLink;

    private CourseSectionDTO courseSection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionVideo() {
        return sessionVideo;
    }

    public void setSessionVideo(String sessionVideo) {
        this.sessionVideo = sessionVideo;
    }

    public Instant getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Instant sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public Integer getSessionOrder() {
        return sessionOrder;
    }

    public void setSessionOrder(Integer sessionOrder) {
        this.sessionOrder = sessionOrder;
    }

    public String getSessionResource() {
        return sessionResource;
    }

    public void setSessionResource(String sessionResource) {
        this.sessionResource = sessionResource;
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

    public String getSessionLocation() {
        return sessionLocation;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public String getQuizLink() {
        return quizLink;
    }

    public void setQuizLink(String quizLink) {
        this.quizLink = quizLink;
    }

    public CourseSectionDTO getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(CourseSectionDTO courseSection) {
        this.courseSection = courseSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSessionDTO)) {
            return false;
        }

        CourseSessionDTO courseSessionDTO = (CourseSessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseSessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSessionDTO{" +
            "id=" + getId() +
            ", sessionTitle='" + getSessionTitle() + "'" +
            ", sessionDescription='" + getSessionDescription() + "'" +
            ", sessionVideo='" + getSessionVideo() + "'" +
            ", sessionDuration='" + getSessionDuration() + "'" +
            ", sessionOrder=" + getSessionOrder() +
            ", sessionResource='" + getSessionResource() + "'" +
            ", isPreview='" + getIsPreview() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            ", sessionLocation='" + getSessionLocation() + "'" +
            ", quizLink='" + getQuizLink() + "'" +
            ", courseSection=" + getCourseSection() +
            "}";
    }
}
