package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("select course from Course course where course.user.login = ?#{principal.username}")
    List<Course> findByUserIsCurrentUser();

    List<Course> findCourseByUserEquals(User author);
    List<Course> findAllByIsApproved(Boolean value);
    //    @Query("select course from Course course where course.semester in (?1, ?2)")
    //    List<Course> findAllBySemester(int a, int b);
}
