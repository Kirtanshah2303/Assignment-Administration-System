package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseAssignmentProgress.
 */
@Entity
@Table(name = "course_assignment_progress")
public class CourseAssignmentProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSession" }, allowSetters = true)
    private CourseAssignment courseAssignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseAssignmentProgress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public CourseAssignmentProgress completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getCompletedDate() {
        return this.completedDate;
    }

    public CourseAssignmentProgress completedDate(LocalDate completedDate) {
        this.setCompletedDate(completedDate);
        return this;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseAssignmentProgress user(User user) {
        this.setUser(user);
        return this;
    }

    public CourseAssignment getCourseAssignment() {
        return this.courseAssignment;
    }

    public void setCourseAssignment(CourseAssignment courseAssignment) {
        this.courseAssignment = courseAssignment;
    }

    public CourseAssignmentProgress courseAssignment(CourseAssignment courseAssignment) {
        this.setCourseAssignment(courseAssignment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseAssignmentProgress)) {
            return false;
        }
        return id != null && id.equals(((CourseAssignmentProgress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseAssignmentProgress{" +
            "id=" + getId() +
            ", completed='" + getCompleted() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            "}";
    }
}
