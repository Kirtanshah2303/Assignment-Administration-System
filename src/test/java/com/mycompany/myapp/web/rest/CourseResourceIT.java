package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.domain.CourseLevel;
import com.mycompany.myapp.domain.CourseType;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.service.criteria.CourseCriteria;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CourseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseResourceIT {

    private static final String DEFAULT_COURSE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_OBJECTIVES = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_OBJECTIVES = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_PREVIEW_URL = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_PREVIEW_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_COURSE_LENGTH = 1;
    private static final Integer UPDATED_COURSE_LENGTH = 2;
    private static final Integer SMALLER_COURSE_LENGTH = 1 - 1;

    private static final String DEFAULT_COURSE_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_LOGO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COURSE_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COURSE_CREATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COURSE_CREATED_ON = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_COURSE_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COURSE_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COURSE_UPDATED_ON = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_COURSE_ROOT_DIR = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_ROOT_DIR = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;

    private static final LocalDate DEFAULT_COURSE_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COURSE_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COURSE_APPROVAL_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseMockMvc;

    private Course course;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .courseTitle(DEFAULT_COURSE_TITLE)
            .courseDescription(DEFAULT_COURSE_DESCRIPTION)
            .courseObjectives(DEFAULT_COURSE_OBJECTIVES)
            .courseSubTitle(DEFAULT_COURSE_SUB_TITLE)
            .coursePreviewURL(DEFAULT_COURSE_PREVIEW_URL)
            .courseLength(DEFAULT_COURSE_LENGTH)
            .courseLogo(DEFAULT_COURSE_LOGO)
            .courseCreatedOn(DEFAULT_COURSE_CREATED_ON)
            .courseUpdatedOn(DEFAULT_COURSE_UPDATED_ON)
            .courseRootDir(DEFAULT_COURSE_ROOT_DIR)
            .amount(DEFAULT_AMOUNT)
            .isDraft(DEFAULT_IS_DRAFT)
            .isApproved(DEFAULT_IS_APPROVED)
            .isPublished(DEFAULT_IS_PUBLISHED)
            .courseApprovalDate(DEFAULT_COURSE_APPROVAL_DATE);
        return course;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createUpdatedEntity(EntityManager em) {
        Course course = new Course()
            .courseTitle(UPDATED_COURSE_TITLE)
            .courseDescription(UPDATED_COURSE_DESCRIPTION)
            .courseObjectives(UPDATED_COURSE_OBJECTIVES)
            .courseSubTitle(UPDATED_COURSE_SUB_TITLE)
            .coursePreviewURL(UPDATED_COURSE_PREVIEW_URL)
            .courseLength(UPDATED_COURSE_LENGTH)
            .courseLogo(UPDATED_COURSE_LOGO)
            .courseCreatedOn(UPDATED_COURSE_CREATED_ON)
            .courseUpdatedOn(UPDATED_COURSE_UPDATED_ON)
            .courseRootDir(UPDATED_COURSE_ROOT_DIR)
            .amount(UPDATED_AMOUNT)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .courseApprovalDate(UPDATED_COURSE_APPROVAL_DATE);
        return course;
    }

    @BeforeEach
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();
        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);
        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseTitle()).isEqualTo(DEFAULT_COURSE_TITLE);
        assertThat(testCourse.getCourseDescription()).isEqualTo(DEFAULT_COURSE_DESCRIPTION);
        assertThat(testCourse.getCourseObjectives()).isEqualTo(DEFAULT_COURSE_OBJECTIVES);
        assertThat(testCourse.getCourseSubTitle()).isEqualTo(DEFAULT_COURSE_SUB_TITLE);
        assertThat(testCourse.getCoursePreviewURL()).isEqualTo(DEFAULT_COURSE_PREVIEW_URL);
        assertThat(testCourse.getCourseLength()).isEqualTo(DEFAULT_COURSE_LENGTH);
        assertThat(testCourse.getCourseLogo()).isEqualTo(DEFAULT_COURSE_LOGO);
        assertThat(testCourse.getCourseCreatedOn()).isEqualTo(DEFAULT_COURSE_CREATED_ON);
        assertThat(testCourse.getCourseUpdatedOn()).isEqualTo(DEFAULT_COURSE_UPDATED_ON);
        assertThat(testCourse.getCourseRootDir()).isEqualTo(DEFAULT_COURSE_ROOT_DIR);
        assertThat(testCourse.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCourse.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourse.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testCourse.getIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
        assertThat(testCourse.getCourseApprovalDate()).isEqualTo(DEFAULT_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void createCourseWithExistingId() throws Exception {
        // Create the Course with an existing ID
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCourseTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseTitle(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseDescription(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseObjectivesIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseObjectives(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseSubTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseSubTitle(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseLogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseLogo(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseCreatedOn(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCourseUpdatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setCourseUpdatedOn(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setIsDraft(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setIsApproved(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPublishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        course.setIsPublished(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseTitle").value(hasItem(DEFAULT_COURSE_TITLE)))
            .andExpect(jsonPath("$.[*].courseDescription").value(hasItem(DEFAULT_COURSE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].courseObjectives").value(hasItem(DEFAULT_COURSE_OBJECTIVES)))
            .andExpect(jsonPath("$.[*].courseSubTitle").value(hasItem(DEFAULT_COURSE_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].coursePreviewURL").value(hasItem(DEFAULT_COURSE_PREVIEW_URL)))
            .andExpect(jsonPath("$.[*].courseLength").value(hasItem(DEFAULT_COURSE_LENGTH)))
            .andExpect(jsonPath("$.[*].courseLogo").value(hasItem(DEFAULT_COURSE_LOGO)))
            .andExpect(jsonPath("$.[*].courseCreatedOn").value(hasItem(DEFAULT_COURSE_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].courseUpdatedOn").value(hasItem(DEFAULT_COURSE_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].courseRootDir").value(hasItem(DEFAULT_COURSE_ROOT_DIR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].courseApprovalDate").value(hasItem(DEFAULT_COURSE_APPROVAL_DATE.toString())));
    }

    @Test
    @Transactional
    void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc
            .perform(get(ENTITY_API_URL_ID, course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.courseTitle").value(DEFAULT_COURSE_TITLE))
            .andExpect(jsonPath("$.courseDescription").value(DEFAULT_COURSE_DESCRIPTION))
            .andExpect(jsonPath("$.courseObjectives").value(DEFAULT_COURSE_OBJECTIVES))
            .andExpect(jsonPath("$.courseSubTitle").value(DEFAULT_COURSE_SUB_TITLE))
            .andExpect(jsonPath("$.coursePreviewURL").value(DEFAULT_COURSE_PREVIEW_URL))
            .andExpect(jsonPath("$.courseLength").value(DEFAULT_COURSE_LENGTH))
            .andExpect(jsonPath("$.courseLogo").value(DEFAULT_COURSE_LOGO))
            .andExpect(jsonPath("$.courseCreatedOn").value(DEFAULT_COURSE_CREATED_ON.toString()))
            .andExpect(jsonPath("$.courseUpdatedOn").value(DEFAULT_COURSE_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.courseRootDir").value(DEFAULT_COURSE_ROOT_DIR))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.courseApprovalDate").value(DEFAULT_COURSE_APPROVAL_DATE.toString()));
    }

    @Test
    @Transactional
    void getCoursesByIdFiltering() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        Long id = course.getId();

        defaultCourseShouldBeFound("id.equals=" + id);
        defaultCourseShouldNotBeFound("id.notEquals=" + id);

        defaultCourseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle equals to DEFAULT_COURSE_TITLE
        defaultCourseShouldBeFound("courseTitle.equals=" + DEFAULT_COURSE_TITLE);

        // Get all the courseList where courseTitle equals to UPDATED_COURSE_TITLE
        defaultCourseShouldNotBeFound("courseTitle.equals=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle not equals to DEFAULT_COURSE_TITLE
        defaultCourseShouldNotBeFound("courseTitle.notEquals=" + DEFAULT_COURSE_TITLE);

        // Get all the courseList where courseTitle not equals to UPDATED_COURSE_TITLE
        defaultCourseShouldBeFound("courseTitle.notEquals=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle in DEFAULT_COURSE_TITLE or UPDATED_COURSE_TITLE
        defaultCourseShouldBeFound("courseTitle.in=" + DEFAULT_COURSE_TITLE + "," + UPDATED_COURSE_TITLE);

        // Get all the courseList where courseTitle equals to UPDATED_COURSE_TITLE
        defaultCourseShouldNotBeFound("courseTitle.in=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle is not null
        defaultCourseShouldBeFound("courseTitle.specified=true");

        // Get all the courseList where courseTitle is null
        defaultCourseShouldNotBeFound("courseTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle contains DEFAULT_COURSE_TITLE
        defaultCourseShouldBeFound("courseTitle.contains=" + DEFAULT_COURSE_TITLE);

        // Get all the courseList where courseTitle contains UPDATED_COURSE_TITLE
        defaultCourseShouldNotBeFound("courseTitle.contains=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseTitle does not contain DEFAULT_COURSE_TITLE
        defaultCourseShouldNotBeFound("courseTitle.doesNotContain=" + DEFAULT_COURSE_TITLE);

        // Get all the courseList where courseTitle does not contain UPDATED_COURSE_TITLE
        defaultCourseShouldBeFound("courseTitle.doesNotContain=" + UPDATED_COURSE_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription equals to DEFAULT_COURSE_DESCRIPTION
        defaultCourseShouldBeFound("courseDescription.equals=" + DEFAULT_COURSE_DESCRIPTION);

        // Get all the courseList where courseDescription equals to UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldNotBeFound("courseDescription.equals=" + UPDATED_COURSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription not equals to DEFAULT_COURSE_DESCRIPTION
        defaultCourseShouldNotBeFound("courseDescription.notEquals=" + DEFAULT_COURSE_DESCRIPTION);

        // Get all the courseList where courseDescription not equals to UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldBeFound("courseDescription.notEquals=" + UPDATED_COURSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription in DEFAULT_COURSE_DESCRIPTION or UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldBeFound("courseDescription.in=" + DEFAULT_COURSE_DESCRIPTION + "," + UPDATED_COURSE_DESCRIPTION);

        // Get all the courseList where courseDescription equals to UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldNotBeFound("courseDescription.in=" + UPDATED_COURSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription is not null
        defaultCourseShouldBeFound("courseDescription.specified=true");

        // Get all the courseList where courseDescription is null
        defaultCourseShouldNotBeFound("courseDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription contains DEFAULT_COURSE_DESCRIPTION
        defaultCourseShouldBeFound("courseDescription.contains=" + DEFAULT_COURSE_DESCRIPTION);

        // Get all the courseList where courseDescription contains UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldNotBeFound("courseDescription.contains=" + UPDATED_COURSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseDescription does not contain DEFAULT_COURSE_DESCRIPTION
        defaultCourseShouldNotBeFound("courseDescription.doesNotContain=" + DEFAULT_COURSE_DESCRIPTION);

        // Get all the courseList where courseDescription does not contain UPDATED_COURSE_DESCRIPTION
        defaultCourseShouldBeFound("courseDescription.doesNotContain=" + UPDATED_COURSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives equals to DEFAULT_COURSE_OBJECTIVES
        defaultCourseShouldBeFound("courseObjectives.equals=" + DEFAULT_COURSE_OBJECTIVES);

        // Get all the courseList where courseObjectives equals to UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldNotBeFound("courseObjectives.equals=" + UPDATED_COURSE_OBJECTIVES);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives not equals to DEFAULT_COURSE_OBJECTIVES
        defaultCourseShouldNotBeFound("courseObjectives.notEquals=" + DEFAULT_COURSE_OBJECTIVES);

        // Get all the courseList where courseObjectives not equals to UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldBeFound("courseObjectives.notEquals=" + UPDATED_COURSE_OBJECTIVES);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives in DEFAULT_COURSE_OBJECTIVES or UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldBeFound("courseObjectives.in=" + DEFAULT_COURSE_OBJECTIVES + "," + UPDATED_COURSE_OBJECTIVES);

        // Get all the courseList where courseObjectives equals to UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldNotBeFound("courseObjectives.in=" + UPDATED_COURSE_OBJECTIVES);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives is not null
        defaultCourseShouldBeFound("courseObjectives.specified=true");

        // Get all the courseList where courseObjectives is null
        defaultCourseShouldNotBeFound("courseObjectives.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives contains DEFAULT_COURSE_OBJECTIVES
        defaultCourseShouldBeFound("courseObjectives.contains=" + DEFAULT_COURSE_OBJECTIVES);

        // Get all the courseList where courseObjectives contains UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldNotBeFound("courseObjectives.contains=" + UPDATED_COURSE_OBJECTIVES);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseObjectivesNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseObjectives does not contain DEFAULT_COURSE_OBJECTIVES
        defaultCourseShouldNotBeFound("courseObjectives.doesNotContain=" + DEFAULT_COURSE_OBJECTIVES);

        // Get all the courseList where courseObjectives does not contain UPDATED_COURSE_OBJECTIVES
        defaultCourseShouldBeFound("courseObjectives.doesNotContain=" + UPDATED_COURSE_OBJECTIVES);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle equals to DEFAULT_COURSE_SUB_TITLE
        defaultCourseShouldBeFound("courseSubTitle.equals=" + DEFAULT_COURSE_SUB_TITLE);

        // Get all the courseList where courseSubTitle equals to UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldNotBeFound("courseSubTitle.equals=" + UPDATED_COURSE_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle not equals to DEFAULT_COURSE_SUB_TITLE
        defaultCourseShouldNotBeFound("courseSubTitle.notEquals=" + DEFAULT_COURSE_SUB_TITLE);

        // Get all the courseList where courseSubTitle not equals to UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldBeFound("courseSubTitle.notEquals=" + UPDATED_COURSE_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle in DEFAULT_COURSE_SUB_TITLE or UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldBeFound("courseSubTitle.in=" + DEFAULT_COURSE_SUB_TITLE + "," + UPDATED_COURSE_SUB_TITLE);

        // Get all the courseList where courseSubTitle equals to UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldNotBeFound("courseSubTitle.in=" + UPDATED_COURSE_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle is not null
        defaultCourseShouldBeFound("courseSubTitle.specified=true");

        // Get all the courseList where courseSubTitle is null
        defaultCourseShouldNotBeFound("courseSubTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle contains DEFAULT_COURSE_SUB_TITLE
        defaultCourseShouldBeFound("courseSubTitle.contains=" + DEFAULT_COURSE_SUB_TITLE);

        // Get all the courseList where courseSubTitle contains UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldNotBeFound("courseSubTitle.contains=" + UPDATED_COURSE_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseSubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseSubTitle does not contain DEFAULT_COURSE_SUB_TITLE
        defaultCourseShouldNotBeFound("courseSubTitle.doesNotContain=" + DEFAULT_COURSE_SUB_TITLE);

        // Get all the courseList where courseSubTitle does not contain UPDATED_COURSE_SUB_TITLE
        defaultCourseShouldBeFound("courseSubTitle.doesNotContain=" + UPDATED_COURSE_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL equals to DEFAULT_COURSE_PREVIEW_URL
        defaultCourseShouldBeFound("coursePreviewURL.equals=" + DEFAULT_COURSE_PREVIEW_URL);

        // Get all the courseList where coursePreviewURL equals to UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldNotBeFound("coursePreviewURL.equals=" + UPDATED_COURSE_PREVIEW_URL);
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL not equals to DEFAULT_COURSE_PREVIEW_URL
        defaultCourseShouldNotBeFound("coursePreviewURL.notEquals=" + DEFAULT_COURSE_PREVIEW_URL);

        // Get all the courseList where coursePreviewURL not equals to UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldBeFound("coursePreviewURL.notEquals=" + UPDATED_COURSE_PREVIEW_URL);
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL in DEFAULT_COURSE_PREVIEW_URL or UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldBeFound("coursePreviewURL.in=" + DEFAULT_COURSE_PREVIEW_URL + "," + UPDATED_COURSE_PREVIEW_URL);

        // Get all the courseList where coursePreviewURL equals to UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldNotBeFound("coursePreviewURL.in=" + UPDATED_COURSE_PREVIEW_URL);
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL is not null
        defaultCourseShouldBeFound("coursePreviewURL.specified=true");

        // Get all the courseList where coursePreviewURL is null
        defaultCourseShouldNotBeFound("coursePreviewURL.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL contains DEFAULT_COURSE_PREVIEW_URL
        defaultCourseShouldBeFound("coursePreviewURL.contains=" + DEFAULT_COURSE_PREVIEW_URL);

        // Get all the courseList where coursePreviewURL contains UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldNotBeFound("coursePreviewURL.contains=" + UPDATED_COURSE_PREVIEW_URL);
    }

    @Test
    @Transactional
    void getAllCoursesByCoursePreviewURLNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where coursePreviewURL does not contain DEFAULT_COURSE_PREVIEW_URL
        defaultCourseShouldNotBeFound("coursePreviewURL.doesNotContain=" + DEFAULT_COURSE_PREVIEW_URL);

        // Get all the courseList where coursePreviewURL does not contain UPDATED_COURSE_PREVIEW_URL
        defaultCourseShouldBeFound("coursePreviewURL.doesNotContain=" + UPDATED_COURSE_PREVIEW_URL);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength equals to DEFAULT_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.equals=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength equals to UPDATED_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.equals=" + UPDATED_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength not equals to DEFAULT_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.notEquals=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength not equals to UPDATED_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.notEquals=" + UPDATED_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength in DEFAULT_COURSE_LENGTH or UPDATED_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.in=" + DEFAULT_COURSE_LENGTH + "," + UPDATED_COURSE_LENGTH);

        // Get all the courseList where courseLength equals to UPDATED_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.in=" + UPDATED_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength is not null
        defaultCourseShouldBeFound("courseLength.specified=true");

        // Get all the courseList where courseLength is null
        defaultCourseShouldNotBeFound("courseLength.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength is greater than or equal to DEFAULT_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.greaterThanOrEqual=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength is greater than or equal to UPDATED_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.greaterThanOrEqual=" + UPDATED_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength is less than or equal to DEFAULT_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.lessThanOrEqual=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength is less than or equal to SMALLER_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.lessThanOrEqual=" + SMALLER_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength is less than DEFAULT_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.lessThan=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength is less than UPDATED_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.lessThan=" + UPDATED_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLength is greater than DEFAULT_COURSE_LENGTH
        defaultCourseShouldNotBeFound("courseLength.greaterThan=" + DEFAULT_COURSE_LENGTH);

        // Get all the courseList where courseLength is greater than SMALLER_COURSE_LENGTH
        defaultCourseShouldBeFound("courseLength.greaterThan=" + SMALLER_COURSE_LENGTH);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo equals to DEFAULT_COURSE_LOGO
        defaultCourseShouldBeFound("courseLogo.equals=" + DEFAULT_COURSE_LOGO);

        // Get all the courseList where courseLogo equals to UPDATED_COURSE_LOGO
        defaultCourseShouldNotBeFound("courseLogo.equals=" + UPDATED_COURSE_LOGO);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo not equals to DEFAULT_COURSE_LOGO
        defaultCourseShouldNotBeFound("courseLogo.notEquals=" + DEFAULT_COURSE_LOGO);

        // Get all the courseList where courseLogo not equals to UPDATED_COURSE_LOGO
        defaultCourseShouldBeFound("courseLogo.notEquals=" + UPDATED_COURSE_LOGO);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo in DEFAULT_COURSE_LOGO or UPDATED_COURSE_LOGO
        defaultCourseShouldBeFound("courseLogo.in=" + DEFAULT_COURSE_LOGO + "," + UPDATED_COURSE_LOGO);

        // Get all the courseList where courseLogo equals to UPDATED_COURSE_LOGO
        defaultCourseShouldNotBeFound("courseLogo.in=" + UPDATED_COURSE_LOGO);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo is not null
        defaultCourseShouldBeFound("courseLogo.specified=true");

        // Get all the courseList where courseLogo is null
        defaultCourseShouldNotBeFound("courseLogo.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo contains DEFAULT_COURSE_LOGO
        defaultCourseShouldBeFound("courseLogo.contains=" + DEFAULT_COURSE_LOGO);

        // Get all the courseList where courseLogo contains UPDATED_COURSE_LOGO
        defaultCourseShouldNotBeFound("courseLogo.contains=" + UPDATED_COURSE_LOGO);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLogoNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseLogo does not contain DEFAULT_COURSE_LOGO
        defaultCourseShouldNotBeFound("courseLogo.doesNotContain=" + DEFAULT_COURSE_LOGO);

        // Get all the courseList where courseLogo does not contain UPDATED_COURSE_LOGO
        defaultCourseShouldBeFound("courseLogo.doesNotContain=" + UPDATED_COURSE_LOGO);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn equals to DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.equals=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn equals to UPDATED_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.equals=" + UPDATED_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn not equals to DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.notEquals=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn not equals to UPDATED_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.notEquals=" + UPDATED_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn in DEFAULT_COURSE_CREATED_ON or UPDATED_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.in=" + DEFAULT_COURSE_CREATED_ON + "," + UPDATED_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn equals to UPDATED_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.in=" + UPDATED_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn is not null
        defaultCourseShouldBeFound("courseCreatedOn.specified=true");

        // Get all the courseList where courseCreatedOn is null
        defaultCourseShouldNotBeFound("courseCreatedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn is greater than or equal to DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.greaterThanOrEqual=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn is greater than or equal to UPDATED_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.greaterThanOrEqual=" + UPDATED_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn is less than or equal to DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.lessThanOrEqual=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn is less than or equal to SMALLER_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.lessThanOrEqual=" + SMALLER_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn is less than DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.lessThan=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn is less than UPDATED_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.lessThan=" + UPDATED_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCreatedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseCreatedOn is greater than DEFAULT_COURSE_CREATED_ON
        defaultCourseShouldNotBeFound("courseCreatedOn.greaterThan=" + DEFAULT_COURSE_CREATED_ON);

        // Get all the courseList where courseCreatedOn is greater than SMALLER_COURSE_CREATED_ON
        defaultCourseShouldBeFound("courseCreatedOn.greaterThan=" + SMALLER_COURSE_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn equals to DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.equals=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn equals to UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.equals=" + UPDATED_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn not equals to DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.notEquals=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn not equals to UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.notEquals=" + UPDATED_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn in DEFAULT_COURSE_UPDATED_ON or UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.in=" + DEFAULT_COURSE_UPDATED_ON + "," + UPDATED_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn equals to UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.in=" + UPDATED_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn is not null
        defaultCourseShouldBeFound("courseUpdatedOn.specified=true");

        // Get all the courseList where courseUpdatedOn is null
        defaultCourseShouldNotBeFound("courseUpdatedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn is greater than or equal to DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.greaterThanOrEqual=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn is greater than or equal to UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.greaterThanOrEqual=" + UPDATED_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn is less than or equal to DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.lessThanOrEqual=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn is less than or equal to SMALLER_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.lessThanOrEqual=" + SMALLER_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn is less than DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.lessThan=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn is less than UPDATED_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.lessThan=" + UPDATED_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseUpdatedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseUpdatedOn is greater than DEFAULT_COURSE_UPDATED_ON
        defaultCourseShouldNotBeFound("courseUpdatedOn.greaterThan=" + DEFAULT_COURSE_UPDATED_ON);

        // Get all the courseList where courseUpdatedOn is greater than SMALLER_COURSE_UPDATED_ON
        defaultCourseShouldBeFound("courseUpdatedOn.greaterThan=" + SMALLER_COURSE_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir equals to DEFAULT_COURSE_ROOT_DIR
        defaultCourseShouldBeFound("courseRootDir.equals=" + DEFAULT_COURSE_ROOT_DIR);

        // Get all the courseList where courseRootDir equals to UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldNotBeFound("courseRootDir.equals=" + UPDATED_COURSE_ROOT_DIR);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir not equals to DEFAULT_COURSE_ROOT_DIR
        defaultCourseShouldNotBeFound("courseRootDir.notEquals=" + DEFAULT_COURSE_ROOT_DIR);

        // Get all the courseList where courseRootDir not equals to UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldBeFound("courseRootDir.notEquals=" + UPDATED_COURSE_ROOT_DIR);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir in DEFAULT_COURSE_ROOT_DIR or UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldBeFound("courseRootDir.in=" + DEFAULT_COURSE_ROOT_DIR + "," + UPDATED_COURSE_ROOT_DIR);

        // Get all the courseList where courseRootDir equals to UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldNotBeFound("courseRootDir.in=" + UPDATED_COURSE_ROOT_DIR);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir is not null
        defaultCourseShouldBeFound("courseRootDir.specified=true");

        // Get all the courseList where courseRootDir is null
        defaultCourseShouldNotBeFound("courseRootDir.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir contains DEFAULT_COURSE_ROOT_DIR
        defaultCourseShouldBeFound("courseRootDir.contains=" + DEFAULT_COURSE_ROOT_DIR);

        // Get all the courseList where courseRootDir contains UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldNotBeFound("courseRootDir.contains=" + UPDATED_COURSE_ROOT_DIR);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseRootDirNotContainsSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseRootDir does not contain DEFAULT_COURSE_ROOT_DIR
        defaultCourseShouldNotBeFound("courseRootDir.doesNotContain=" + DEFAULT_COURSE_ROOT_DIR);

        // Get all the courseList where courseRootDir does not contain UPDATED_COURSE_ROOT_DIR
        defaultCourseShouldBeFound("courseRootDir.doesNotContain=" + UPDATED_COURSE_ROOT_DIR);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount equals to DEFAULT_AMOUNT
        defaultCourseShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount equals to UPDATED_AMOUNT
        defaultCourseShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount not equals to DEFAULT_AMOUNT
        defaultCourseShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount not equals to UPDATED_AMOUNT
        defaultCourseShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCourseShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the courseList where amount equals to UPDATED_AMOUNT
        defaultCourseShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount is not null
        defaultCourseShouldBeFound("amount.specified=true");

        // Get all the courseList where amount is null
        defaultCourseShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCourseShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCourseShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCourseShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount is less than or equal to SMALLER_AMOUNT
        defaultCourseShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount is less than DEFAULT_AMOUNT
        defaultCourseShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount is less than UPDATED_AMOUNT
        defaultCourseShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where amount is greater than DEFAULT_AMOUNT
        defaultCourseShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the courseList where amount is greater than SMALLER_AMOUNT
        defaultCourseShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCoursesByIsDraftIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isDraft equals to DEFAULT_IS_DRAFT
        defaultCourseShouldBeFound("isDraft.equals=" + DEFAULT_IS_DRAFT);

        // Get all the courseList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseShouldNotBeFound("isDraft.equals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCoursesByIsDraftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isDraft not equals to DEFAULT_IS_DRAFT
        defaultCourseShouldNotBeFound("isDraft.notEquals=" + DEFAULT_IS_DRAFT);

        // Get all the courseList where isDraft not equals to UPDATED_IS_DRAFT
        defaultCourseShouldBeFound("isDraft.notEquals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCoursesByIsDraftIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isDraft in DEFAULT_IS_DRAFT or UPDATED_IS_DRAFT
        defaultCourseShouldBeFound("isDraft.in=" + DEFAULT_IS_DRAFT + "," + UPDATED_IS_DRAFT);

        // Get all the courseList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseShouldNotBeFound("isDraft.in=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCoursesByIsDraftIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isDraft is not null
        defaultCourseShouldBeFound("isDraft.specified=true");

        // Get all the courseList where isDraft is null
        defaultCourseShouldNotBeFound("isDraft.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isApproved equals to DEFAULT_IS_APPROVED
        defaultCourseShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the courseList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultCourseShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the courseList where isApproved not equals to UPDATED_IS_APPROVED
        defaultCourseShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultCourseShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the courseList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isApproved is not null
        defaultCourseShouldBeFound("isApproved.specified=true");

        // Get all the courseList where isApproved is null
        defaultCourseShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByIsPublishedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isPublished equals to DEFAULT_IS_PUBLISHED
        defaultCourseShouldBeFound("isPublished.equals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseShouldNotBeFound("isPublished.equals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsPublishedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isPublished not equals to DEFAULT_IS_PUBLISHED
        defaultCourseShouldNotBeFound("isPublished.notEquals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseList where isPublished not equals to UPDATED_IS_PUBLISHED
        defaultCourseShouldBeFound("isPublished.notEquals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsPublishedIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isPublished in DEFAULT_IS_PUBLISHED or UPDATED_IS_PUBLISHED
        defaultCourseShouldBeFound("isPublished.in=" + DEFAULT_IS_PUBLISHED + "," + UPDATED_IS_PUBLISHED);

        // Get all the courseList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseShouldNotBeFound("isPublished.in=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCoursesByIsPublishedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where isPublished is not null
        defaultCourseShouldBeFound("isPublished.specified=true");

        // Get all the courseList where isPublished is null
        defaultCourseShouldNotBeFound("isPublished.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate equals to DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.equals=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate equals to UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.equals=" + UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate not equals to DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.notEquals=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate not equals to UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.notEquals=" + UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate in DEFAULT_COURSE_APPROVAL_DATE or UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.in=" + DEFAULT_COURSE_APPROVAL_DATE + "," + UPDATED_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate equals to UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.in=" + UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate is not null
        defaultCourseShouldBeFound("courseApprovalDate.specified=true");

        // Get all the courseList where courseApprovalDate is null
        defaultCourseShouldNotBeFound("courseApprovalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate is greater than or equal to DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.greaterThanOrEqual=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate is greater than or equal to UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.greaterThanOrEqual=" + UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate is less than or equal to DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.lessThanOrEqual=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate is less than or equal to SMALLER_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.lessThanOrEqual=" + SMALLER_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate is less than DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.lessThan=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate is less than UPDATED_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.lessThan=" + UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseApprovalDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList where courseApprovalDate is greater than DEFAULT_COURSE_APPROVAL_DATE
        defaultCourseShouldNotBeFound("courseApprovalDate.greaterThan=" + DEFAULT_COURSE_APPROVAL_DATE);

        // Get all the courseList where courseApprovalDate is greater than SMALLER_COURSE_APPROVAL_DATE
        defaultCourseShouldBeFound("courseApprovalDate.greaterThan=" + SMALLER_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllCoursesByCourseLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        CourseLevel courseLevel;
        if (TestUtil.findAll(em, CourseLevel.class).isEmpty()) {
            courseLevel = CourseLevelResourceIT.createEntity(em);
            em.persist(courseLevel);
            em.flush();
        } else {
            courseLevel = TestUtil.findAll(em, CourseLevel.class).get(0);
        }
        em.persist(courseLevel);
        em.flush();
        course.setCourseLevel(courseLevel);
        courseRepository.saveAndFlush(course);
        Long courseLevelId = courseLevel.getId();

        // Get all the courseList where courseLevel equals to courseLevelId
        defaultCourseShouldBeFound("courseLevelId.equals=" + courseLevelId);

        // Get all the courseList where courseLevel equals to (courseLevelId + 1)
        defaultCourseShouldNotBeFound("courseLevelId.equals=" + (courseLevelId + 1));
    }

    @Test
    @Transactional
    void getAllCoursesByCourseCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        CourseCategory courseCategory;
        if (TestUtil.findAll(em, CourseCategory.class).isEmpty()) {
            courseCategory = CourseCategoryResourceIT.createEntity(em);
            em.persist(courseCategory);
            em.flush();
        } else {
            courseCategory = TestUtil.findAll(em, CourseCategory.class).get(0);
        }
        em.persist(courseCategory);
        em.flush();
        course.setCourseCategory(courseCategory);
        courseRepository.saveAndFlush(course);
        Long courseCategoryId = courseCategory.getId();

        // Get all the courseList where courseCategory equals to courseCategoryId
        defaultCourseShouldBeFound("courseCategoryId.equals=" + courseCategoryId);

        // Get all the courseList where courseCategory equals to (courseCategoryId + 1)
        defaultCourseShouldNotBeFound("courseCategoryId.equals=" + (courseCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCoursesByCourseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        CourseType courseType;
        if (TestUtil.findAll(em, CourseType.class).isEmpty()) {
            courseType = CourseTypeResourceIT.createEntity(em);
            em.persist(courseType);
            em.flush();
        } else {
            courseType = TestUtil.findAll(em, CourseType.class).get(0);
        }
        em.persist(courseType);
        em.flush();
        course.setCourseType(courseType);
        courseRepository.saveAndFlush(course);
        Long courseTypeId = courseType.getId();

        // Get all the courseList where courseType equals to courseTypeId
        defaultCourseShouldBeFound("courseTypeId.equals=" + courseTypeId);

        // Get all the courseList where courseType equals to (courseTypeId + 1)
        defaultCourseShouldNotBeFound("courseTypeId.equals=" + (courseTypeId + 1));
    }

    @Test
    @Transactional
    void getAllCoursesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            user = UserResourceIT.createEntity(em);
            em.persist(user);
            em.flush();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        course.setUser(user);
        courseRepository.saveAndFlush(course);
        Long userId = user.getId();

        // Get all the courseList where user equals to userId
        defaultCourseShouldBeFound("userId.equals=" + userId);

        // Get all the courseList where user equals to (userId + 1)
        defaultCourseShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseShouldBeFound(String filter) throws Exception {
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseTitle").value(hasItem(DEFAULT_COURSE_TITLE)))
            .andExpect(jsonPath("$.[*].courseDescription").value(hasItem(DEFAULT_COURSE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].courseObjectives").value(hasItem(DEFAULT_COURSE_OBJECTIVES)))
            .andExpect(jsonPath("$.[*].courseSubTitle").value(hasItem(DEFAULT_COURSE_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].coursePreviewURL").value(hasItem(DEFAULT_COURSE_PREVIEW_URL)))
            .andExpect(jsonPath("$.[*].courseLength").value(hasItem(DEFAULT_COURSE_LENGTH)))
            .andExpect(jsonPath("$.[*].courseLogo").value(hasItem(DEFAULT_COURSE_LOGO)))
            .andExpect(jsonPath("$.[*].courseCreatedOn").value(hasItem(DEFAULT_COURSE_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].courseUpdatedOn").value(hasItem(DEFAULT_COURSE_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].courseRootDir").value(hasItem(DEFAULT_COURSE_ROOT_DIR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].courseApprovalDate").value(hasItem(DEFAULT_COURSE_APPROVAL_DATE.toString())));

        // Check, that the count call also returns 1
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseShouldNotBeFound(String filter) throws Exception {
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findById(course.getId()).get();
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .courseTitle(UPDATED_COURSE_TITLE)
            .courseDescription(UPDATED_COURSE_DESCRIPTION)
            .courseObjectives(UPDATED_COURSE_OBJECTIVES)
            .courseSubTitle(UPDATED_COURSE_SUB_TITLE)
            .coursePreviewURL(UPDATED_COURSE_PREVIEW_URL)
            .courseLength(UPDATED_COURSE_LENGTH)
            .courseLogo(UPDATED_COURSE_LOGO)
            .courseCreatedOn(UPDATED_COURSE_CREATED_ON)
            .courseUpdatedOn(UPDATED_COURSE_UPDATED_ON)
            .courseRootDir(UPDATED_COURSE_ROOT_DIR)
            .amount(UPDATED_AMOUNT)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .courseApprovalDate(UPDATED_COURSE_APPROVAL_DATE);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseTitle()).isEqualTo(UPDATED_COURSE_TITLE);
        assertThat(testCourse.getCourseDescription()).isEqualTo(UPDATED_COURSE_DESCRIPTION);
        assertThat(testCourse.getCourseObjectives()).isEqualTo(UPDATED_COURSE_OBJECTIVES);
        assertThat(testCourse.getCourseSubTitle()).isEqualTo(UPDATED_COURSE_SUB_TITLE);
        assertThat(testCourse.getCoursePreviewURL()).isEqualTo(UPDATED_COURSE_PREVIEW_URL);
        assertThat(testCourse.getCourseLength()).isEqualTo(UPDATED_COURSE_LENGTH);
        assertThat(testCourse.getCourseLogo()).isEqualTo(UPDATED_COURSE_LOGO);
        assertThat(testCourse.getCourseCreatedOn()).isEqualTo(UPDATED_COURSE_CREATED_ON);
        assertThat(testCourse.getCourseUpdatedOn()).isEqualTo(UPDATED_COURSE_UPDATED_ON);
        assertThat(testCourse.getCourseRootDir()).isEqualTo(UPDATED_COURSE_ROOT_DIR);
        assertThat(testCourse.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCourse.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourse.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourse.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testCourse.getCourseApprovalDate()).isEqualTo(UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseWithPatch() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course using partial update
        Course partialUpdatedCourse = new Course();
        partialUpdatedCourse.setId(course.getId());

        partialUpdatedCourse
            .courseObjectives(UPDATED_COURSE_OBJECTIVES)
            .coursePreviewURL(UPDATED_COURSE_PREVIEW_URL)
            .courseLength(UPDATED_COURSE_LENGTH)
            .courseCreatedOn(UPDATED_COURSE_CREATED_ON)
            .amount(UPDATED_AMOUNT)
            .isDraft(UPDATED_IS_DRAFT)
            .isPublished(UPDATED_IS_PUBLISHED);

        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourse))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseTitle()).isEqualTo(DEFAULT_COURSE_TITLE);
        assertThat(testCourse.getCourseDescription()).isEqualTo(DEFAULT_COURSE_DESCRIPTION);
        assertThat(testCourse.getCourseObjectives()).isEqualTo(UPDATED_COURSE_OBJECTIVES);
        assertThat(testCourse.getCourseSubTitle()).isEqualTo(DEFAULT_COURSE_SUB_TITLE);
        assertThat(testCourse.getCoursePreviewURL()).isEqualTo(UPDATED_COURSE_PREVIEW_URL);
        assertThat(testCourse.getCourseLength()).isEqualTo(UPDATED_COURSE_LENGTH);
        assertThat(testCourse.getCourseLogo()).isEqualTo(DEFAULT_COURSE_LOGO);
        assertThat(testCourse.getCourseCreatedOn()).isEqualTo(UPDATED_COURSE_CREATED_ON);
        assertThat(testCourse.getCourseUpdatedOn()).isEqualTo(DEFAULT_COURSE_UPDATED_ON);
        assertThat(testCourse.getCourseRootDir()).isEqualTo(DEFAULT_COURSE_ROOT_DIR);
        assertThat(testCourse.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCourse.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourse.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testCourse.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testCourse.getCourseApprovalDate()).isEqualTo(DEFAULT_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCourseWithPatch() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course using partial update
        Course partialUpdatedCourse = new Course();
        partialUpdatedCourse.setId(course.getId());

        partialUpdatedCourse
            .courseTitle(UPDATED_COURSE_TITLE)
            .courseDescription(UPDATED_COURSE_DESCRIPTION)
            .courseObjectives(UPDATED_COURSE_OBJECTIVES)
            .courseSubTitle(UPDATED_COURSE_SUB_TITLE)
            .coursePreviewURL(UPDATED_COURSE_PREVIEW_URL)
            .courseLength(UPDATED_COURSE_LENGTH)
            .courseLogo(UPDATED_COURSE_LOGO)
            .courseCreatedOn(UPDATED_COURSE_CREATED_ON)
            .courseUpdatedOn(UPDATED_COURSE_UPDATED_ON)
            .courseRootDir(UPDATED_COURSE_ROOT_DIR)
            .amount(UPDATED_AMOUNT)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .courseApprovalDate(UPDATED_COURSE_APPROVAL_DATE);

        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourse))
            )
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseTitle()).isEqualTo(UPDATED_COURSE_TITLE);
        assertThat(testCourse.getCourseDescription()).isEqualTo(UPDATED_COURSE_DESCRIPTION);
        assertThat(testCourse.getCourseObjectives()).isEqualTo(UPDATED_COURSE_OBJECTIVES);
        assertThat(testCourse.getCourseSubTitle()).isEqualTo(UPDATED_COURSE_SUB_TITLE);
        assertThat(testCourse.getCoursePreviewURL()).isEqualTo(UPDATED_COURSE_PREVIEW_URL);
        assertThat(testCourse.getCourseLength()).isEqualTo(UPDATED_COURSE_LENGTH);
        assertThat(testCourse.getCourseLogo()).isEqualTo(UPDATED_COURSE_LOGO);
        assertThat(testCourse.getCourseCreatedOn()).isEqualTo(UPDATED_COURSE_CREATED_ON);
        assertThat(testCourse.getCourseUpdatedOn()).isEqualTo(UPDATED_COURSE_UPDATED_ON);
        assertThat(testCourse.getCourseRootDir()).isEqualTo(UPDATED_COURSE_ROOT_DIR);
        assertThat(testCourse.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCourse.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourse.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourse.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testCourse.getCourseApprovalDate()).isEqualTo(UPDATED_COURSE_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Delete the course
        restCourseMockMvc
            .perform(delete(ENTITY_API_URL_ID, course.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
