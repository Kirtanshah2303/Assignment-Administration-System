package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseAssignmentProgress} entity.
 */
public class CourseAssignmentProgressDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Boolean completed;

    private LocalDate completedDate;

    private UserDTO user;

    private CourseAssignmentDTO courseAssignment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseAssignmentDTO getCourseAssignment() {
        return courseAssignment;
    }

    public void setCourseAssignment(CourseAssignmentDTO courseAssignment) {
        this.courseAssignment = courseAssignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentProgressDTO)) {
            return false;
        }

        CourseAssignmentProgressDTO courseAssignmentProgressDTO = (CourseAssignmentProgressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseAssignmentProgressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentProgressDTO{" +
            "id=" + getId() +
            ", completed='" + getCompleted() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            ", user=" + getUser() +
            ", courseAssignment=" + getCourseAssignment() +
            "}";
    }
}
