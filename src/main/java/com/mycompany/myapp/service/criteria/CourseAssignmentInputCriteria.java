package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseAssignmentInput} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseAssignmentInputResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-assignment-inputs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseAssignmentInputCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter input;

    private LongFilter courseAssignmentId;

    private LongFilter userId;

    private Boolean distinct;

    public CourseAssignmentInputCriteria() {}

    public CourseAssignmentInputCriteria(CourseAssignmentInputCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.input = other.input == null ? null : other.input.copy();
        this.courseAssignmentId = other.courseAssignmentId == null ? null : other.courseAssignmentId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseAssignmentInputCriteria copy() {
        return new CourseAssignmentInputCriteria(this);
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

    public StringFilter getInput() {
        return input;
    }

    public StringFilter input() {
        if (input == null) {
            input = new StringFilter();
        }
        return input;
    }

    public void setInput(StringFilter input) {
        this.input = input;
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
        final CourseAssignmentInputCriteria that = (CourseAssignmentInputCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(input, that.input) &&
            Objects.equals(courseAssignmentId, that.courseAssignmentId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, input, courseAssignmentId, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentInputCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (input != null ? "input=" + input + ", " : "") +
            (courseAssignmentId != null ? "courseAssignmentId=" + courseAssignmentId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
