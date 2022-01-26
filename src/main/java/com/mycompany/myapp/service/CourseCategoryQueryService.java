package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.repository.CourseCategoryRepository;
import com.mycompany.myapp.service.criteria.CourseCategoryCriteria;
import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import com.mycompany.myapp.service.mapper.CourseCategoryMapper;
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
 * Service for executing complex queries for {@link CourseCategory} entities in the database.
 * The main input is a {@link CourseCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseCategoryDTO} or a {@link Page} of {@link CourseCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseCategoryQueryService extends QueryService<CourseCategory> {

    private final Logger log = LoggerFactory.getLogger(CourseCategoryQueryService.class);

    private final CourseCategoryRepository courseCategoryRepository;

    private final CourseCategoryMapper courseCategoryMapper;

    public CourseCategoryQueryService(CourseCategoryRepository courseCategoryRepository, CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CourseCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseCategoryDTO> findByCriteria(CourseCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseCategory> specification = createSpecification(criteria);
        return courseCategoryMapper.toDto(courseCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseCategoryDTO> findByCriteria(CourseCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseCategory> specification = createSpecification(criteria);
        return courseCategoryRepository.findAll(specification, page).map(courseCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseCategory> specification = createSpecification(criteria);
        return courseCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseCategory> createSpecification(CourseCategoryCriteria criteria) {
        Specification<CourseCategory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseCategory_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), CourseCategory_.title));
            }
            if (criteria.getLogo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogo(), CourseCategory_.logo));
            }
            if (criteria.getIsParent() != null) {
                specification = specification.and(buildSpecification(criteria.getIsParent(), CourseCategory_.isParent));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentId(), CourseCategory_.parentId));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CourseCategory_.description));
            }
        }
        return specification;
    }
}
