package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CourseCategory} entity.
 */
public class CourseCategoryDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String logo;

    @NotNull
    private Boolean isParent;

    @NotNull
    private Integer parentId;

    @Size(max = 100)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseCategoryDTO)) {
            return false;
        }

        CourseCategoryDTO courseCategoryDTO = (CourseCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCategoryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", logo='" + getLogo() + "'" +
            ", isParent='" + getIsParent() + "'" +
            ", parentId=" + getParentId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
