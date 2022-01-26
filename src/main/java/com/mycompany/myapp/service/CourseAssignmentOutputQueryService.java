package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseAssignmentOutput;
import com.mycompany.myapp.repository.CourseAssignmentOutputRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentOutputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentOutputMapper;
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
 * Service for executing complex queries for {@link CourseAssignmentOutput} entities in the database.
 * The main input is a {@link CourseAssignmentOutputCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseAssignmentOutputDTO} or a {@link Page} of {@link CourseAssignmentOutputDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseAssignmentOutputQueryService extends QueryService<CourseAssignmentOutput> {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentOutputQueryService.class);

    private final CourseAssignmentOutputRepository courseAssignmentOutputRepository;

    private final CourseAssignmentOutputMapper courseAssignmentOutputMapper;

    public CourseAssignmentOutputQueryService(
        CourseAssignmentOutputRepository courseAssignmentOutputRepository,
        CourseAssignmentOutputMapper courseAssignmentOutputMapper
    ) {
        this.courseAssignmentOutputRepository = courseAssignmentOutputRepository;
        this.courseAssignmentOutputMapper = courseAssignmentOutputMapper;
    }

    /**
     * Return a {@link List} of {@link CourseAssignmentOutputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseAssignmentOutputDTO> findByCriteria(CourseAssignmentOutputCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseAssignmentOutput> specification = createSpecification(criteria);
        return courseAssignmentOutputMapper.toDto(courseAssignmentOutputRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseAssignmentOutputDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseAssignmentOutputDTO> findByCriteria(CourseAssignmentOutputCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseAssignmentOutput> specification = createSpecification(criteria);
        return courseAssignmentOutputRepository.findAll(specification, page).map(courseAssignmentOutputMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseAssignmentOutputCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseAssignmentOutput> specification = createSpecification(criteria);
        return courseAssignmentOutputRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseAssignmentOutputCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseAssignmentOutput> createSpecification(CourseAssignmentOutputCriteria criteria) {
        Specification<CourseAssignmentOutput> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseAssignmentOutput_.id));
            }
            if (criteria.getOutput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutput(), CourseAssignmentOutput_.output));
            }
            if (criteria.getCourseAssignmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseAssignmentId(),
                            root -> root.join(CourseAssignmentOutput_.courseAssignment, JoinType.LEFT).get(CourseAssignment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
