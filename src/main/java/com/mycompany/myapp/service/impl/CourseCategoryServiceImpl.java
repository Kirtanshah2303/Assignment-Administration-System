package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.repository.CourseCategoryRepository;
import com.mycompany.myapp.service.CourseCategoryService;
import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import com.mycompany.myapp.service.mapper.CourseCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseCategory}.
 */
@Service
@Transactional
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final Logger log = LoggerFactory.getLogger(CourseCategoryServiceImpl.class);

    private final CourseCategoryRepository courseCategoryRepository;

    private final CourseCategoryMapper courseCategoryMapper;

    public CourseCategoryServiceImpl(CourseCategoryRepository courseCategoryRepository, CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
    }

    @Override
    public CourseCategoryDTO save(CourseCategoryDTO courseCategoryDTO) {
        log.debug("Request to save CourseCategory : {}", courseCategoryDTO);
        CourseCategory courseCategory = courseCategoryMapper.toEntity(courseCategoryDTO);
        courseCategory = courseCategoryRepository.save(courseCategory);
        return courseCategoryMapper.toDto(courseCategory);
    }

    @Override
    public Optional<CourseCategoryDTO> partialUpdate(CourseCategoryDTO courseCategoryDTO) {
        log.debug("Request to partially update CourseCategory : {}", courseCategoryDTO);

        return courseCategoryRepository
            .findById(courseCategoryDTO.getId())
            .map(existingCourseCategory -> {
                courseCategoryMapper.partialUpdate(existingCourseCategory, courseCategoryDTO);

                return existingCourseCategory;
            })
            .map(courseCategoryRepository::save)
            .map(courseCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseCategories");
        return courseCategoryRepository.findAll(pageable).map(courseCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseCategoryDTO> findOne(Long id) {
        log.debug("Request to get CourseCategory : {}", id);
        return courseCategoryRepository.findById(id).map(courseCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseCategory : {}", id);
        courseCategoryRepository.deleteById(id);
    }
}
