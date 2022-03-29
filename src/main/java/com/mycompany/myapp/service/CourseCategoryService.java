package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

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

    List<CourseCategory> listParentCategory();

    List<CourseCategory> listByParentId(Long id);

    Map<String, List<Course>> getCoursesBySubCategories();

    Map<String, List<CourseCategory>> getCourseSubCategoriesByParentCategories();

    ResponseEntity<Map<Long, Integer>> getCourseCountBySubCategory(Long parentId);

    ResponseEntity<Map<Long, Integer>> getCourseCountByParentCategory();
}
