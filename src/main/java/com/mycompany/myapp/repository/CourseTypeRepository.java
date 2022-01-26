package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CourseType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType, Long>, JpaSpecificationExecutor<CourseType> {}
