package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseSessionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseSession}.
 */
public interface CourseSessionService {
    /**
     * Save a courseSession.
     *
     * @param courseSessionDTO the entity to save.
     * @return the persisted entity.
     */
    CourseSessionDTO save(CourseSessionDTO courseSessionDTO);

    /**
     * Partially updates a courseSession.
     *
     * @param courseSessionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseSessionDTO> partialUpdate(CourseSessionDTO courseSessionDTO);

    /**
     * Get all the courseSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseSessionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseSessionDTO> findOne(Long id);

    /**
     * Delete the "id" courseSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
