package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseSessionProgress}.
 */
public interface CourseSessionProgressService {
    /**
     * Save a courseSessionProgress.
     *
     * @param courseSessionProgressDTO the entity to save.
     * @return the persisted entity.
     */
    CourseSessionProgressDTO save(CourseSessionProgressDTO courseSessionProgressDTO);

    /**
     * Partially updates a courseSessionProgress.
     *
     * @param courseSessionProgressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseSessionProgressDTO> partialUpdate(CourseSessionProgressDTO courseSessionProgressDTO);

    /**
     * Get all the courseSessionProgresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseSessionProgressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseSessionProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseSessionProgressDTO> findOne(Long id);

    /**
     * Delete the "id" courseSessionProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
