package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseAssignmentInput;
import com.mycompany.myapp.repository.CourseAssignmentInputRepository;
import com.mycompany.myapp.service.CourseAssignmentInputService;
import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentInputMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseAssignmentInput}.
 */
@Service
@Transactional
public class CourseAssignmentInputServiceImpl implements CourseAssignmentInputService {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentInputServiceImpl.class);

    private final CourseAssignmentInputRepository courseAssignmentInputRepository;

    private final CourseAssignmentInputMapper courseAssignmentInputMapper;

    public CourseAssignmentInputServiceImpl(
        CourseAssignmentInputRepository courseAssignmentInputRepository,
        CourseAssignmentInputMapper courseAssignmentInputMapper
    ) {
        this.courseAssignmentInputRepository = courseAssignmentInputRepository;
        this.courseAssignmentInputMapper = courseAssignmentInputMapper;
    }

    @Override
    public CourseAssignmentInputDTO save(CourseAssignmentInputDTO courseAssignmentInputDTO) {
        log.debug("Request to save CourseAssignmentInput : {}", courseAssignmentInputDTO);
        CourseAssignmentInput courseAssignmentInput = courseAssignmentInputMapper.toEntity(courseAssignmentInputDTO);
        courseAssignmentInput = courseAssignmentInputRepository.save(courseAssignmentInput);
        return courseAssignmentInputMapper.toDto(courseAssignmentInput);
    }

    @Override
    public Optional<CourseAssignmentInputDTO> partialUpdate(CourseAssignmentInputDTO courseAssignmentInputDTO) {
        log.debug("Request to partially update CourseAssignmentInput : {}", courseAssignmentInputDTO);

        return courseAssignmentInputRepository
            .findById(courseAssignmentInputDTO.getId())
            .map(existingCourseAssignmentInput -> {
                courseAssignmentInputMapper.partialUpdate(existingCourseAssignmentInput, courseAssignmentInputDTO);

                return existingCourseAssignmentInput;
            })
            .map(courseAssignmentInputRepository::save)
            .map(courseAssignmentInputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseAssignmentInputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseAssignmentInputs");
        return courseAssignmentInputRepository.findAll(pageable).map(courseAssignmentInputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseAssignmentInputDTO> findOne(Long id) {
        log.debug("Request to get CourseAssignmentInput : {}", id);
        return courseAssignmentInputRepository.findById(id).map(courseAssignmentInputMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseAssignmentInput : {}", id);
        courseAssignmentInputRepository.deleteById(id);
    }
}
