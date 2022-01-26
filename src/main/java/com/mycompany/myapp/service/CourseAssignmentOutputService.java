package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseAssignmentOutput}.
 */
public interface CourseAssignmentOutputService {
    /**
     * Save a courseAssignmentOutput.
     *
     * @param courseAssignmentOutputDTO the entity to save.
     * @return the persisted entity.
     */
    CourseAssignmentOutputDTO save(CourseAssignmentOutputDTO courseAssignmentOutputDTO);

    /**
     * Partially updates a courseAssignmentOutput.
     *
     * @param courseAssignmentOutputDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseAssignmentOutputDTO> partialUpdate(CourseAssignmentOutputDTO courseAssignmentOutputDTO);

    /**
     * Get all the courseAssignmentOutputs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseAssignmentOutputDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseAssignmentOutput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseAssignmentOutputDTO> findOne(Long id);

    /**
     * Delete the "id" courseAssignmentOutput.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
