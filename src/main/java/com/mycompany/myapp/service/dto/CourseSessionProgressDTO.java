package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseSessionProgress} entity.
 */
public class CourseSessionProgressDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long watchSeconds;

    private UserDTO user;

    private CourseSessionDTO courseSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWatchSeconds() {
        return watchSeconds;
    }

    public void setWatchSeconds(Long watchSeconds) {
        this.watchSeconds = watchSeconds;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseSessionDTO getCourseSession() {
        return courseSession;
    }

    public void setCourseSession(CourseSessionDTO courseSession) {
        this.courseSession = courseSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseSessionProgressDTO)) {
            return false;
        }

        CourseSessionProgressDTO courseSessionProgressDTO = (CourseSessionProgressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseSessionProgressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseSessionProgressDTO{" +
            "id=" + getId() +
            ", watchSeconds=" + getWatchSeconds() +
            ", user=" + getUser() +
            ", courseSession=" + getCourseSession() +
            "}";
    }
}
