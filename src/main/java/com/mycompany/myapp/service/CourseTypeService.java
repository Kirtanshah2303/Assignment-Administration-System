package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseType}.
 */
public interface CourseTypeService {
    /**
     * Save a courseType.
     *
     * @param courseTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CourseTypeDTO save(CourseTypeDTO courseTypeDTO);

    /**
     * Partially updates a courseType.
     *
     * @param courseTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseTypeDTO> partialUpdate(CourseTypeDTO courseTypeDTO);

    /**
     * Get all the courseTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseTypeDTO> findOne(Long id);

    /**
     * Delete the "id" courseType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
