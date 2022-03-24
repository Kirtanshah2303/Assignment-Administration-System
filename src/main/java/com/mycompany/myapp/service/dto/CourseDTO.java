package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseLevel;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Course} entity.
 */
public class CourseDTO implements Serializable {

    private CourseMapper courseMapper;

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    private String courseTitle;

    @NotNull
    @Size(min = 10, max = 400)
    private String courseDescription;

    @NotNull
    @Size(min = 10, max = 400)
    private String courseObjectives;

    @NotNull
    @Size(min = 10, max = 42)
    private String courseSubTitle;

    @Size(min = 10, max = 42)
    private String coursePreviewURL;

    private Integer courseLength;

    @NotNull
    @Size(min = 10, max = 42)
    private String courseLogo;

    @NotNull
    private LocalDate courseCreatedOn;

    @NotNull
    private LocalDate courseUpdatedOn;

    @Size(min = 10, max = 42)
    private String courseRootDir;

    private Double amount;

    @NotNull
    private Boolean isDraft;

    @NotNull
    private Boolean isApproved;

    @NotNull
    private Boolean isPublished;

    private LocalDate courseApprovalDate;

    private CourseLevelDTO courseLevel;

    private CourseCategoryDTO courseCategory;

    private CourseTypeDTO courseType;

    private UserDTO user;

    Boolean enrolled;

    Integer minStudents;

    Integer maxStudents;

    public CourseDTO() {}

    public CourseDTO(CourseDTO course) {
        this.id = course.getId();
        this.courseTitle = course.getCourseTitle();
        this.courseDescription = course.getCourseDescription();
        this.courseObjectives = course.getCourseObjectives();
        this.courseSubTitle = course.getCourseSubTitle();
        this.coursePreviewURL = course.getCoursePreviewURL();
        this.courseLength = course.getCourseLength();
        this.courseLogo = course.getCourseLogo();
        this.courseCreatedOn = course.getCourseCreatedOn();
        this.courseUpdatedOn = course.getCourseUpdatedOn();
        this.courseRootDir = course.getCourseRootDir();
        this.amount = course.getAmount();
        this.isDraft = course.getIsDraft();
        this.isApproved = course.getIsApproved();
        this.isPublished = course.getIsPublished();
        this.courseApprovalDate = course.getCourseApprovalDate();
        this.courseLevel = course.getCourseLevel();
        this.courseCategory = course.getCourseCategory();
        this.courseType = course.getCourseType();
        this.user = course.getUser();
        this.minStudents = course.getMinStudents();
        this.maxStudents = course.getMaxStudents();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseObjectives() {
        return courseObjectives;
    }

    public void setCourseObjectives(String courseObjectives) {
        this.courseObjectives = courseObjectives;
    }

    public String getCourseSubTitle() {
        return courseSubTitle;
    }

    public void setCourseSubTitle(String courseSubTitle) {
        this.courseSubTitle = courseSubTitle;
    }

    public String getCoursePreviewURL() {
        return coursePreviewURL;
    }

    public void setCoursePreviewURL(String coursePreviewURL) {
        this.coursePreviewURL = coursePreviewURL;
    }

    public Integer getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(Integer courseLength) {
        this.courseLength = courseLength;
    }

    public String getCourseLogo() {
        return courseLogo;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    public LocalDate getCourseCreatedOn() {
        return courseCreatedOn;
    }

    public void setCourseCreatedOn(LocalDate courseCreatedOn) {
        this.courseCreatedOn = courseCreatedOn;
    }

    public LocalDate getCourseUpdatedOn() {
        return courseUpdatedOn;
    }

    public void setCourseUpdatedOn(LocalDate courseUpdatedOn) {
        this.courseUpdatedOn = courseUpdatedOn;
    }

    public String getCourseRootDir() {
        return courseRootDir;
    }

    public void setCourseRootDir(String courseRootDir) {
        this.courseRootDir = courseRootDir;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public LocalDate getCourseApprovalDate() {
        return courseApprovalDate;
    }

    public void setCourseApprovalDate(LocalDate courseApprovalDate) {
        this.courseApprovalDate = courseApprovalDate;
    }

    public CourseLevelDTO getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevelDTO courseLevel) {
        this.courseLevel = courseLevel;
    }

    public CourseCategoryDTO getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategoryDTO courseCategory) {
        this.courseCategory = courseCategory;
    }

    public CourseTypeDTO getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseTypeDTO courseType) {
        this.courseType = courseType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getMinStudents() {
        return minStudents;
    }

    public void setMinStudents(Integer minStudents) {
        this.minStudents = minStudents;
    }

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
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
            ", courseLevel=" + getCourseLevel() +
            ", courseCategory=" + getCourseCategory() +
            ", courseType=" + getCourseType() +
            ", user=" + getUser() +
            "}";
    }
}
