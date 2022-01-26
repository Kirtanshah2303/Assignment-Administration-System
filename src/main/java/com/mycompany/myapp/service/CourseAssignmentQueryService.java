package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.repository.CourseAssignmentRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentMapper;
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
 * Service for executing complex queries for {@link CourseAssignment} entities in the database.
 * The main input is a {@link CourseAssignmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseAssignmentDTO} or a {@link Page} of {@link CourseAssignmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseAssignmentQueryService extends QueryService<CourseAssignment> {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentQueryService.class);

    private final CourseAssignmentRepository courseAssignmentRepository;

    private final CourseAssignmentMapper courseAssignmentMapper;

    public CourseAssignmentQueryService(
        CourseAssignmentRepository courseAssignmentRepository,
        CourseAssignmentMapper courseAssignmentMapper
    ) {
        this.courseAssignmentRepository = courseAssignmentRepository;
        this.courseAssignmentMapper = courseAssignmentMapper;
    }

    /**
     * Return a {@link List} of {@link CourseAssignmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseAssignmentDTO> findByCriteria(CourseAssignmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseAssignment> specification = createSpecification(criteria);
        return courseAssignmentMapper.toDto(courseAssignmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseAssignmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseAssignmentDTO> findByCriteria(CourseAssignmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseAssignment> specification = createSpecification(criteria);
        return courseAssignmentRepository.findAll(specification, page).map(courseAssignmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseAssignmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseAssignment> specification = createSpecification(criteria);
        return courseAssignmentRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseAssignmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseAssignment> createSpecification(CourseAssignmentCriteria criteria) {
        Specification<CourseAssignment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseAssignment_.id));
            }
            if (criteria.getAssignmentTitle() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssignmentTitle(), CourseAssignment_.assignmentTitle));
            }
            if (criteria.getAssignmentDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAssignmentDescription(), CourseAssignment_.assignmentDescription)
                    );
            }
            if (criteria.getSessionVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionVideo(), CourseAssignment_.sessionVideo));
            }
            if (criteria.getSessionDuration() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSessionDuration(), CourseAssignment_.sessionDuration));
            }
            if (criteria.getAssignmentOrder() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAssignmentOrder(), CourseAssignment_.assignmentOrder));
            }
            if (criteria.getAssignmentResource() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssignmentResource(), CourseAssignment_.assignmentResource));
            }
            if (criteria.getIsPreview() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPreview(), CourseAssignment_.isPreview));
            }
            if (criteria.getIsDraft() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDraft(), CourseAssignment_.isDraft));
            }
            if (criteria.getIsApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsApproved(), CourseAssignment_.isApproved));
            }
            if (criteria.getIsPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPublished(), CourseAssignment_.isPublished));
            }
            if (criteria.getCourseSectionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseSectionId(),
                            root -> root.join(CourseAssignment_.courseSection, JoinType.LEFT).get(CourseSection_.id)
                        )
                    );
            }
            if (criteria.getCourseAssignmentInputId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseAssignmentInputId(),
                            root -> root.join(CourseAssignment_.courseAssignmentInputs, JoinType.LEFT).get(CourseAssignmentInput_.id)
                        )
                    );
            }
            if (criteria.getCourseAssignmentOutputId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseAssignmentOutputId(),
                            root -> root.join(CourseAssignment_.courseAssignmentOutputs, JoinType.LEFT).get(CourseAssignmentOutput_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
