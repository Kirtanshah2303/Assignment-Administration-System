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
 * Criteria class for the {@link com.mycompany.myapp.domain.Course} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /courses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter courseTitle;

    private StringFilter courseDescription;

    private StringFilter courseObjectives;

    private StringFilter courseSubTitle;

    private StringFilter coursePreviewURL;

    private IntegerFilter courseLength;

    private StringFilter courseLogo;

    private LocalDateFilter courseCreatedOn;

    private LocalDateFilter courseUpdatedOn;

    private StringFilter courseRootDir;

    private DoubleFilter amount;

    private BooleanFilter isDraft;

    private BooleanFilter isApproved;

    private BooleanFilter isPublished;

    private LocalDateFilter courseApprovalDate;

    private LongFilter courseLevelId;

    private LongFilter courseCategoryId;

    private LongFilter courseTypeId;

    private LongFilter userId;

    private LongFilter reviewerId;

    private Boolean distinct;

    public CourseCriteria() {}

    public CourseCriteria(CourseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.courseTitle = other.courseTitle == null ? null : other.courseTitle.copy();
        this.courseDescription = other.courseDescription == null ? null : other.courseDescription.copy();
        this.courseObjectives = other.courseObjectives == null ? null : other.courseObjectives.copy();
        this.courseSubTitle = other.courseSubTitle == null ? null : other.courseSubTitle.copy();
        this.coursePreviewURL = other.coursePreviewURL == null ? null : other.coursePreviewURL.copy();
        this.courseLength = other.courseLength == null ? null : other.courseLength.copy();
        this.courseLogo = other.courseLogo == null ? null : other.courseLogo.copy();
        this.courseCreatedOn = other.courseCreatedOn == null ? null : other.courseCreatedOn.copy();
        this.courseUpdatedOn = other.courseUpdatedOn == null ? null : other.courseUpdatedOn.copy();
        this.courseRootDir = other.courseRootDir == null ? null : other.courseRootDir.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.isDraft = other.isDraft == null ? null : other.isDraft.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.isPublished = other.isPublished == null ? null : other.isPublished.copy();
        this.courseApprovalDate = other.courseApprovalDate == null ? null : other.courseApprovalDate.copy();
        this.courseLevelId = other.courseLevelId == null ? null : other.courseLevelId.copy();
        this.courseCategoryId = other.courseCategoryId == null ? null : other.courseCategoryId.copy();
        this.courseTypeId = other.courseTypeId == null ? null : other.courseTypeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.reviewerId = other.reviewerId == null ? null : other.reviewerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseCriteria copy() {
        return new CourseCriteria(this);
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

    public StringFilter getCourseTitle() {
        return courseTitle;
    }

    public StringFilter courseTitle() {
        if (courseTitle == null) {
            courseTitle = new StringFilter();
        }
        return courseTitle;
    }

    public void setCourseTitle(StringFilter courseTitle) {
        this.courseTitle = courseTitle;
    }

    public StringFilter getCourseDescription() {
        return courseDescription;
    }

    public StringFilter courseDescription() {
        if (courseDescription == null) {
            courseDescription = new StringFilter();
        }
        return courseDescription;
    }

    public void setCourseDescription(StringFilter courseDescription) {
        this.courseDescription = courseDescription;
    }

    public StringFilter getCourseObjectives() {
        return courseObjectives;
    }

    public StringFilter courseObjectives() {
        if (courseObjectives == null) {
            courseObjectives = new StringFilter();
        }
        return courseObjectives;
    }

    public void setCourseObjectives(StringFilter courseObjectives) {
        this.courseObjectives = courseObjectives;
    }

    public StringFilter getCourseSubTitle() {
        return courseSubTitle;
    }

    public StringFilter courseSubTitle() {
        if (courseSubTitle == null) {
            courseSubTitle = new StringFilter();
        }
        return courseSubTitle;
    }

    public void setCourseSubTitle(StringFilter courseSubTitle) {
        this.courseSubTitle = courseSubTitle;
    }

    public StringFilter getCoursePreviewURL() {
        return coursePreviewURL;
    }

    public StringFilter coursePreviewURL() {
        if (coursePreviewURL == null) {
            coursePreviewURL = new StringFilter();
        }
        return coursePreviewURL;
    }

    public void setCoursePreviewURL(StringFilter coursePreviewURL) {
        this.coursePreviewURL = coursePreviewURL;
    }

    public IntegerFilter getCourseLength() {
        return courseLength;
    }

    public IntegerFilter courseLength() {
        if (courseLength == null) {
            courseLength = new IntegerFilter();
        }
        return courseLength;
    }

    public void setCourseLength(IntegerFilter courseLength) {
        this.courseLength = courseLength;
    }

    public StringFilter getCourseLogo() {
        return courseLogo;
    }

    public StringFilter courseLogo() {
        if (courseLogo == null) {
            courseLogo = new StringFilter();
        }
        return courseLogo;
    }

    public void setCourseLogo(StringFilter courseLogo) {
        this.courseLogo = courseLogo;
    }

    public LocalDateFilter getCourseCreatedOn() {
        return courseCreatedOn;
    }

    public LocalDateFilter courseCreatedOn() {
        if (courseCreatedOn == null) {
            courseCreatedOn = new LocalDateFilter();
        }
        return courseCreatedOn;
    }

    public void setCourseCreatedOn(LocalDateFilter courseCreatedOn) {
        this.courseCreatedOn = courseCreatedOn;
    }

    public LocalDateFilter getCourseUpdatedOn() {
        return courseUpdatedOn;
    }

    public LocalDateFilter courseUpdatedOn() {
        if (courseUpdatedOn == null) {
            courseUpdatedOn = new LocalDateFilter();
        }
        return courseUpdatedOn;
    }

    public void setCourseUpdatedOn(LocalDateFilter courseUpdatedOn) {
        this.courseUpdatedOn = courseUpdatedOn;
    }

    public StringFilter getCourseRootDir() {
        return courseRootDir;
    }

    public StringFilter courseRootDir() {
        if (courseRootDir == null) {
            courseRootDir = new StringFilter();
        }
        return courseRootDir;
    }

    public void setCourseRootDir(StringFilter courseRootDir) {
        this.courseRootDir = courseRootDir;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
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

    public LocalDateFilter getCourseApprovalDate() {
        return courseApprovalDate;
    }

    public LocalDateFilter courseApprovalDate() {
        if (courseApprovalDate == null) {
            courseApprovalDate = new LocalDateFilter();
        }
        return courseApprovalDate;
    }

    public void setCourseApprovalDate(LocalDateFilter courseApprovalDate) {
        this.courseApprovalDate = courseApprovalDate;
    }

    public LongFilter getCourseLevelId() {
        return courseLevelId;
    }

    public LongFilter courseLevelId() {
        if (courseLevelId == null) {
            courseLevelId = new LongFilter();
        }
        return courseLevelId;
    }

    public void setCourseLevelId(LongFilter courseLevelId) {
        this.courseLevelId = courseLevelId;
    }

    public LongFilter getCourseCategoryId() {
        return courseCategoryId;
    }

    public LongFilter courseCategoryId() {
        if (courseCategoryId == null) {
            courseCategoryId = new LongFilter();
        }
        return courseCategoryId;
    }

    public void setCourseCategoryId(LongFilter courseCategoryId) {
        this.courseCategoryId = courseCategoryId;
    }

    public LongFilter getCourseTypeId() {
        return courseTypeId;
    }

    public LongFilter courseTypeId() {
        if (courseTypeId == null) {
            courseTypeId = new LongFilter();
        }
        return courseTypeId;
    }

    public void setCourseTypeId(LongFilter courseTypeId) {
        this.courseTypeId = courseTypeId;
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

    public LongFilter getReviewerId() {
        return reviewerId;
    }

    public LongFilter reviewerId() {
        if (reviewerId == null) {
            reviewerId = new LongFilter();
        }
        return reviewerId;
    }

    public void setReviewerId(LongFilter reviewerId) {
        this.reviewerId = reviewerId;
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
        final CourseCriteria that = (CourseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(courseTitle, that.courseTitle) &&
            Objects.equals(courseDescription, that.courseDescription) &&
            Objects.equals(courseObjectives, that.courseObjectives) &&
            Objects.equals(courseSubTitle, that.courseSubTitle) &&
            Objects.equals(coursePreviewURL, that.coursePreviewURL) &&
            Objects.equals(courseLength, that.courseLength) &&
            Objects.equals(courseLogo, that.courseLogo) &&
            Objects.equals(courseCreatedOn, that.courseCreatedOn) &&
            Objects.equals(courseUpdatedOn, that.courseUpdatedOn) &&
            Objects.equals(courseRootDir, that.courseRootDir) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isDraft, that.isDraft) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(isPublished, that.isPublished) &&
            Objects.equals(courseApprovalDate, that.courseApprovalDate) &&
            Objects.equals(courseLevelId, that.courseLevelId) &&
            Objects.equals(courseCategoryId, that.courseCategoryId) &&
            Objects.equals(courseTypeId, that.courseTypeId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(reviewerId, that.reviewerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            courseTitle,
            courseDescription,
            courseObjectives,
            courseSubTitle,
            coursePreviewURL,
            courseLength,
            courseLogo,
            courseCreatedOn,
            courseUpdatedOn,
            courseRootDir,
            amount,
            isDraft,
            isApproved,
            isPublished,
            courseApprovalDate,
            courseLevelId,
            courseCategoryId,
            courseTypeId,
            userId,
            reviewerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (courseTitle != null ? "courseTitle=" + courseTitle + ", " : "") +
            (courseDescription != null ? "courseDescription=" + courseDescription + ", " : "") +
            (courseObjectives != null ? "courseObjectives=" + courseObjectives + ", " : "") +
            (courseSubTitle != null ? "courseSubTitle=" + courseSubTitle + ", " : "") +
            (coursePreviewURL != null ? "coursePreviewURL=" + coursePreviewURL + ", " : "") +
            (courseLength != null ? "courseLength=" + courseLength + ", " : "") +
            (courseLogo != null ? "courseLogo=" + courseLogo + ", " : "") +
            (courseCreatedOn != null ? "courseCreatedOn=" + courseCreatedOn + ", " : "") +
            (courseUpdatedOn != null ? "courseUpdatedOn=" + courseUpdatedOn + ", " : "") +
            (courseRootDir != null ? "courseRootDir=" + courseRootDir + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (isDraft != null ? "isDraft=" + isDraft + ", " : "") +
            (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
            (isPublished != null ? "isPublished=" + isPublished + ", " : "") +
            (courseApprovalDate != null ? "courseApprovalDate=" + courseApprovalDate + ", " : "") +
            (courseLevelId != null ? "courseLevelId=" + courseLevelId + ", " : "") +
            (courseCategoryId != null ? "courseCategoryId=" + courseCategoryId + ", " : "") +
            (courseTypeId != null ? "courseTypeId=" + courseTypeId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (reviewerId != null ? "reviewerId=" + reviewerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
