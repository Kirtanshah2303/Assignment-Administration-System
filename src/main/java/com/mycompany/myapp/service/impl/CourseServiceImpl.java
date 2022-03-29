package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
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

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
            System.out.println("HAHAHAH");
            if (authority.contains(AuthoritiesConstants.ADMIN)) {
                return courseRepository.findAll();
            } else if (authority.contains(AuthoritiesConstants.FACULTY)) {
                return courseRepository.findCourseByUserEquals(user.get());
            } else if (authority.contains(AuthoritiesConstants.STUDENT)) {
                //                return courseRepository.findCourseByEnrolledUsersListsContaining(user.get(), pageable);
                return courseRepository.findAllByIsApproved(true);
            } else {
                return null;
            }
        } else {
            System.out.println("Inside ELse part");
            return null;
        }
    }
    /**
     * This method will fetch the courses for the given user according to the user's email.
     * @return List<CourseDTO>
     * */
    //    public List<CourseDTO> findAllByCurrentSemester() {
    //        log.debug("Request to get all Courses");
    //        Optional<User> user = userService.getUserWithAuthorities();
    //        if (user.isPresent()) {
    //            int currentYear = Math.subtractExact(
    //                GregorianCalendar.getInstance().get(GregorianCalendar.YEAR) % 2000,
    //                Integer.parseInt(user.get().getEmail().substring(0, 2))
    //            );
    //            int a = 1, b = 2;
    //            int month = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
    //            if (currentYear == 0) {
    //                a = 1;
    //                b = 1;
    //            } else if (currentYear == 1) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 1;
    //                    b = 2;
    //                } else {
    //                    a = 2;
    //                    b = 3;
    //                }
    //            } else if (currentYear == 2) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 3;
    //                    b = 4;
    //                } else {
    //                    a = 4;
    //                    b = 5;
    //                }
    //            } else if (currentYear == 3) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 5;
    //                    b = 6;
    //                } else {
    //                    a = 6;
    //                    b = 7;
    //                }
    //            } else if (currentYear == 4) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 7;
    //                    b = 8;
    //                }
    //            }
    //            List<CourseDTO> courseDTOList = new ArrayList<>();
    //            courseRepository
    //                .findAllBySemester(a, b)
    //                .stream()
    //                .forEach(course -> {
    //                    courseDTOList.add(courseMapper.toDto(course));
    //                });
    //            return courseDTOList;
    //        }
    //        return null;
    //    }
}
