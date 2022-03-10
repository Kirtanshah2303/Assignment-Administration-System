package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.CourseSectionRepository;
import com.mycompany.myapp.service.CourseSectionService;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import com.mycompany.myapp.service.mapper.CourseSectionMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseSection}.
 */
@Service
@Transactional
public class CourseSectionServiceImpl implements CourseSectionService {

    private final Logger log = LoggerFactory.getLogger(CourseSectionServiceImpl.class);

    private final CourseSectionRepository courseSectionRepository;

    private final CourseSectionMapper courseSectionMapper;

    private final CourseMapper courseMapper;

    private final CourseRepository courseRepository;

    public CourseSectionServiceImpl(
        CourseSectionRepository courseSectionRepository,
        CourseSectionMapper courseSectionMapper,
        CourseMapper courseMapper,
        CourseRepository courseRepository
    ) {
        this.courseSectionRepository = courseSectionRepository;
        this.courseSectionMapper = courseSectionMapper;
        this.courseMapper = courseMapper;
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseSectionDTO save(CourseSectionDTO courseSectionDTO) {
        log.debug("Request to save CourseSection : {}", courseSectionDTO);
        CourseSection courseSection = courseSectionMapper.toEntity(courseSectionDTO);
        courseSection = courseSectionRepository.save(courseSection);
        return courseSectionMapper.toDto(courseSection);
    }

    @Override
    public Optional<CourseSectionDTO> partialUpdate(CourseSectionDTO courseSectionDTO) {
        log.debug("Request to partially update CourseSection : {}", courseSectionDTO);

        return courseSectionRepository
            .findById(courseSectionDTO.getId())
            .map(existingCourseSection -> {
                courseSectionMapper.partialUpdate(existingCourseSection, courseSectionDTO);

                return existingCourseSection;
            })
            .map(courseSectionRepository::save)
            .map(courseSectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSections");
        return courseSectionRepository.findAll(pageable).map(courseSectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseSectionDTO> findOne(Long id) {
        log.debug("Request to get CourseSection : {}", id);
        return courseSectionRepository.findById(id).map(courseSectionMapper::toDto);
    }

    @Override
    public List<CourseSection> findSectionsByCourse(Long id) {
        return courseSectionRepository.findCourseSectionByCourse(courseRepository.findById(id));
    }

    @Override
    public List<CourseSection> findCourseSectionsByCourse(Long id) {
        return courseSectionRepository.findCourseSectionsByCourse(courseRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSection : {}", id);
        courseSectionRepository.deleteById(id);
    }
}
