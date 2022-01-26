package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseAssignmentOutput;
import com.mycompany.myapp.repository.CourseAssignmentOutputRepository;
import com.mycompany.myapp.service.CourseAssignmentOutputService;
import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentOutputMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseAssignmentOutput}.
 */
@Service
@Transactional
public class CourseAssignmentOutputServiceImpl implements CourseAssignmentOutputService {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentOutputServiceImpl.class);

    private final CourseAssignmentOutputRepository courseAssignmentOutputRepository;

    private final CourseAssignmentOutputMapper courseAssignmentOutputMapper;

    public CourseAssignmentOutputServiceImpl(
        CourseAssignmentOutputRepository courseAssignmentOutputRepository,
        CourseAssignmentOutputMapper courseAssignmentOutputMapper
    ) {
        this.courseAssignmentOutputRepository = courseAssignmentOutputRepository;
        this.courseAssignmentOutputMapper = courseAssignmentOutputMapper;
    }

    @Override
    public CourseAssignmentOutputDTO save(CourseAssignmentOutputDTO courseAssignmentOutputDTO) {
        log.debug("Request to save CourseAssignmentOutput : {}", courseAssignmentOutputDTO);
        CourseAssignmentOutput courseAssignmentOutput = courseAssignmentOutputMapper.toEntity(courseAssignmentOutputDTO);
        courseAssignmentOutput = courseAssignmentOutputRepository.save(courseAssignmentOutput);
        return courseAssignmentOutputMapper.toDto(courseAssignmentOutput);
    }

    @Override
    public Optional<CourseAssignmentOutputDTO> partialUpdate(CourseAssignmentOutputDTO courseAssignmentOutputDTO) {
        log.debug("Request to partially update CourseAssignmentOutput : {}", courseAssignmentOutputDTO);

        return courseAssignmentOutputRepository
            .findById(courseAssignmentOutputDTO.getId())
            .map(existingCourseAssignmentOutput -> {
                courseAssignmentOutputMapper.partialUpdate(existingCourseAssignmentOutput, courseAssignmentOutputDTO);

                return existingCourseAssignmentOutput;
            })
            .map(courseAssignmentOutputRepository::save)
            .map(courseAssignmentOutputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseAssignmentOutputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseAssignmentOutputs");
        return courseAssignmentOutputRepository.findAll(pageable).map(courseAssignmentOutputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseAssignmentOutputDTO> findOne(Long id) {
        log.debug("Request to get CourseAssignmentOutput : {}", id);
        return courseAssignmentOutputRepository.findById(id).map(courseAssignmentOutputMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseAssignmentOutput : {}", id);
        courseAssignmentOutputRepository.deleteById(id);
    }
}
