package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourseEnrollment;
import com.mycompany.myapp.repository.CourseEnrollmentRepository;
import com.mycompany.myapp.service.CourseEnrollmentService;
import com.mycompany.myapp.service.dto.CourseEnrollmentDTO;
import com.mycompany.myapp.service.mapper.CourseEnrollmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseEnrollment}.
 */
@Service
@Transactional
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentServiceImpl.class);

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CourseEnrollmentMapper courseEnrollmentMapper;

    public CourseEnrollmentServiceImpl(
        CourseEnrollmentRepository courseEnrollmentRepository,
        CourseEnrollmentMapper courseEnrollmentMapper
    ) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseEnrollmentMapper = courseEnrollmentMapper;
    }

    @Override
    public CourseEnrollmentDTO save(CourseEnrollmentDTO courseEnrollmentDTO) {
        log.debug("Request to save CourseEnrollment : {}", courseEnrollmentDTO);
        CourseEnrollment courseEnrollment = courseEnrollmentMapper.toEntity(courseEnrollmentDTO);
        courseEnrollment = courseEnrollmentRepository.save(courseEnrollment);
        return courseEnrollmentMapper.toDto(courseEnrollment);
    }

    @Override
    public Optional<CourseEnrollmentDTO> partialUpdate(CourseEnrollmentDTO courseEnrollmentDTO) {
        log.debug("Request to partially update CourseEnrollment : {}", courseEnrollmentDTO);

        return courseEnrollmentRepository
            .findById(courseEnrollmentDTO.getId())
            .map(existingCourseEnrollment -> {
                courseEnrollmentMapper.partialUpdate(existingCourseEnrollment, courseEnrollmentDTO);

                return existingCourseEnrollment;
            })
            .map(courseEnrollmentRepository::save)
            .map(courseEnrollmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseEnrollmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseEnrollments");
        return courseEnrollmentRepository.findAll(pageable).map(courseEnrollmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseEnrollmentDTO> findOne(Long id) {
        log.debug("Request to get CourseEnrollment : {}", id);
        return courseEnrollmentRepository.findById(id).map(courseEnrollmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseEnrollment : {}", id);
        courseEnrollmentRepository.deleteById(id);
    }
}
