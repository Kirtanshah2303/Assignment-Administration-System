package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseAssignmentProgress;
import com.mycompany.myapp.repository.CourseAssignmentProgressRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentProgressCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentProgressDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentProgressMapper;
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
 * Service for executing complex queries for {@link CourseAssignmentProgress} entities in the database.
 * The main input is a {@link CourseAssignmentProgressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseAssignmentProgressDTO} or a {@link Page} of {@link CourseAssignmentProgressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseAssignmentProgressQueryService extends QueryService<CourseAssignmentProgress> {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentProgressQueryService.class);

    private final CourseAssignmentProgressRepository courseAssignmentProgressRepository;

    private final CourseAssignmentProgressMapper courseAssignmentProgressMapper;

    public CourseAssignmentProgressQueryService(
        CourseAssignmentProgressRepository courseAssignmentProgressRepository,
        CourseAssignmentProgressMapper courseAssignmentProgressMapper
    ) {
        this.courseAssignmentProgressRepository = courseAssignmentProgressRepository;
        this.courseAssignmentProgressMapper = courseAssignmentProgressMapper;
    }

    /**
     * Return a {@link List} of {@link CourseAssignmentProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseAssignmentProgressDTO> findByCriteria(CourseAssignmentProgressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseAssignmentProgress> specification = createSpecification(criteria);
        return courseAssignmentProgressMapper.toDto(courseAssignmentProgressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseAssignmentProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseAssignmentProgressDTO> findByCriteria(CourseAssignmentProgressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseAssignmentProgress> specification = createSpecification(criteria);
        return courseAssignmentProgressRepository.findAll(specification, page).map(courseAssignmentProgressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseAssignmentProgressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseAssignmentProgress> specification = createSpecification(criteria);
        return courseAssignmentProgressRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseAssignmentProgressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseAssignmentProgress> createSpecification(CourseAssignmentProgressCriteria criteria) {
        Specification<CourseAssignmentProgress> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseAssignmentProgress_.id));
            }
            if (criteria.getCompleted() != null) {
                specification = specification.and(buildSpecification(criteria.getCompleted(), CourseAssignmentProgress_.completed));
            }
            if (criteria.getCompletedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCompletedDate(), CourseAssignmentProgress_.completedDate));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(CourseAssignmentProgress_.user, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getCourseAssignmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseAssignmentId(),
                            root -> root.join(CourseAssignmentProgress_.courseAssignment, JoinType.LEFT).get(CourseAssignment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
