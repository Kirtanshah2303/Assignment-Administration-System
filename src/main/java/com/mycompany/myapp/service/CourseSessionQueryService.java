package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseSession;
import com.mycompany.myapp.repository.CourseSessionRepository;
import com.mycompany.myapp.service.criteria.CourseSessionCriteria;
import com.mycompany.myapp.service.dto.CourseSessionDTO;
import com.mycompany.myapp.service.mapper.CourseSessionMapper;
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
 * Service for executing complex queries for {@link CourseSession} entities in the database.
 * The main input is a {@link CourseSessionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseSessionDTO} or a {@link Page} of {@link CourseSessionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseSessionQueryService extends QueryService<CourseSession> {

    private final Logger log = LoggerFactory.getLogger(CourseSessionQueryService.class);

    private final CourseSessionRepository courseSessionRepository;

    private final CourseSessionMapper courseSessionMapper;

    public CourseSessionQueryService(CourseSessionRepository courseSessionRepository, CourseSessionMapper courseSessionMapper) {
        this.courseSessionRepository = courseSessionRepository;
        this.courseSessionMapper = courseSessionMapper;
    }

    /**
     * Return a {@link List} of {@link CourseSessionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseSessionDTO> findByCriteria(CourseSessionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseSession> specification = createSpecification(criteria);
        return courseSessionMapper.toDto(courseSessionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseSessionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseSessionDTO> findByCriteria(CourseSessionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseSession> specification = createSpecification(criteria);
        return courseSessionRepository.findAll(specification, page).map(courseSessionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseSessionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseSession> specification = createSpecification(criteria);
        return courseSessionRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseSessionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseSession> createSpecification(CourseSessionCriteria criteria) {
        Specification<CourseSession> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseSession_.id));
            }
            if (criteria.getSessionTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionTitle(), CourseSession_.sessionTitle));
            }
            if (criteria.getSessionDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSessionDescription(), CourseSession_.sessionDescription));
            }
            if (criteria.getSessionVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionVideo(), CourseSession_.sessionVideo));
            }
            if (criteria.getSessionDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSessionDuration(), CourseSession_.sessionDuration));
            }
            if (criteria.getSessionOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSessionOrder(), CourseSession_.sessionOrder));
            }
            if (criteria.getSessionResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionResource(), CourseSession_.sessionResource));
            }
            if (criteria.getSessionQuiz() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionQuiz(), CourseSession_.sessionQuiz));
            }
            if (criteria.getIsPreview() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPreview(), CourseSession_.isPreview));
            }
            if (criteria.getIsDraft() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDraft(), CourseSession_.isDraft));
            }
            if (criteria.getIsApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsApproved(), CourseSession_.isApproved));
            }
            if (criteria.getIsPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPublished(), CourseSession_.isPublished));
            }
            if (criteria.getCourseSectionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseSectionId(),
                            root -> root.join(CourseSession_.courseSection, JoinType.LEFT).get(CourseSection_.id)
                        )
                    );
            }
            if (criteria.getCourseReviewStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseReviewStatusId(),
                            root -> root.join(CourseSession_.courseReviewStatuses, JoinType.LEFT).get(CourseReviewStatus_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
