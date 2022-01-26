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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseSessionProgress} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseSessionProgressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-session-progresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseSessionProgressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter watchSeconds;

    private LongFilter userId;

    private LongFilter courseSessionId;

    private Boolean distinct;

    public CourseSessionProgressCriteria() {}

    public CourseSessionProgressCriteria(CourseSessionProgressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.watchSeconds = other.watchSeconds == null ? null : other.watchSeconds.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.courseSessionId = other.courseSessionId == null ? null : other.courseSessionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseSessionProgressCriteria copy() {
        return new CourseSessionProgressCriteria(this);
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

    public InstantFilter getWatchSeconds() {
        return watchSeconds;
    }

    public InstantFilter watchSeconds() {
        if (watchSeconds == null) {
            watchSeconds = new InstantFilter();
        }
        return watchSeconds;
    }

    public void setWatchSeconds(InstantFilter watchSeconds) {
        this.watchSeconds = watchSeconds;
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
        final CourseSessionProgressCriteria that = (CourseSessionProgressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(watchSeconds, that.watchSeconds) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(courseSessionId, that.courseSessionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, watchSeconds, userId, courseSessionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSessionProgressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (watchSeconds != null ? "watchSeconds=" + watchSeconds + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (courseSessionId != null ? "courseSessionId=" + courseSessionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
