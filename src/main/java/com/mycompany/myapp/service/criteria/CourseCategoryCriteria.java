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
 * Criteria class for the {@link com.mycompany.myapp.domain.CourseCategory} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CourseCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter courseCategoryTitle;

    private StringFilter logo;

    private BooleanFilter isParent;

    private IntegerFilter parentId;

    private StringFilter description;

    private Boolean distinct;

    public CourseCategoryCriteria() {}

    public CourseCategoryCriteria(CourseCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.courseCategoryTitle = other.courseCategoryTitle == null ? null : other.courseCategoryTitle.copy();
        this.logo = other.logo == null ? null : other.logo.copy();
        this.isParent = other.isParent == null ? null : other.isParent.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourseCategoryCriteria copy() {
        return new CourseCategoryCriteria(this);
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

    public StringFilter getCourseCategoryTitle() {
        return courseCategoryTitle;
    }

    public StringFilter courseCategoryTitle() {
        if (courseCategoryTitle == null) {
            courseCategoryTitle = new StringFilter();
        }
        return courseCategoryTitle;
    }

    public void setCourseCategoryTitle(StringFilter courseCategoryTitle) {
        this.courseCategoryTitle = courseCategoryTitle;
    }

    public StringFilter getLogo() {
        return logo;
    }

    public StringFilter logo() {
        if (logo == null) {
            logo = new StringFilter();
        }
        return logo;
    }

    public void setLogo(StringFilter logo) {
        this.logo = logo;
    }

    public BooleanFilter getIsParent() {
        return isParent;
    }

    public BooleanFilter isParent() {
        if (isParent == null) {
            isParent = new BooleanFilter();
        }
        return isParent;
    }

    public void setIsParent(BooleanFilter isParent) {
        this.isParent = isParent;
    }

    public IntegerFilter getParentId() {
        return parentId;
    }

    public IntegerFilter parentId() {
        if (parentId == null) {
            parentId = new IntegerFilter();
        }
        return parentId;
    }

    public void setParentId(IntegerFilter parentId) {
        this.parentId = parentId;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final CourseCategoryCriteria that = (CourseCategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(courseCategoryTitle, that.courseCategoryTitle) &&
            Objects.equals(logo, that.logo) &&
            Objects.equals(isParent, that.isParent) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(description, that.description) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseCategoryTitle, logo, isParent, parentId, description, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCategoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (courseCategoryTitle != null ? "courseCategoryTitle=" + courseCategoryTitle + ", " : "") +
            (logo != null ? "logo=" + logo + ", " : "") +
            (isParent != null ? "isParent=" + isParent + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
