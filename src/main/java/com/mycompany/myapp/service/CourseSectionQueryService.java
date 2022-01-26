package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.repository.CourseSectionRepository;
import com.mycompany.myapp.service.criteria.CourseSectionCriteria;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
import com.mycompany.myapp.service.mapper.CourseSectionMapper;
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
 * Service for executing complex queries for {@link CourseSection} entities in the database.
 * The main input is a {@link CourseSectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseSectionDTO} or a {@link Page} of {@link CourseSectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseSectionQueryService extends QueryService<CourseSection> {

    private final Logger log = LoggerFactory.getLogger(CourseSectionQueryService.class);

    private final CourseSectionRepository courseSectionRepository;

    private final CourseSectionMapper courseSectionMapper;

    public CourseSectionQueryService(CourseSectionRepository courseSectionRepository, CourseSectionMapper courseSectionMapper) {
        this.courseSectionRepository = courseSectionRepository;
        this.courseSectionMapper = courseSectionMapper;
    }

    /**
     * Return a {@link List} of {@link CourseSectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseSectionDTO> findByCriteria(CourseSectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseSection> specification = createSpecification(criteria);
        return courseSectionMapper.toDto(courseSectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseSectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseSectionDTO> findByCriteria(CourseSectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseSection> specification = createSpecification(criteria);
        return courseSectionRepository.findAll(specification, page).map(courseSectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseSectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseSection> specification = createSpecification(criteria);
        return courseSectionRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseSectionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseSection> createSpecification(CourseSectionCriteria criteria) {
        Specification<CourseSection> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseSection_.id));
            }
            if (criteria.getSectionTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSectionTitle(), CourseSection_.sectionTitle));
            }
            if (criteria.getSectionDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSectionDescription(), CourseSection_.sectionDescription));
            }
            if (criteria.getSectionOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSectionOrder(), CourseSection_.sectionOrder));
            }
            if (criteria.getIsDraft() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDraft(), CourseSection_.isDraft));
            }
            if (criteria.getIsApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsApproved(), CourseSection_.isApproved));
            }
            if (criteria.getCourseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCourseId(), root -> root.join(CourseSection_.course, JoinType.LEFT).get(Course_.id))
                    );
            }
        }
        return specification;
    }
}
