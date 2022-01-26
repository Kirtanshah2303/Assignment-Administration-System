package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseAssignmentProgressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseAssignmentProgress}.
 */
public interface CourseAssignmentProgressService {
    /**
     * Save a courseAssignmentProgress.
     *
     * @param courseAssignmentProgressDTO the entity to save.
     * @return the persisted entity.
     */
    CourseAssignmentProgressDTO save(CourseAssignmentProgressDTO courseAssignmentProgressDTO);

    /**
     * Partially updates a courseAssignmentProgress.
     *
     * @param courseAssignmentProgressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseAssignmentProgressDTO> partialUpdate(CourseAssignmentProgressDTO courseAssignmentProgressDTO);

    /**
     * Get all the courseAssignmentProgresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseAssignmentProgressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseAssignmentProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseAssignmentProgressDTO> findOne(Long id);

    /**
     * Delete the "id" courseAssignmentProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
