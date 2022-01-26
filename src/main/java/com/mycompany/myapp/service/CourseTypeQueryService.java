package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseType;
import com.mycompany.myapp.repository.CourseTypeRepository;
import com.mycompany.myapp.service.criteria.CourseTypeCriteria;
import com.mycompany.myapp.service.dto.CourseTypeDTO;
import com.mycompany.myapp.service.mapper.CourseTypeMapper;
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
 * Service for executing complex queries for {@link CourseType} entities in the database.
 * The main input is a {@link CourseTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseTypeDTO} or a {@link Page} of {@link CourseTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseTypeQueryService extends QueryService<CourseType> {

    private final Logger log = LoggerFactory.getLogger(CourseTypeQueryService.class);

    private final CourseTypeRepository courseTypeRepository;

    private final CourseTypeMapper courseTypeMapper;

    public CourseTypeQueryService(CourseTypeRepository courseTypeRepository, CourseTypeMapper courseTypeMapper) {
        this.courseTypeRepository = courseTypeRepository;
        this.courseTypeMapper = courseTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CourseTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseTypeDTO> findByCriteria(CourseTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseType> specification = createSpecification(criteria);
        return courseTypeMapper.toDto(courseTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseTypeDTO> findByCriteria(CourseTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseType> specification = createSpecification(criteria);
        return courseTypeRepository.findAll(specification, page).map(courseTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseType> specification = createSpecification(criteria);
        return courseTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseType> createSpecification(CourseTypeCriteria criteria) {
        Specification<CourseType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseType_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), CourseType_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CourseType_.description));
            }
        }
        return specification;
    }
}
