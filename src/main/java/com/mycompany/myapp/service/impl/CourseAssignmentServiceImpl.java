package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.repository.CourseAssignmentRepository;
import com.mycompany.myapp.service.CourseAssignmentService;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseAssignment}.
 */
@Service
@Transactional
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentServiceImpl.class);

    private final CourseAssignmentRepository courseAssignmentRepository;

    private final CourseAssignmentMapper courseAssignmentMapper;

    public CourseAssignmentServiceImpl(
        CourseAssignmentRepository courseAssignmentRepository,
        CourseAssignmentMapper courseAssignmentMapper
    ) {
        this.courseAssignmentRepository = courseAssignmentRepository;
        this.courseAssignmentMapper = courseAssignmentMapper;
    }

    @Override
    public CourseAssignmentDTO save(CourseAssignmentDTO courseAssignmentDTO) {
        log.debug("Request to save CourseAssignment : {}", courseAssignmentDTO);
        CourseAssignment courseAssignment = courseAssignmentMapper.toEntity(courseAssignmentDTO);
        courseAssignment = courseAssignmentRepository.save(courseAssignment);
        return courseAssignmentMapper.toDto(courseAssignment);
    }

    @Override
    public Optional<CourseAssignmentDTO> partialUpdate(CourseAssignmentDTO courseAssignmentDTO) {
        log.debug("Request to partially update CourseAssignment : {}", courseAssignmentDTO);

        return courseAssignmentRepository
            .findById(courseAssignmentDTO.getId())
            .map(existingCourseAssignment -> {
                courseAssignmentMapper.partialUpdate(existingCourseAssignment, courseAssignmentDTO);

                return existingCourseAssignment;
            })
            .map(courseAssignmentRepository::save)
            .map(courseAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseAssignments");
        return courseAssignmentRepository.findAll(pageable).map(courseAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseAssignmentDTO> findOne(Long id) {
        log.debug("Request to get CourseAssignment : {}", id);
        return courseAssignmentRepository.findById(id).map(courseAssignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseAssignment : {}", id);
        courseAssignmentRepository.deleteById(id);
    }
}
