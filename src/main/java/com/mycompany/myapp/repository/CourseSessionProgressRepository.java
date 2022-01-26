package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseSessionProgress;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseSessionProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSessionProgressRepository
    extends JpaRepository<CourseSessionProgress, Long>, JpaSpecificationExecutor<CourseSessionProgress> {
    @Query(
        "select courseSessionProgress from CourseSessionProgress courseSessionProgress where courseSessionProgress.user.login = ?#{principal.username}"
    )
    List<CourseSessionProgress> findByUserIsCurrentUser();
}
