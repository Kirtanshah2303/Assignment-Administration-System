package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseAssignmentProgress;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseAssignmentProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseAssignmentProgressRepository
    extends JpaRepository<CourseAssignmentProgress, Long>, JpaSpecificationExecutor<CourseAssignmentProgress> {
    @Query(
        "select courseAssignmentProgress from CourseAssignmentProgress courseAssignmentProgress where courseAssignmentProgress.user.login = ?#{principal.username}"
    )
    List<CourseAssignmentProgress> findByUserIsCurrentUser();
}
