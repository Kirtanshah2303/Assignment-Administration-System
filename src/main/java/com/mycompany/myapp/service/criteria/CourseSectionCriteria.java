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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseSection} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseSectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-sections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseSectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter sectionTitle;

    private StringFilter sectionDescription;

    private IntegerFilter sectionOrder;

    private BooleanFilter isDraft;

    private BooleanFilter isApproved;

    private LongFilter courseId;

    private Boolean distinct;

    public CourseSectionCriteria() {}

    public CourseSectionCriteria(CourseSectionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sectionTitle = other.sectionTitle == null ? null : other.sectionTitle.copy();
        this.sectionDescription = other.sectionDescription == null ? null : other.sectionDescription.copy();
        this.sectionOrder = other.sectionOrder == null ? null : other.sectionOrder.copy();
        this.isDraft = other.isDraft == null ? null : other.isDraft.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseSectionCriteria copy() {
        return new CourseSectionCriteria(this);
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

    public StringFilter getSectionTitle() {
        return sectionTitle;
    }

    public StringFilter sectionTitle() {
        if (sectionTitle == null) {
            sectionTitle = new StringFilter();
        }
        return sectionTitle;
    }

    public void setSectionTitle(StringFilter sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public StringFilter getSectionDescription() {
        return sectionDescription;
    }

    public StringFilter sectionDescription() {
        if (sectionDescription == null) {
            sectionDescription = new StringFilter();
        }
        return sectionDescription;
    }

    public void setSectionDescription(StringFilter sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public IntegerFilter getSectionOrder() {
        return sectionOrder;
    }

    public IntegerFilter sectionOrder() {
        if (sectionOrder == null) {
            sectionOrder = new IntegerFilter();
        }
        return sectionOrder;
    }

    public void setSectionOrder(IntegerFilter sectionOrder) {
        this.sectionOrder = sectionOrder;
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
        final CourseSectionCriteria that = (CourseSectionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sectionTitle, that.sectionTitle) &&
            Objects.equals(sectionDescription, that.sectionDescription) &&
            Objects.equals(sectionOrder, that.sectionOrder) &&
            Objects.equals(isDraft, that.isDraft) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectionTitle, sectionDescription, sectionOrder, isDraft, isApproved, courseId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSectionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sectionTitle != null ? "sectionTitle=" + sectionTitle + ", " : "") +
            (sectionDescription != null ? "sectionDescription=" + sectionDescription + ", " : "") +
            (sectionOrder != null ? "sectionOrder=" + sectionOrder + ", " : "") +
            (isDraft != null ? "isDraft=" + isDraft + ", " : "") +
            (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
            (courseId != null ? "courseId=" + courseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
