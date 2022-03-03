package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseSession} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseSessionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-sessions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseSessionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter sessionTitle;

    private StringFilter sessionDescription;

    private StringFilter sessionVideo;

    private InstantFilter sessionDuration;

    private IntegerFilter sessionOrder;

    private StringFilter sessionResource;

    private BooleanFilter isPreview;

    private BooleanFilter isDraft;

    private BooleanFilter isApproved;

    private BooleanFilter isPublished;

    private StringFilter sessionLocation;

    private StringFilter quizLink;

    private LongFilter courseSectionId;

    private Boolean distinct;

    public CourseSessionCriteria() {}

    public CourseSessionCriteria(CourseSessionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sessionTitle = other.sessionTitle == null ? null : other.sessionTitle.copy();
        this.sessionDescription = other.sessionDescription == null ? null : other.sessionDescription.copy();
        this.sessionVideo = other.sessionVideo == null ? null : other.sessionVideo.copy();
        this.sessionDuration = other.sessionDuration == null ? null : other.sessionDuration.copy();
        this.sessionOrder = other.sessionOrder == null ? null : other.sessionOrder.copy();
        this.sessionResource = other.sessionResource == null ? null : other.sessionResource.copy();
        this.isPreview = other.isPreview == null ? null : other.isPreview.copy();
        this.isDraft = other.isDraft == null ? null : other.isDraft.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.isPublished = other.isPublished == null ? null : other.isPublished.copy();
        this.sessionLocation = other.sessionLocation == null ? null : other.sessionLocation.copy();
        this.quizLink = other.quizLink == null ? null : other.quizLink.copy();
        this.courseSectionId = other.courseSectionId == null ? null : other.courseSectionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseSessionCriteria copy() {
        return new CourseSessionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSessionTitle() {
        return sessionTitle;
    }

    public StringFilter sessionTitle() {
        if (sessionTitle == null) {
            sessionTitle = new StringFilter();
        }
        return sessionTitle;
    }

    public void setSessionTitle(StringFilter sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public StringFilter getSessionDescription() {
        return sessionDescription;
    }

    public StringFilter sessionDescription() {
        if (sessionDescription == null) {
            sessionDescription = new StringFilter();
        }
        return sessionDescription;
    }

    public void setSessionDescription(StringFilter sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public StringFilter getSessionVideo() {
        return sessionVideo;
    }

    public StringFilter sessionVideo() {
        if (sessionVideo == null) {
            sessionVideo = new StringFilter();
        }
        return sessionVideo;
    }

    public void setSessionVideo(StringFilter sessionVideo) {
        this.sessionVideo = sessionVideo;
    }

    public InstantFilter getSessionDuration() {
        return sessionDuration;
    }

    public InstantFilter sessionDuration() {
        if (sessionDuration == null) {
            sessionDuration = new InstantFilter();
        }
        return sessionDuration;
    }

    public void setSessionDuration(InstantFilter sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public IntegerFilter getSessionOrder() {
        return sessionOrder;
    }

    public IntegerFilter sessionOrder() {
        if (sessionOrder == null) {
            sessionOrder = new IntegerFilter();
        }
        return sessionOrder;
    }

    public void setSessionOrder(IntegerFilter sessionOrder) {
        this.sessionOrder = sessionOrder;
    }

    public StringFilter getSessionResource() {
        return sessionResource;
    }

    public StringFilter sessionResource() {
        if (sessionResource == null) {
            sessionResource = new StringFilter();
        }
        return sessionResource;
    }

    public void setSessionResource(StringFilter sessionResource) {
        this.sessionResource = sessionResource;
    }

    public BooleanFilter getIsPreview() {
        return isPreview;
    }

    public BooleanFilter isPreview() {
        if (isPreview == null) {
            isPreview = new BooleanFilter();
        }
        return isPreview;
    }

    public void setIsPreview(BooleanFilter isPreview) {
        this.isPreview = isPreview;
    }

    public BooleanFilter getIsDraft() {
        return isDraft;
    }

    public BooleanFilter isDraft() {
        if (isDraft == null) {
            isDraft = new BooleanFilter();
        }
        return isDraft;
    }

    public void setIsDraft(BooleanFilter isDraft) {
        this.isDraft = isDraft;
    }

    public BooleanFilter getIsApproved() {
        return isApproved;
    }

    public BooleanFilter isApproved() {
        if (isApproved == null) {
            isApproved = new BooleanFilter();
        }
        return isApproved;
    }

    public void setIsApproved(BooleanFilter isApproved) {
        this.isApproved = isApproved;
    }

    public BooleanFilter getIsPublished() {
        return isPublished;
    }

    public BooleanFilter isPublished() {
        if (isPublished == null) {
            isPublished = new BooleanFilter();
        }
        return isPublished;
    }

    public void setIsPublished(BooleanFilter isPublished) {
        this.isPublished = isPublished;
    }

    public StringFilter getSessionLocation() {
        return sessionLocation;
    }

    public StringFilter sessionLocation() {
        if (sessionLocation == null) {
            sessionLocation = new StringFilter();
        }
        return sessionLocation;
    }

    public void setSessionLocation(StringFilter sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public StringFilter getQuizLink() {
        return quizLink;
    }

    public StringFilter quizLink() {
        if (quizLink == null) {
            quizLink = new StringFilter();
        }
        return quizLink;
    }

    public void setQuizLink(StringFilter quizLink) {
        this.quizLink = quizLink;
    }

    public LongFilter getCourseSectionId() {
        return courseSectionId;
    }

    public LongFilter courseSectionId() {
        if (courseSectionId == null) {
            courseSectionId = new LongFilter();
        }
        return courseSectionId;
    }

    public void setCourseSectionId(LongFilter courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourseSessionCriteria that = (CourseSessionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sessionTitle, that.sessionTitle) &&
            Objects.equals(sessionDescription, that.sessionDescription) &&
            Objects.equals(sessionVideo, that.sessionVideo) &&
            Objects.equals(sessionDuration, that.sessionDuration) &&
            Objects.equals(sessionOrder, that.sessionOrder) &&
            Objects.equals(sessionResource, that.sessionResource) &&
            Objects.equals(isPreview, that.isPreview) &&
            Objects.equals(isDraft, that.isDraft) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(isPublished, that.isPublished) &&
            Objects.equals(sessionLocation, that.sessionLocation) &&
            Objects.equals(quizLink, that.quizLink) &&
            Objects.equals(courseSectionId, that.courseSectionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sessionTitle,
            sessionDescription,
            sessionVideo,
            sessionDuration,
            sessionOrder,
            sessionResource,
            isPreview,
            isDraft,
            isApproved,
            isPublished,
            sessionLocation,
            quizLink,
            courseSectionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSessionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sessionTitle != null ? "sessionTitle=" + sessionTitle + ", " : "") +
            (sessionDescription != null ? "sessionDescription=" + sessionDescription + ", " : "") +
            (sessionVideo != null ? "sessionVideo=" + sessionVideo + ", " : "") +
            (sessionDuration != null ? "sessionDuration=" + sessionDuration + ", " : "") +
            (sessionOrder != null ? "sessionOrder=" + sessionOrder + ", " : "") +
            (sessionResource != null ? "sessionResource=" + sessionResource + ", " : "") +
            (isPreview != null ? "isPreview=" + isPreview + ", " : "") +
            (isDraft != null ? "isDraft=" + isDraft + ", " : "") +
            (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
            (isPublished != null ? "isPublished=" + isPublished + ", " : "") +
            (sessionLocation != null ? "sessionLocation=" + sessionLocation + ", " : "") +
            (quizLink != null ? "quizLink=" + quizLink + ", " : "") +
            (courseSectionId != null ? "courseSectionId=" + courseSectionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
