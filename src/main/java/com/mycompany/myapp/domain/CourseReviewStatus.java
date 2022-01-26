package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseReviewStatus.
 */
@Entity
@Table(name = "course_review_status")
public class CourseReviewStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "status_updated_on")
    private LocalDate statusUpdatedOn;

    @Size(max = 200)
    @Column(name = "feedback", length = 200)
    private String feedback;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSection", "courseReviewStatuses" }, allowSetters = true)
    private CourseSession courseSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseReviewStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public CourseReviewStatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStatusUpdatedOn() {
        return this.statusUpdatedOn;
    }

    public CourseReviewStatus statusUpdatedOn(LocalDate statusUpdatedOn) {
        this.setStatusUpdatedOn(statusUpdatedOn);
        return this;
    }

    public void setStatusUpdatedOn(LocalDate statusUpdatedOn) {
        this.statusUpdatedOn = statusUpdatedOn;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public CourseReviewStatus feedback(String feedback) {
        this.setFeedback(feedback);
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseReviewStatus user(User user) {
        this.setUser(user);
        return this;
    }

    public CourseSession getCourseSession() {
        return this.courseSession;
    }

    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }

    public CourseReviewStatus courseSession(CourseSession courseSession) {
        this.setCourseSession(courseSession);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseReviewStatus)) {
            return false;
        }
        return id != null && id.equals(((CourseReviewStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseReviewStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", statusUpdatedOn='" + getStatusUpdatedOn() + "'" +
            ", feedback='" + getFeedback() + "'" +
            "}";
    }
}
