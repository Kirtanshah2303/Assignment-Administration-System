package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseAssignmentInput.
 */
@Entity
@Table(name = "course_assignment_input")
public class CourseAssignmentInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @Column(name = "input", length = 200)
    private String input;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSession" }, allowSetters = true)
    private CourseAssignment courseAssignment;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseAssignmentInput id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return this.input;
    }

    public CourseAssignmentInput input(String input) {
        this.setInput(input);
        return this;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CourseAssignment getCourseAssignment() {
        return this.courseAssignment;
    }

    public void setCourseAssignment(CourseAssignment courseAssignment) {
        this.courseAssignment = courseAssignment;
    }

    public CourseAssignmentInput courseAssignment(CourseAssignment courseAssignment) {
        this.setCourseAssignment(courseAssignment);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseAssignmentInput user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentInput)) {
            return false;
        }
        return id != null && id.equals(((CourseAssignmentInput) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentInput{" +
            "id=" + getId() +
            ", input='" + getInput() + "'" +
            "}";
    }
}
