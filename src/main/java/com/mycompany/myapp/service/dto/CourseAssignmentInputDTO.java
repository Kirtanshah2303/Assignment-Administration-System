package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseAssignmentInput} entity.
 */
public class CourseAssignmentInputDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 200)
    private String input;

    private CourseAssignmentDTO courseAssignment;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CourseAssignmentDTO getCourseAssignment() {
        return courseAssignment;
    }

    public void setCourseAssignment(CourseAssignmentDTO courseAssignment) {
        this.courseAssignment = courseAssignment;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentInputDTO)) {
            return false;
        }

        CourseAssignmentInputDTO courseAssignmentInputDTO = (CourseAssignmentInputDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseAssignmentInputDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentInputDTO{" +
            "id=" + getId() +
            ", input='" + getInput() + "'" +
            ", courseAssignment=" + getCourseAssignment() +
            ", user=" + getUser() +
            "}";
    }
}
