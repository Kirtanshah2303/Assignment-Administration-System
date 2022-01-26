package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseAssignmentInput;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseAssignmentInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseAssignmentInputRepository
    extends JpaRepository<CourseAssignmentInput, Long>, JpaSpecificationExecutor<CourseAssignmentInput> {
    @Query(
        "select courseAssignmentInput from CourseAssignmentInput courseAssignmentInput where courseAssignmentInput.user.login = ?#{principal.username}"
    )
    List<CourseAssignmentInput> findByUserIsCurrentUser();
}
