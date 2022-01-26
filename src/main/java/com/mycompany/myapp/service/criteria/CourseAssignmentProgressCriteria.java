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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseAssignmentProgress} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseAssignmentProgressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-assignment-progresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseAssignmentProgressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter completed;

    private LocalDateFilter completedDate;

    private LongFilter userId;

    private LongFilter courseAssignmentId;

    private Boolean distinct;

    public CourseAssignmentProgressCriteria() {}

    public CourseAssignmentProgressCriteria(CourseAssignmentProgressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.completed = other.completed == null ? null : other.completed.copy();
        this.completedDate = other.completedDate == null ? null : other.completedDate.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.courseAssignmentId = other.courseAssignmentId == null ? null : other.courseAssignmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseAssignmentProgressCriteria copy() {
        return new CourseAssignmentProgressCriteria(this);
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

    public BooleanFilter getCompleted() {
        return completed;
    }

    public BooleanFilter completed() {
        if (completed == null) {
            completed = new BooleanFilter();
        }
        return completed;
    }

    public void setCompleted(BooleanFilter completed) {
        this.completed = completed;
    }

    public LocalDateFilter getCompletedDate() {
        return completedDate;
    }

    public LocalDateFilter completedDate() {
        if (completedDate == null) {
            completedDate = new LocalDateFilter();
        }
        return completedDate;
    }

    public void setCompletedDate(LocalDateFilter completedDate) {
        this.completedDate = completedDate;
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

    public LongFilter getCourseAssignmentId() {
        return courseAssignmentId;
    }

    public LongFilter courseAssignmentId() {
        if (courseAssignmentId == null) {
            courseAssignmentId = new LongFilter();
        }
        return courseAssignmentId;
    }

    public void setCourseAssignmentId(LongFilter courseAssignmentId) {
        this.courseAssignmentId = courseAssignmentId;
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
        final CourseAssignmentProgressCriteria that = (CourseAssignmentProgressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(completed, that.completed) &&
            Objects.equals(completedDate, that.completedDate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(courseAssignmentId, that.courseAssignmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, completed, completedDate, userId, courseAssignmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentProgressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (completed != null ? "completed=" + completed + ", " : "") +
            (completedDate != null ? "completedDate=" + completedDate + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (courseAssignmentId != null ? "courseAssignmentId=" + courseAssignmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
