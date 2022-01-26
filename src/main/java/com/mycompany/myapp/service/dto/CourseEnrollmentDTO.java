package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseEnrollment} entity.
 */
public class CourseEnrollmentDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private LocalDate enrollementDate;

    @NotNull
    private LocalDate lastAccessedDate;

    private UserDTO user;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEnrollementDate() {
        return enrollementDate;
    }

    public void setEnrollementDate(LocalDate enrollementDate) {
        this.enrollementDate = enrollementDate;
    }

    public LocalDate getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(LocalDate lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseEnrollmentDTO)) {
            return false;
        }

        CourseEnrollmentDTO courseEnrollmentDTO = (CourseEnrollmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseEnrollmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseEnrollmentDTO{" +
            "id=" + getId() +
            ", enrollementDate='" + getEnrollementDate() + "'" +
            ", lastAccessedDate='" + getLastAccessedDate() + "'" +
            ", user=" + getUser() +
            ", course=" + getCourse() +
            "}";
    }
}
