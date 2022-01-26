package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseAssignmentOutput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseAssignmentOutput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseAssignmentOutputRepository
    extends JpaRepository<CourseAssignmentOutput, Long>, JpaSpecificationExecutor<CourseAssignmentOutput> {}
