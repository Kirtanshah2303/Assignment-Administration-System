package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseReviewStatus;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    private final UserRepository userRepository;

    private final CourseMapper courseMapper;

    private final UserService userService;

    public CourseServiceImpl(
        CourseRepository courseRepository,
        UserRepository userRepository,
        CourseMapper courseMapper,
        UserService userService
    ) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        /**
         * Setting the default values that needs to set during the course creation.
         * */
        if (course.getCourseCreatedOn() == null) {
            course.setCourseCreatedOn(LocalDate.now());
        }
        course.setIsApproved(false);
        course.isPublished(false);
        course.setIsDraft(true);
        course.setCourseUpdatedOn(LocalDate.now());
        course.setUser(userService.getUserWithAuthorities().get());
        /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public Optional<CourseDTO> partialUpdate(CourseDTO courseDTO) {
        log.debug("Request to partially update Course : {}", courseDTO);

        return courseRepository
            .findById(courseDTO.getId())
            .map(existingCourse -> {
                existingCourse.setCourseUpdatedOn(LocalDate.now());
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
            int a = 1, b = 2;
            int month = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
            if (currentYear == 0) {
                a = 1;
                b = 1;
            } else if (currentYear == 1) {
                if (month <= GregorianCalendar.JUNE) {
                    a = 1;
                    b = 2;
                } else {
                    a = 2;
                    b = 3;
                }
            } else if (currentYear == 2) {
                if (month <= GregorianCalendar.JUNE) {
                    a = 3;
                    b = 4;
                } else {
                    a = 4;
                    b = 5;
                }
            } else if (currentYear == 3) {
                if (month <= GregorianCalendar.JUNE) {
                    a = 5;
                    b = 6;
                } else {
                    a = 6;
                    b = 7;
                }
            } else if (currentYear == 4) {
                if (month <= GregorianCalendar.JUNE) {
                    a = 7;
                    b = 8;
                }
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

    @Override
    public List<CourseDTO> getEnrolledCourses() throws Exception {
        Optional<User> user = userService.getUserWithAuthorities();
        List<Course> courses;
        List<CourseDTO> courseDTOList = new ArrayList<>();
        CourseDTO courseDTO;
        if (user.isPresent()) {
            courses = courseRepository.findCourseByEnrolledUsersListsContaining(user.get());
            for (Course course : courses) {
                courseDTO = new CourseDTO(courseMapper.toDto(course));
                courseDTO.setEnrolled(true);
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        } else {
            throw new Exception("User not found");
        }
    }

    /**
     * CUSTOM
     * */
    @Override
    public List<CourseDTO> getByCategoryId(Long id) throws Exception {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            List<CourseDTO> list = new ArrayList<>();
            List<Course> courses = courseRepository.findByCategoryId(id);
            CourseDTO courseDTO;
            for (Course course : courses) {
                courseDTO = new CourseDTO(courseMapper.toDto(course));
                if (course.getEnrolledUsersLists().contains(user.get())) {
                    System.out.println("HAHAHAHAHAHAHA  --> " + course.getEnrolledUsersLists());
                    courseDTO.setEnrolled(true);
                } else {
                    System.out.println("HAHAHAHAHAHAHA2  --> " + course.getEnrolledUsersLists());
                    courseDTO.setEnrolled(false);
                }
                courseDTO.setMinStudents(course.getMinStudents() + getStudentEnrolledCountByCourse(course.getId()).getBody());
                list.add(courseDTO);
            }
            return list;
        } else {
            throw new Exception("User ot found");
        }
    }

    @Override
    public ResponseEntity<Integer> getStudentEnrolledCountByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Integer count = course.get().getEnrolledUsersLists().size();
            return ResponseEntity.ok(count);
        } else {
            log.error("Course not found");
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity enrollInCourse(Long courseId) {
        AtomicBoolean flag = new AtomicBoolean(false);
        try {
            System.out.println("Inside Try Block");
            System.out.println("courseId is --> " + courseId);
            //            System.out.println("courseId is --> "+Long.valueOf(courseId));

            Optional<Course> demoCourse = courseRepository.findById(courseId);
            Set<User> alreadyEnrolledUsers = demoCourse.get().getEnrolledUsersLists();
            if (
                userService.getUserWithAuthorities().isPresent() &&
                !alreadyEnrolledUsers.contains(userService.getUserWithAuthorities().get())
            ) {
                System.out.println("Inside If Part");
                alreadyEnrolledUsers.add(userService.getUserWithAuthorities().get());
                System.out.println(alreadyEnrolledUsers);
                demoCourse.get().setEnrolledUsersLists(alreadyEnrolledUsers);
                flag.set(true);
                courseRepository.save(demoCourse.orElse(null));
            }
            if (flag.get()) return ResponseEntity.accepted().build(); else {
                return ResponseEntity.badRequest().body("Already enrolled");
            }
            //            courseRepository
            //                .findById(courseId)
            //                .map(
            //                    existingCourse -> {
            //                        System.out.println("Inside Try Block 2");
            //                        Set<User> alreadyEnrolledUsers = existingCourse.getEnrolledUsersLists();
            //                        if (
            //                            userService.getUserWithAuthorities().isPresent() &&
            //                                !alreadyEnrolledUsers.contains(userService.getUserWithAuthorities().get())
            //                        ) {
            //                            System.out.println("Inside If Part");
            //                            alreadyEnrolledUsers.add(userService.getUserWithAuthorities().get());
            //                            System.out.println(alreadyEnrolledUsers);
            //                            existingCourse.setEnrolledUsersLists(alreadyEnrolledUsers);
            //                            flag.set(true);
            //                        }
            //                        return existingCourse;
            //                    }
            //                )
            //                .map(courseRepository::save);
            //            if (flag.get()) return ResponseEntity.accepted().build(); else {
            //                return ResponseEntity.badRequest().body("Already enrolled");
            //            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> getOverview() {
        Map<String, String> map = new HashMap<>();

        Integer data = courseRepository.findAll().size();
        map.put("totalCourses", data.toString());

        data = courseRepository.findTotalEnrollment();
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            data += course.getMinStudents();
        }
        map.put("totalEnrollments", data.toString());

        data = userService.getTotalUsersByAuthority(AuthoritiesConstants.FACULTY);
        map.put("totalInstructors", data.toString());

        return ResponseEntity.ok().body(map);
    }

    @Override
    public ResponseEntity<Set<User>> getEnrolledUsersByCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Set<User> users = course.get().getEnrolledUsersLists();
            return ResponseEntity.ok().body(users);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
