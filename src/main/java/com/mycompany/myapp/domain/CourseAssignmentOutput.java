package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseAssignmentOutput.
 */
@Entity
@Table(name = "course_assignment_output")
public class CourseAssignmentOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @Column(name = "output", length = 200)
    private String output;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSession" }, allowSetters = true)
    private CourseAssignment courseAssignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseAssignmentOutput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutput() {
        return this.output;
    }

    public CourseAssignmentOutput output(String output) {
        this.setOutput(output);
        return this;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public CourseAssignment getCourseAssignment() {
        return this.courseAssignment;
    }

    public void setCourseAssignment(CourseAssignment courseAssignment) {
        this.courseAssignment = courseAssignment;
    }

    public CourseAssignmentOutput courseAssignment(CourseAssignment courseAssignment) {
        this.setCourseAssignment(courseAssignment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentOutput)) {
            return false;
        }
        return id != null && id.equals(((CourseAssignmentOutput) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentOutput{" +
            "id=" + getId() +
            ", output='" + getOutput() + "'" +
            "}";
    }
}
