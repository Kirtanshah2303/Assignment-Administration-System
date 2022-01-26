package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseSession;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long>, JpaSpecificationExecutor<CourseSession> {}
