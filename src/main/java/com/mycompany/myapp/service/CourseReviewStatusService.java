package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseReviewStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseReviewStatus}.
 */
public interface CourseReviewStatusService {
    /**
     * Save a courseReviewStatus.
     *
     * @param courseReviewStatusDTO the entity to save.
     * @return the persisted entity.
     */
    CourseReviewStatusDTO save(CourseReviewStatusDTO courseReviewStatusDTO);

    /**
     * Partially updates a courseReviewStatus.
     *
     * @param courseReviewStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseReviewStatusDTO> partialUpdate(CourseReviewStatusDTO courseReviewStatusDTO);

    /**
     * Get all the courseReviewStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseReviewStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseReviewStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseReviewStatusDTO> findOne(Long id);

    /**
     * Delete the "id" courseReviewStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
