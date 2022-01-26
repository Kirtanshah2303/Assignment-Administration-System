package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseAssignmentInput;
import com.mycompany.myapp.repository.CourseAssignmentInputRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentInputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentInputMapper;
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
 * Service for executing complex queries for {@link CourseAssignmentInput} entities in the database.
 * The main input is a {@link CourseAssignmentInputCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseAssignmentInputDTO} or a {@link Page} of {@link CourseAssignmentInputDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseAssignmentInputQueryService extends QueryService<CourseAssignmentInput> {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentInputQueryService.class);

    private final CourseAssignmentInputRepository courseAssignmentInputRepository;

    private final CourseAssignmentInputMapper courseAssignmentInputMapper;

    public CourseAssignmentInputQueryService(
        CourseAssignmentInputRepository courseAssignmentInputRepository,
        CourseAssignmentInputMapper courseAssignmentInputMapper
    ) {
        this.courseAssignmentInputRepository = courseAssignmentInputRepository;
        this.courseAssignmentInputMapper = courseAssignmentInputMapper;
    }

    /**
     * Return a {@link List} of {@link CourseAssignmentInputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseAssignmentInputDTO> findByCriteria(CourseAssignmentInputCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseAssignmentInput> specification = createSpecification(criteria);
        return courseAssignmentInputMapper.toDto(courseAssignmentInputRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseAssignmentInputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseAssignmentInputDTO> findByCriteria(CourseAssignmentInputCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseAssignmentInput> specification = createSpecification(criteria);
        return courseAssignmentInputRepository.findAll(specification, page).map(courseAssignmentInputMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseAssignmentInputCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseAssignmentInput> specification = createSpecification(criteria);
        return courseAssignmentInputRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseAssignmentInputCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseAssignmentInput> createSpecification(CourseAssignmentInputCriteria criteria) {
        Specification<CourseAssignmentInput> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseAssignmentInput_.id));
            }
            if (criteria.getInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInput(), CourseAssignmentInput_.input));
            }
            if (criteria.getCourseAssignmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseAssignmentId(),
                            root -> root.join(CourseAssignmentInput_.courseAssignment, JoinType.LEFT).get(CourseAssignment_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(CourseAssignmentInput_.user, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
