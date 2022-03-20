package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Course}.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final UserService userService;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public Optional<CourseDTO> partialUpdate(CourseDTO courseDTO) {
        log.debug("Request to partially update Course : {}", courseDTO);

        return courseRepository
            .findById(courseDTO.getId())
            .map(existingCourse -> {
                courseMapper.partialUpdate(existingCourse, courseDTO);

                return existingCourse;
            })
            .map(courseRepository::save)
            .map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }

    /**
     * This method will fetch the courses for the given user according to the user's email.
     * @return List<CourseDTO>
     * */
    public List<CourseDTO> findAllByCurrentSemester() {
        log.debug("Request to get all Courses");
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            int currentYear = Math.subtractExact(
                GregorianCalendar.getInstance().get(GregorianCalendar.YEAR) % 2000,
                Integer.parseInt(user.get().getEmail().substring(0, 2))
            );
            int a, b;
            switch (currentYear) {
                case 2:
                    a = 3;
                    b = 4;
                    break;
                case 3:
                    a = 5;
                    b = 6;
                    break;
                case 4:
                    a = 7;
                    b = 8;
                    break;
                default:
                    a = 1;
                    b = 2;
                    break;
            }
            List<CourseDTO> courseDTOList = new ArrayList<>();
            courseRepository
                .findAllBySemester(a, b)
                .stream()
                .forEach(course -> {
                    courseDTOList.add(courseMapper.toDto(course));
                });
            return courseDTOList;
        }
        return null;
    }
}
