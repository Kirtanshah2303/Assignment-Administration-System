package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseAssignment}.
 */
public interface CourseAssignmentService {
    /**
     * Save a courseAssignment.
     *
     * @param courseAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    CourseAssignmentDTO save(CourseAssignmentDTO courseAssignmentDTO);

    /**
     * Partially updates a courseAssignment.
     *
     * @param courseAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseAssignmentDTO> partialUpdate(CourseAssignmentDTO courseAssignmentDTO);

    /**
     * Get all the courseAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseAssignmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" courseAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
