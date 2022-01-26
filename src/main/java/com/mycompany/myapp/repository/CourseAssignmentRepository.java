package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long>, JpaSpecificationExecutor<CourseAssignment> {}
