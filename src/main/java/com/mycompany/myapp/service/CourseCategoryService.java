package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CourseCategory}.
 */
public interface CourseCategoryService {
    /**
     * Save a courseCategory.
     *
     * @param courseCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    CourseCategoryDTO save(CourseCategoryDTO courseCategoryDTO);

    /**
     * Partially updates a courseCategory.
     *
     * @param courseCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseCategoryDTO> partialUpdate(CourseCategoryDTO courseCategoryDTO);

    /**
     * Get all the courseCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" courseCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
