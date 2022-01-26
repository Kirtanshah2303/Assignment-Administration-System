package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourseSessionProgress;
import com.mycompany.myapp.repository.CourseSessionProgressRepository;
import com.mycompany.myapp.service.criteria.CourseSessionProgressCriteria;
import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
import com.mycompany.myapp.service.mapper.CourseSessionProgressMapper;
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
 * Service for executing complex queries for {@link CourseSessionProgress} entities in the database.
 * The main input is a {@link CourseSessionProgressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseSessionProgressDTO} or a {@link Page} of {@link CourseSessionProgressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseSessionProgressQueryService extends QueryService<CourseSessionProgress> {

    private final Logger log = LoggerFactory.getLogger(CourseSessionProgressQueryService.class);

    private final CourseSessionProgressRepository courseSessionProgressRepository;

    private final CourseSessionProgressMapper courseSessionProgressMapper;

    public CourseSessionProgressQueryService(
        CourseSessionProgressRepository courseSessionProgressRepository,
        CourseSessionProgressMapper courseSessionProgressMapper
    ) {
        this.courseSessionProgressRepository = courseSessionProgressRepository;
        this.courseSessionProgressMapper = courseSessionProgressMapper;
    }

    /**
     * Return a {@link List} of {@link CourseSessionProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseSessionProgressDTO> findByCriteria(CourseSessionProgressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseSessionProgress> specification = createSpecification(criteria);
        return courseSessionProgressMapper.toDto(courseSessionProgressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseSessionProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseSessionProgressDTO> findByCriteria(CourseSessionProgressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseSessionProgress> specification = createSpecification(criteria);
        return courseSessionProgressRepository.findAll(specification, page).map(courseSessionProgressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseSessionProgressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseSessionProgress> specification = createSpecification(criteria);
        return courseSessionProgressRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseSessionProgressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseSessionProgress> createSpecification(CourseSessionProgressCriteria criteria) {
        Specification<CourseSessionProgress> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseSessionProgress_.id));
            }
            if (criteria.getWatchSeconds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWatchSeconds(), CourseSessionProgress_.watchSeconds));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(CourseSessionProgress_.user, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getCourseSessionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseSessionId(),
                            root -> root.join(CourseSessionProgress_.courseSession, JoinType.LEFT).get(CourseSession_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
