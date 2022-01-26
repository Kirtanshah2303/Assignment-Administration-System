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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseAssignmentOutput} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseAssignmentOutputResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-assignment-outputs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseAssignmentOutputCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter output;

    private LongFilter courseAssignmentId;

    private Boolean distinct;

    public CourseAssignmentOutputCriteria() {}

    public CourseAssignmentOutputCriteria(CourseAssignmentOutputCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.output = other.output == null ? null : other.output.copy();
        this.courseAssignmentId = other.courseAssignmentId == null ? null : other.courseAssignmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseAssignmentOutputCriteria copy() {
        return new CourseAssignmentOutputCriteria(this);
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

    public StringFilter getOutput() {
        return output;
    }

    public StringFilter output() {
        if (output == null) {
            output = new StringFilter();
        }
        return output;
    }

    public void setOutput(StringFilter output) {
        this.output = output;
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
        final CourseAssignmentOutputCriteria that = (CourseAssignmentOutputCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(output, that.output) &&
            Objects.equals(courseAssignmentId, that.courseAssignmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, output, courseAssignmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentOutputCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (output != null ? "output=" + output + ", " : "") +
            (courseAssignmentId != null ? "courseAssignmentId=" + courseAssignmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
