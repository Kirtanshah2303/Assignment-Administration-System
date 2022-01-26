package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseEnrollment;
import com.mycompany.myapp.repository.CourseEnrollmentRepository;
import com.mycompany.myapp.service.criteria.CourseEnrollmentCriteria;
import com.mycompany.myapp.service.dto.CourseEnrollmentDTO;
import com.mycompany.myapp.service.mapper.CourseEnrollmentMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CourseEnrollment} entities in the database.
 * The main input is a {@link CourseEnrollmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseEnrollmentDTO} or a {@link Page} of {@link CourseEnrollmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseEnrollmentQueryService extends QueryService<CourseEnrollment> {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentQueryService.class);

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CourseEnrollmentMapper courseEnrollmentMapper;

    public CourseEnrollmentQueryService(
        CourseEnrollmentRepository courseEnrollmentRepository,
        CourseEnrollmentMapper courseEnrollmentMapper
    ) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseEnrollmentMapper = courseEnrollmentMapper;
    }

    /**
     * Return a {@link List} of {@link CourseEnrollmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseEnrollmentDTO> findByCriteria(CourseEnrollmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseEnrollment> specification = createSpecification(criteria);
        return courseEnrollmentMapper.toDto(courseEnrollmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseEnrollmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseEnrollmentDTO> findByCriteria(CourseEnrollmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseEnrollment> specification = createSpecification(criteria);
        return courseEnrollmentRepository.findAll(specification, page).map(courseEnrollmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseEnrollmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseEnrollment> specification = createSpecification(criteria);
        return courseEnrollmentRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseEnrollmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseEnrollment> createSpecification(CourseEnrollmentCriteria criteria) {
        Specification<CourseEnrollment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseEnrollment_.id));
            }
            if (criteria.getEnrollementDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getEnrollementDate(), CourseEnrollment_.enrollementDate));
            }
            if (criteria.getLastAccessedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastAccessedDate(), CourseEnrollment_.lastAccessedDate));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(CourseEnrollment_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getCourseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseId(),
                            root -> root.join(CourseEnrollment_.course, JoinType.LEFT).get(Course_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
