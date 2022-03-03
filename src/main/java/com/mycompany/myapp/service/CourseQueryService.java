package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.service.criteria.CourseCriteria;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
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
 * Service for executing complex queries for {@link Course} entities in the database.
 * The main input is a {@link CourseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseDTO} or a {@link Page} of {@link CourseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseQueryService extends QueryService<Course> {

    private final Logger log = LoggerFactory.getLogger(CourseQueryService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseQueryService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Return a {@link List} of {@link CourseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseDTO> findByCriteria(CourseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Course> specification = createSpecification(criteria);
        return courseMapper.toDto(courseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseDTO> findByCriteria(CourseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Course> specification = createSpecification(criteria);
        return courseRepository.findAll(specification, page).map(courseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Course> specification = createSpecification(criteria);
        return courseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Course> createSpecification(CourseCriteria criteria) {
        Specification<Course> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Course_.id));
            }
            if (criteria.getCourseTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseTitle(), Course_.courseTitle));
            }
            if (criteria.getCourseDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseDescription(), Course_.courseDescription));
            }
            if (criteria.getCourseObjectives() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseObjectives(), Course_.courseObjectives));
            }
            if (criteria.getCourseSubTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseSubTitle(), Course_.courseSubTitle));
            }
            if (criteria.getCoursePreviewURL() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoursePreviewURL(), Course_.coursePreviewURL));
            }
            if (criteria.getCourseLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCourseLength(), Course_.courseLength));
            }
            if (criteria.getCourseLogo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseLogo(), Course_.courseLogo));
            }
            if (criteria.getCourseCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCourseCreatedOn(), Course_.courseCreatedOn));
            }
            if (criteria.getCourseUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCourseUpdatedOn(), Course_.courseUpdatedOn));
            }
            if (criteria.getCourseRootDir() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseRootDir(), Course_.courseRootDir));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Course_.amount));
            }
            if (criteria.getIsDraft() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDraft(), Course_.isDraft));
            }
            if (criteria.getIsApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsApproved(), Course_.isApproved));
            }
            if (criteria.getIsPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPublished(), Course_.isPublished));
            }
            if (criteria.getCourseApprovalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCourseApprovalDate(), Course_.courseApprovalDate));
            }
            if (criteria.getCourseLevelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseLevelId(),
                            root -> root.join(Course_.courseLevel, JoinType.LEFT).get(CourseLevel_.id)
                        )
                    );
            }
            if (criteria.getCourseCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseCategoryId(),
                            root -> root.join(Course_.courseCategory, JoinType.LEFT).get(CourseCategory_.id)
                        )
                    );
            }
            if (criteria.getCourseTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseTypeId(),
                            root -> root.join(Course_.courseType, JoinType.LEFT).get(CourseType_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Course_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
