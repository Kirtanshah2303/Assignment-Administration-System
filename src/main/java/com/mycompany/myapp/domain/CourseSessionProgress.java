package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseSessionProgress.
 */
@Entity
@Table(name = "course_session_progress")
public class CourseSessionProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "watch_seconds", nullable = false)
    private Instant watchSeconds;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courseSection", "courseReviewStatuses" }, allowSetters = true)
    private CourseSession courseSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseSessionProgress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getWatchSeconds() {
        return this.watchSeconds;
    }

    public CourseSessionProgress watchSeconds(Instant watchSeconds) {
        this.setWatchSeconds(watchSeconds);
        return this;
    }

    public void setWatchSeconds(Instant watchSeconds) {
        this.watchSeconds = watchSeconds;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseSessionProgress user(User user) {
        this.setUser(user);
        return this;
    }

    public CourseSession getCourseSession() {
        return this.courseSession;
    }

    public void setCourseSession(CourseSession courseSession) {
        this.courseSession = courseSession;
    }

    public CourseSessionProgress courseSession(CourseSession courseSession) {
        this.setCourseSession(courseSession);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSessionProgress)) {
            return false;
        }
        return id != null && id.equals(((CourseSessionProgress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSessionProgress{" +
            "id=" + getId() +
            ", watchSeconds='" + getWatchSeconds() + "'" +
            "}";
    }
}
