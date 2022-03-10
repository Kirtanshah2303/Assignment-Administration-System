package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long>, JpaSpecificationExecutor<CourseSection> {
    List<CourseSection> findCourseSectionByCourse(Optional<Course> course);

    List<CourseSection> findCourseSectionsByCourse(Optional<Course> course);
}
