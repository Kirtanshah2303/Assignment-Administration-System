package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("select course from Course course where course.user.login = ?#{principal.username}")
    List<Course> findByUserIsCurrentUser();

    @Query("select course from Course course where course.semester in (?1, ?2)")
    List<Course> findAllBySemester(int a, int b);

    List<Course> findCourseByEnrolledUsersListsContaining(User user);

    @Query(
        value = "select course from Course course where course.courseCategory.id = :id and course.isApproved = true order by course.courseTitle"
    )
    List<Course> findByCategoryId(@Param("id") Long id);

    @Query(value = "select count(*) from rel_course__enrolled_users_list", nativeQuery = true)
    Integer findTotalEnrollment();
}
