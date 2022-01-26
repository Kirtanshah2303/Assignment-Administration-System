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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseEnrollment} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseEnrollmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-enrollments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseEnrollmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter enrollementDate;

    private LocalDateFilter lastAccessedDate;

    private LongFilter userId;

    private LongFilter courseId;

    private Boolean distinct;

    public CourseEnrollmentCriteria() {}

    public CourseEnrollmentCriteria(CourseEnrollmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.enrollementDate = other.enrollementDate == null ? null : other.enrollementDate.copy();
        this.lastAccessedDate = other.lastAccessedDate == null ? null : other.lastAccessedDate.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseEnrollmentCriteria copy() {
        return new CourseEnrollmentCriteria(this);
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

    public LocalDateFilter getEnrollementDate() {
        return enrollementDate;
    }

    public LocalDateFilter enrollementDate() {
        if (enrollementDate == null) {
            enrollementDate = new LocalDateFilter();
        }
        return enrollementDate;
    }

    public void setEnrollementDate(LocalDateFilter enrollementDate) {
        this.enrollementDate = enrollementDate;
    }

    public LocalDateFilter getLastAccessedDate() {
        return lastAccessedDate;
    }

    public LocalDateFilter lastAccessedDate() {
        if (lastAccessedDate == null) {
            lastAccessedDate = new LocalDateFilter();
        }
        return lastAccessedDate;
    }

    public void setLastAccessedDate(LocalDateFilter lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
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

    public LongFilter getCourseId() {
        return courseId;
    }

    public LongFilter courseId() {
        if (courseId == null) {
            courseId = new LongFilter();
        }
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
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
        final CourseEnrollmentCriteria that = (CourseEnrollmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(enrollementDate, that.enrollementDate) &&
            Objects.equals(lastAccessedDate, that.lastAccessedDate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enrollementDate, lastAccessedDate, userId, courseId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseEnrollmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (enrollementDate != null ? "enrollementDate=" + enrollementDate + ", " : "") +
            (lastAccessedDate != null ? "lastAccessedDate=" + lastAccessedDate + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (courseId != null ? "courseId=" + courseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
