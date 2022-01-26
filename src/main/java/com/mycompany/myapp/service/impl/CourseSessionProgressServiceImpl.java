package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseSessionProgress;
import com.mycompany.myapp.repository.CourseSessionProgressRepository;
import com.mycompany.myapp.service.CourseSessionProgressService;
import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
import com.mycompany.myapp.service.mapper.CourseSessionProgressMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseSessionProgress}.
 */
@Service
@Transactional
public class CourseSessionProgressServiceImpl implements CourseSessionProgressService {

    private final Logger log = LoggerFactory.getLogger(CourseSessionProgressServiceImpl.class);

    private final CourseSessionProgressRepository courseSessionProgressRepository;

    private final CourseSessionProgressMapper courseSessionProgressMapper;

    public CourseSessionProgressServiceImpl(
        CourseSessionProgressRepository courseSessionProgressRepository,
        CourseSessionProgressMapper courseSessionProgressMapper
    ) {
        this.courseSessionProgressRepository = courseSessionProgressRepository;
        this.courseSessionProgressMapper = courseSessionProgressMapper;
    }

    @Override
    public CourseSessionProgressDTO save(CourseSessionProgressDTO courseSessionProgressDTO) {
        log.debug("Request to save CourseSessionProgress : {}", courseSessionProgressDTO);
        CourseSessionProgress courseSessionProgress = courseSessionProgressMapper.toEntity(courseSessionProgressDTO);
        courseSessionProgress = courseSessionProgressRepository.save(courseSessionProgress);
        return courseSessionProgressMapper.toDto(courseSessionProgress);
    }

    @Override
    public Optional<CourseSessionProgressDTO> partialUpdate(CourseSessionProgressDTO courseSessionProgressDTO) {
        log.debug("Request to partially update CourseSessionProgress : {}", courseSessionProgressDTO);

        return courseSessionProgressRepository
            .findById(courseSessionProgressDTO.getId())
            .map(existingCourseSessionProgress -> {
                courseSessionProgressMapper.partialUpdate(existingCourseSessionProgress, courseSessionProgressDTO);

                return existingCourseSessionProgress;
            })
            .map(courseSessionProgressRepository::save)
            .map(courseSessionProgressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSessionProgressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSessionProgresses");
        return courseSessionProgressRepository.findAll(pageable).map(courseSessionProgressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseSessionProgressDTO> findOne(Long id) {
        log.debug("Request to get CourseSessionProgress : {}", id);
        return courseSessionProgressRepository.findById(id).map(courseSessionProgressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSessionProgress : {}", id);
        courseSessionProgressRepository.deleteById(id);
    }
}
