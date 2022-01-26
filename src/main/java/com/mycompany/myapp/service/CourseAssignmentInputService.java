package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseAssignmentInput}.
 */
public interface CourseAssignmentInputService {
    /**
     * Save a courseAssignmentInput.
     *
     * @param courseAssignmentInputDTO the entity to save.
     * @return the persisted entity.
     */
    CourseAssignmentInputDTO save(CourseAssignmentInputDTO courseAssignmentInputDTO);

    /**
     * Partially updates a courseAssignmentInput.
     *
     * @param courseAssignmentInputDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseAssignmentInputDTO> partialUpdate(CourseAssignmentInputDTO courseAssignmentInputDTO);

    /**
     * Get all the courseAssignmentInputs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseAssignmentInputDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseAssignmentInput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseAssignmentInputDTO> findOne(Long id);

    /**
     * Delete the "id" courseAssignmentInput.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
