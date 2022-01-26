package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseReviewStatus} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseReviewStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-review-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseReviewStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter status;

    private LocalDateFilter statusUpdatedOn;

    private StringFilter feedback;

    private LongFilter userId;

    private LongFilter courseSessionId;

    private Boolean distinct;

    public CourseReviewStatusCriteria() {}

    public CourseReviewStatusCriteria(CourseReviewStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.statusUpdatedOn = other.statusUpdatedOn == null ? null : other.statusUpdatedOn.copy();
        this.feedback = other.feedback == null ? null : other.feedback.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.courseSessionId = other.courseSessionId == null ? null : other.courseSessionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseReviewStatusCriteria copy() {
        return new CourseReviewStatusCriteria(this);
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

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getStatusUpdatedOn() {
        return statusUpdatedOn;
    }

    public LocalDateFilter statusUpdatedOn() {
        if (statusUpdatedOn == null) {
            statusUpdatedOn = new LocalDateFilter();
        }
        return statusUpdatedOn;
    }

    public void setStatusUpdatedOn(LocalDateFilter statusUpdatedOn) {
        this.statusUpdatedOn = statusUpdatedOn;
    }

    public StringFilter getFeedback() {
        return feedback;
    }

    public StringFilter feedback() {
        if (feedback == null) {
            feedback = new StringFilter();
        }
        return feedback;
    }

    public void setFeedback(StringFilter feedback) {
        this.feedback = feedback;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCourseSessionId() {
        return courseSessionId;
    }

    public LongFilter courseSessionId() {
        if (courseSessionId == null) {
            courseSessionId = new LongFilter();
        }
        return courseSessionId;
    }

    public void setCourseSessionId(LongFilter courseSessionId) {
        this.courseSessionId = courseSessionId;
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
        final CourseReviewStatusCriteria that = (CourseReviewStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(statusUpdatedOn, that.statusUpdatedOn) &&
            Objects.equals(feedback, that.feedback) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(courseSessionId, that.courseSessionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, statusUpdatedOn, feedback, userId, courseSessionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseReviewStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (statusUpdatedOn != null ? "statusUpdatedOn=" + statusUpdatedOn + ", " : "") +
            (feedback != null ? "feedback=" + feedback + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (courseSessionId != null ? "courseSessionId=" + courseSessionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
