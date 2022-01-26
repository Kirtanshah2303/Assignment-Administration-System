package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseAssignmentOutput} entity.
 */
public class CourseAssignmentOutputDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 200)
    private String output;

    private CourseAssignmentDTO courseAssignment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
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
        if (!(o instanceof CourseAssignmentOutputDTO)) {
            return false;
        }

        CourseAssignmentOutputDTO courseAssignmentOutputDTO = (CourseAssignmentOutputDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseAssignmentOutputDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentOutputDTO{" +
            "id=" + getId() +
            ", output='" + getOutput() + "'" +
            ", courseAssignment=" + getCourseAssignment() +
            "}";
    }
}
