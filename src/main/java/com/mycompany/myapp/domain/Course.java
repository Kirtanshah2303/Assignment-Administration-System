package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "course_title", length = 42, nullable = false)
    private String courseTitle;

    @NotNull
    @Size(min = 10, max = 400)
    @Column(name = "course_description", length = 400, nullable = false)
    private String courseDescription;

    @NotNull
    @Size(min = 10, max = 400)
    @Column(name = "course_objectives", length = 400, nullable = false)
    private String courseObjectives;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "course_sub_title", length = 42, nullable = false)
    private String courseSubTitle;

    @Size(min = 10, max = 42)
    @Column(name = "course_preview_url", length = 42)
    private String coursePreviewURL;

    @Column(name = "course_length")
    private Integer courseLength;

    @NotNull
    @Size(min = 10, max = 42)
    @Column(name = "course_logo", length = 42, nullable = false)
    private String courseLogo;

    @NotNull
    @Column(name = "course_created_on", nullable = false)
    private LocalDate courseCreatedOn;

    @NotNull
    @Column(name = "course_updated_on", nullable = false)
    private LocalDate courseUpdatedOn;

    @Size(min = 10, max = 42)
    @Column(name = "course_root_dir", length = 42)
    private String courseRootDir;

    @Column(name = "amount")
    private Double amount;

    @NotNull
    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft;

    @NotNull
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @NotNull
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @Column(name = "course_approval_date")
    private LocalDate courseApprovalDate;

    @ManyToOne
    private CourseLevel courseLevel;

    @ManyToOne
    private CourseCategory courseCategory;

    @ManyToOne
    private CourseType courseType;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public Course courseTitle(String courseTitle) {
        this.setCourseTitle(courseTitle);
        return this;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return this.courseDescription;
    }

    public Course courseDescription(String courseDescription) {
        this.setCourseDescription(courseDescription);
        return this;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseObjectives() {
        return this.courseObjectives;
    }

    public Course courseObjectives(String courseObjectives) {
        this.setCourseObjectives(courseObjectives);
        return this;
    }

    public void setCourseObjectives(String courseObjectives) {
        this.courseObjectives = courseObjectives;
    }

    public String getCourseSubTitle() {
        return this.courseSubTitle;
    }

    public Course courseSubTitle(String courseSubTitle) {
        this.setCourseSubTitle(courseSubTitle);
        return this;
    }

    public void setCourseSubTitle(String courseSubTitle) {
        this.courseSubTitle = courseSubTitle;
    }

    public String getCoursePreviewURL() {
        return this.coursePreviewURL;
    }

    public Course coursePreviewURL(String coursePreviewURL) {
        this.setCoursePreviewURL(coursePreviewURL);
        return this;
    }

    public void setCoursePreviewURL(String coursePreviewURL) {
        this.coursePreviewURL = coursePreviewURL;
    }

    public Integer getCourseLength() {
        return this.courseLength;
    }

    public Course courseLength(Integer courseLength) {
        this.setCourseLength(courseLength);
        return this;
    }

    public void setCourseLength(Integer courseLength) {
        this.courseLength = courseLength;
    }

    public String getCourseLogo() {
        return this.courseLogo;
    }

    public Course courseLogo(String courseLogo) {
        this.setCourseLogo(courseLogo);
        return this;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    public LocalDate getCourseCreatedOn() {
        return this.courseCreatedOn;
    }

    public Course courseCreatedOn(LocalDate courseCreatedOn) {
        this.setCourseCreatedOn(courseCreatedOn);
        return this;
    }

    public void setCourseCreatedOn(LocalDate courseCreatedOn) {
        this.courseCreatedOn = courseCreatedOn;
    }

    public LocalDate getCourseUpdatedOn() {
        return this.courseUpdatedOn;
    }

    public Course courseUpdatedOn(LocalDate courseUpdatedOn) {
        this.setCourseUpdatedOn(courseUpdatedOn);
        return this;
    }

    public void setCourseUpdatedOn(LocalDate courseUpdatedOn) {
        this.courseUpdatedOn = courseUpdatedOn;
    }

    public String getCourseRootDir() {
        return this.courseRootDir;
    }

    public Course courseRootDir(String courseRootDir) {
        this.setCourseRootDir(courseRootDir);
        return this;
    }

    public void setCourseRootDir(String courseRootDir) {
        this.courseRootDir = courseRootDir;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Course amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public Course isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public Course isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public Course isPublished(Boolean isPublished) {
        this.setIsPublished(isPublished);
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public LocalDate getCourseApprovalDate() {
        return this.courseApprovalDate;
    }

    public Course courseApprovalDate(LocalDate courseApprovalDate) {
        this.setCourseApprovalDate(courseApprovalDate);
        return this;
    }

    public void setCourseApprovalDate(LocalDate courseApprovalDate) {
        this.courseApprovalDate = courseApprovalDate;
    }

    public CourseLevel getCourseLevel() {
        return this.courseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    public Course courseLevel(CourseLevel courseLevel) {
        this.setCourseLevel(courseLevel);
        return this;
    }

    public CourseCategory getCourseCategory() {
        return this.courseCategory;
    }

    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }

    public Course courseCategory(CourseCategory courseCategory) {
        this.setCourseCategory(courseCategory);
        return this;
    }

    public CourseType getCourseType() {
        return this.courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public Course courseType(CourseType courseType) {
        this.setCourseType(courseType);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseTitle='" + getCourseTitle() + "'" +
            ", courseDescription='" + getCourseDescription() + "'" +
            ", courseObjectives='" + getCourseObjectives() + "'" +
            ", courseSubTitle='" + getCourseSubTitle() + "'" +
            ", coursePreviewURL='" + getCoursePreviewURL() + "'" +
            ", courseLength=" + getCourseLength() +
            ", courseLogo='" + getCourseLogo() + "'" +
            ", courseCreatedOn='" + getCourseCreatedOn() + "'" +
            ", courseUpdatedOn='" + getCourseUpdatedOn() + "'" +
            ", courseRootDir='" + getCourseRootDir() + "'" +
            ", amount=" + getAmount() +
            ", isDraft='" + getIsDraft() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isPublished='" + getIsPublished() + "'" +
            ", courseApprovalDate='" + getCourseApprovalDate() + "'" +
            "}";
    }
}
