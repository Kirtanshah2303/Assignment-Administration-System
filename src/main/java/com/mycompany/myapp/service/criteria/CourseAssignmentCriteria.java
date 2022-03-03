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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseAssignment} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseAssignmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-assignments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseAssignmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter assignmentTitle;

    private StringFilter assignmentDescription;

    private IntegerFilter assignmentOrder;

    private StringFilter assignmentResource;

    private BooleanFilter isPreview;

    private BooleanFilter isDraft;

    private BooleanFilter isApproved;

    private BooleanFilter isPublished;

    private LongFilter courseSessionId;

    private Boolean distinct;

    public CourseAssignmentCriteria() {}

    public CourseAssignmentCriteria(CourseAssignmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assignmentTitle = other.assignmentTitle == null ? null : other.assignmentTitle.copy();
        this.assignmentDescription = other.assignmentDescription == null ? null : other.assignmentDescription.copy();
        this.assignmentOrder = other.assignmentOrder == null ? null : other.assignmentOrder.copy();
        this.assignmentResource = other.assignmentResource == null ? null : other.assignmentResource.copy();
        this.isPreview = other.isPreview == null ? null : other.isPreview.copy();
        this.isDraft = other.isDraft == null ? null : other.isDraft.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.isPublished = other.isPublished == null ? null : other.isPublished.copy();
        this.courseSessionId = other.courseSessionId == null ? null : other.courseSessionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseAssignmentCriteria copy() {
        return new CourseAssignmentCriteria(this);
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

    public StringFilter getAssignmentTitle() {
        return assignmentTitle;
    }

    public StringFilter assignmentTitle() {
        if (assignmentTitle == null) {
            assignmentTitle = new StringFilter();
        }
        return assignmentTitle;
    }

    public void setAssignmentTitle(StringFilter assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public StringFilter getAssignmentDescription() {
        return assignmentDescription;
    }

    public StringFilter assignmentDescription() {
        if (assignmentDescription == null) {
            assignmentDescription = new StringFilter();
        }
        return assignmentDescription;
    }

    public void setAssignmentDescription(StringFilter assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public IntegerFilter getAssignmentOrder() {
        return assignmentOrder;
    }

    public IntegerFilter assignmentOrder() {
        if (assignmentOrder == null) {
            assignmentOrder = new IntegerFilter();
        }
        return assignmentOrder;
    }

    public void setAssignmentOrder(IntegerFilter assignmentOrder) {
        this.assignmentOrder = assignmentOrder;
    }

    public StringFilter getAssignmentResource() {
        return assignmentResource;
    }

    public StringFilter assignmentResource() {
        if (assignmentResource == null) {
            assignmentResource = new StringFilter();
        }
        return assignmentResource;
    }

    public void setAssignmentResource(StringFilter assignmentResource) {
        this.assignmentResource = assignmentResource;
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
        final CourseAssignmentCriteria that = (CourseAssignmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assignmentTitle, that.assignmentTitle) &&
            Objects.equals(assignmentDescription, that.assignmentDescription) &&
            Objects.equals(assignmentOrder, that.assignmentOrder) &&
            Objects.equals(assignmentResource, that.assignmentResource) &&
            Objects.equals(isPreview, that.isPreview) &&
            Objects.equals(isDraft, that.isDraft) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(isPublished, that.isPublished) &&
            Objects.equals(courseSessionId, that.courseSessionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assignmentTitle,
            assignmentDescription,
            assignmentOrder,
            assignmentResource,
            isPreview,
            isDraft,
            isApproved,
            isPublished,
            courseSessionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assignmentTitle != null ? "assignmentTitle=" + assignmentTitle + ", " : "") +
            (assignmentDescription != null ? "assignmentDescription=" + assignmentDescription + ", " : "") +
            (assignmentOrder != null ? "assignmentOrder=" + assignmentOrder + ", " : "") +
            (assignmentResource != null ? "assignmentResource=" + assignmentResource + ", " : "") +
            (isPreview != null ? "isPreview=" + isPreview + ", " : "") +
            (isDraft != null ? "isDraft=" + isDraft + ", " : "") +
            (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
            (isPublished != null ? "isPublished=" + isPublished + ", " : "") +
            (courseSessionId != null ? "courseSessionId=" + courseSessionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
