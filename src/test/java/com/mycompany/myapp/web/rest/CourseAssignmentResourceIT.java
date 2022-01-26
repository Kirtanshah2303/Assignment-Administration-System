package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.domain.CourseAssignmentInput;
import com.mycompany.myapp.domain.CourseAssignmentOutput;
import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.repository.CourseAssignmentRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CourseAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseAssignmentResourceIT {

    private static final String DEFAULT_ASSIGNMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_VIDEO = "BBBBBBBBBB";

    private static final Instant DEFAULT_SESSION_DURATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SESSION_DURATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ASSIGNMENT_ORDER = 1;
    private static final Integer UPDATED_ASSIGNMENT_ORDER = 2;
    private static final Integer SMALLER_ASSIGNMENT_ORDER = 1 - 1;

    private static final String DEFAULT_ASSIGNMENT_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNMENT_RESOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PREVIEW = false;
    private static final Boolean UPDATED_IS_PREVIEW = true;

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;

    private static final String ENTITY_API_URL = "/api/course-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseAssignmentRepository courseAssignmentRepository;

    @Autowired
    private CourseAssignmentMapper courseAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseAssignmentMockMvc;

    private CourseAssignment courseAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignment createEntity(EntityManager em) {
        CourseAssignment courseAssignment = new CourseAssignment()
            .assignmentTitle(DEFAULT_ASSIGNMENT_TITLE)
            .assignmentDescription(DEFAULT_ASSIGNMENT_DESCRIPTION)
            .sessionVideo(DEFAULT_SESSION_VIDEO)
            .sessionDuration(DEFAULT_SESSION_DURATION)
            .assignmentOrder(DEFAULT_ASSIGNMENT_ORDER)
            .assignmentResource(DEFAULT_ASSIGNMENT_RESOURCE)
            .isPreview(DEFAULT_IS_PREVIEW)
            .isDraft(DEFAULT_IS_DRAFT)
            .isApproved(DEFAULT_IS_APPROVED)
            .isPublished(DEFAULT_IS_PUBLISHED);
        return courseAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignment createUpdatedEntity(EntityManager em) {
        CourseAssignment courseAssignment = new CourseAssignment()
            .assignmentTitle(UPDATED_ASSIGNMENT_TITLE)
            .assignmentDescription(UPDATED_ASSIGNMENT_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .assignmentOrder(UPDATED_ASSIGNMENT_ORDER)
            .assignmentResource(UPDATED_ASSIGNMENT_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED);
        return courseAssignment;
    }

    @BeforeEach
    public void initTest() {
        courseAssignment = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseAssignment() throws Exception {
        int databaseSizeBeforeCreate = courseAssignmentRepository.findAll().size();
        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);
        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        CourseAssignment testCourseAssignment = courseAssignmentList.get(courseAssignmentList.size() - 1);
        assertThat(testCourseAssignment.getAssignmentTitle()).isEqualTo(DEFAULT_ASSIGNMENT_TITLE);
        assertThat(testCourseAssignment.getAssignmentDescription()).isEqualTo(DEFAULT_ASSIGNMENT_DESCRIPTION);
        assertThat(testCourseAssignment.getSessionVideo()).isEqualTo(DEFAULT_SESSION_VIDEO);
        assertThat(testCourseAssignment.getSessionDuration()).isEqualTo(DEFAULT_SESSION_DURATION);
        assertThat(testCourseAssignment.getAssignmentOrder()).isEqualTo(DEFAULT_ASSIGNMENT_ORDER);
        assertThat(testCourseAssignment.getAssignmentResource()).isEqualTo(DEFAULT_ASSIGNMENT_RESOURCE);
        assertThat(testCourseAssignment.getIsPreview()).isEqualTo(DEFAULT_IS_PREVIEW);
        assertThat(testCourseAssignment.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseAssignment.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testCourseAssignment.getIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void createCourseAssignmentWithExistingId() throws Exception {
        // Create the CourseAssignment with an existing ID
        courseAssignment.setId(1L);
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        int databaseSizeBeforeCreate = courseAssignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssignmentTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setAssignmentTitle(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionVideoIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setSessionVideo(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setSessionDuration(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssignmentOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setAssignmentOrder(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPreviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setIsPreview(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setIsDraft(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setIsApproved(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPublishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentRepository.findAll().size();
        // set the field null
        courseAssignment.setIsPublished(null);

        // Create the CourseAssignment, which fails.
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseAssignments() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignmentTitle").value(hasItem(DEFAULT_ASSIGNMENT_TITLE)))
            .andExpect(jsonPath("$.[*].assignmentDescription").value(hasItem(DEFAULT_ASSIGNMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sessionVideo").value(hasItem(DEFAULT_SESSION_VIDEO)))
            .andExpect(jsonPath("$.[*].sessionDuration").value(hasItem(DEFAULT_SESSION_DURATION.toString())))
            .andExpect(jsonPath("$.[*].assignmentOrder").value(hasItem(DEFAULT_ASSIGNMENT_ORDER)))
            .andExpect(jsonPath("$.[*].assignmentResource").value(hasItem(DEFAULT_ASSIGNMENT_RESOURCE)))
            .andExpect(jsonPath("$.[*].isPreview").value(hasItem(DEFAULT_IS_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())));
    }

    @Test
    @Transactional
    void getCourseAssignment() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get the courseAssignment
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, courseAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseAssignment.getId().intValue()))
            .andExpect(jsonPath("$.assignmentTitle").value(DEFAULT_ASSIGNMENT_TITLE))
            .andExpect(jsonPath("$.assignmentDescription").value(DEFAULT_ASSIGNMENT_DESCRIPTION))
            .andExpect(jsonPath("$.sessionVideo").value(DEFAULT_SESSION_VIDEO))
            .andExpect(jsonPath("$.sessionDuration").value(DEFAULT_SESSION_DURATION.toString()))
            .andExpect(jsonPath("$.assignmentOrder").value(DEFAULT_ASSIGNMENT_ORDER))
            .andExpect(jsonPath("$.assignmentResource").value(DEFAULT_ASSIGNMENT_RESOURCE))
            .andExpect(jsonPath("$.isPreview").value(DEFAULT_IS_PREVIEW.booleanValue()))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()));
    }

    @Test
    @Transactional
    void getCourseAssignmentsByIdFiltering() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        Long id = courseAssignment.getId();

        defaultCourseAssignmentShouldBeFound("id.equals=" + id);
        defaultCourseAssignmentShouldNotBeFound("id.notEquals=" + id);

        defaultCourseAssignmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseAssignmentShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseAssignmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseAssignmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle equals to DEFAULT_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldBeFound("assignmentTitle.equals=" + DEFAULT_ASSIGNMENT_TITLE);

        // Get all the courseAssignmentList where assignmentTitle equals to UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.equals=" + UPDATED_ASSIGNMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle not equals to DEFAULT_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.notEquals=" + DEFAULT_ASSIGNMENT_TITLE);

        // Get all the courseAssignmentList where assignmentTitle not equals to UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldBeFound("assignmentTitle.notEquals=" + UPDATED_ASSIGNMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle in DEFAULT_ASSIGNMENT_TITLE or UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldBeFound("assignmentTitle.in=" + DEFAULT_ASSIGNMENT_TITLE + "," + UPDATED_ASSIGNMENT_TITLE);

        // Get all the courseAssignmentList where assignmentTitle equals to UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.in=" + UPDATED_ASSIGNMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle is not null
        defaultCourseAssignmentShouldBeFound("assignmentTitle.specified=true");

        // Get all the courseAssignmentList where assignmentTitle is null
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle contains DEFAULT_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldBeFound("assignmentTitle.contains=" + DEFAULT_ASSIGNMENT_TITLE);

        // Get all the courseAssignmentList where assignmentTitle contains UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.contains=" + UPDATED_ASSIGNMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentTitle does not contain DEFAULT_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldNotBeFound("assignmentTitle.doesNotContain=" + DEFAULT_ASSIGNMENT_TITLE);

        // Get all the courseAssignmentList where assignmentTitle does not contain UPDATED_ASSIGNMENT_TITLE
        defaultCourseAssignmentShouldBeFound("assignmentTitle.doesNotContain=" + UPDATED_ASSIGNMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription equals to DEFAULT_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldBeFound("assignmentDescription.equals=" + DEFAULT_ASSIGNMENT_DESCRIPTION);

        // Get all the courseAssignmentList where assignmentDescription equals to UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.equals=" + UPDATED_ASSIGNMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription not equals to DEFAULT_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.notEquals=" + DEFAULT_ASSIGNMENT_DESCRIPTION);

        // Get all the courseAssignmentList where assignmentDescription not equals to UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldBeFound("assignmentDescription.notEquals=" + UPDATED_ASSIGNMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription in DEFAULT_ASSIGNMENT_DESCRIPTION or UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldBeFound(
            "assignmentDescription.in=" + DEFAULT_ASSIGNMENT_DESCRIPTION + "," + UPDATED_ASSIGNMENT_DESCRIPTION
        );

        // Get all the courseAssignmentList where assignmentDescription equals to UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.in=" + UPDATED_ASSIGNMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription is not null
        defaultCourseAssignmentShouldBeFound("assignmentDescription.specified=true");

        // Get all the courseAssignmentList where assignmentDescription is null
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription contains DEFAULT_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldBeFound("assignmentDescription.contains=" + DEFAULT_ASSIGNMENT_DESCRIPTION);

        // Get all the courseAssignmentList where assignmentDescription contains UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.contains=" + UPDATED_ASSIGNMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentDescription does not contain DEFAULT_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldNotBeFound("assignmentDescription.doesNotContain=" + DEFAULT_ASSIGNMENT_DESCRIPTION);

        // Get all the courseAssignmentList where assignmentDescription does not contain UPDATED_ASSIGNMENT_DESCRIPTION
        defaultCourseAssignmentShouldBeFound("assignmentDescription.doesNotContain=" + UPDATED_ASSIGNMENT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo equals to DEFAULT_SESSION_VIDEO
        defaultCourseAssignmentShouldBeFound("sessionVideo.equals=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseAssignmentList where sessionVideo equals to UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.equals=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo not equals to DEFAULT_SESSION_VIDEO
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.notEquals=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseAssignmentList where sessionVideo not equals to UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldBeFound("sessionVideo.notEquals=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo in DEFAULT_SESSION_VIDEO or UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldBeFound("sessionVideo.in=" + DEFAULT_SESSION_VIDEO + "," + UPDATED_SESSION_VIDEO);

        // Get all the courseAssignmentList where sessionVideo equals to UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.in=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo is not null
        defaultCourseAssignmentShouldBeFound("sessionVideo.specified=true");

        // Get all the courseAssignmentList where sessionVideo is null
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo contains DEFAULT_SESSION_VIDEO
        defaultCourseAssignmentShouldBeFound("sessionVideo.contains=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseAssignmentList where sessionVideo contains UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.contains=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionVideoNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionVideo does not contain DEFAULT_SESSION_VIDEO
        defaultCourseAssignmentShouldNotBeFound("sessionVideo.doesNotContain=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseAssignmentList where sessionVideo does not contain UPDATED_SESSION_VIDEO
        defaultCourseAssignmentShouldBeFound("sessionVideo.doesNotContain=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionDuration equals to DEFAULT_SESSION_DURATION
        defaultCourseAssignmentShouldBeFound("sessionDuration.equals=" + DEFAULT_SESSION_DURATION);

        // Get all the courseAssignmentList where sessionDuration equals to UPDATED_SESSION_DURATION
        defaultCourseAssignmentShouldNotBeFound("sessionDuration.equals=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionDuration not equals to DEFAULT_SESSION_DURATION
        defaultCourseAssignmentShouldNotBeFound("sessionDuration.notEquals=" + DEFAULT_SESSION_DURATION);

        // Get all the courseAssignmentList where sessionDuration not equals to UPDATED_SESSION_DURATION
        defaultCourseAssignmentShouldBeFound("sessionDuration.notEquals=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionDurationIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionDuration in DEFAULT_SESSION_DURATION or UPDATED_SESSION_DURATION
        defaultCourseAssignmentShouldBeFound("sessionDuration.in=" + DEFAULT_SESSION_DURATION + "," + UPDATED_SESSION_DURATION);

        // Get all the courseAssignmentList where sessionDuration equals to UPDATED_SESSION_DURATION
        defaultCourseAssignmentShouldNotBeFound("sessionDuration.in=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsBySessionDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where sessionDuration is not null
        defaultCourseAssignmentShouldBeFound("sessionDuration.specified=true");

        // Get all the courseAssignmentList where sessionDuration is null
        defaultCourseAssignmentShouldNotBeFound("sessionDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder equals to DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.equals=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder equals to UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.equals=" + UPDATED_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder not equals to DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.notEquals=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder not equals to UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.notEquals=" + UPDATED_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder in DEFAULT_ASSIGNMENT_ORDER or UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.in=" + DEFAULT_ASSIGNMENT_ORDER + "," + UPDATED_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder equals to UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.in=" + UPDATED_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder is not null
        defaultCourseAssignmentShouldBeFound("assignmentOrder.specified=true");

        // Get all the courseAssignmentList where assignmentOrder is null
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder is greater than or equal to DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.greaterThanOrEqual=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder is greater than or equal to UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.greaterThanOrEqual=" + UPDATED_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder is less than or equal to DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.lessThanOrEqual=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder is less than or equal to SMALLER_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.lessThanOrEqual=" + SMALLER_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder is less than DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.lessThan=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder is less than UPDATED_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.lessThan=" + UPDATED_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentOrder is greater than DEFAULT_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldNotBeFound("assignmentOrder.greaterThan=" + DEFAULT_ASSIGNMENT_ORDER);

        // Get all the courseAssignmentList where assignmentOrder is greater than SMALLER_ASSIGNMENT_ORDER
        defaultCourseAssignmentShouldBeFound("assignmentOrder.greaterThan=" + SMALLER_ASSIGNMENT_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource equals to DEFAULT_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldBeFound("assignmentResource.equals=" + DEFAULT_ASSIGNMENT_RESOURCE);

        // Get all the courseAssignmentList where assignmentResource equals to UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.equals=" + UPDATED_ASSIGNMENT_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource not equals to DEFAULT_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.notEquals=" + DEFAULT_ASSIGNMENT_RESOURCE);

        // Get all the courseAssignmentList where assignmentResource not equals to UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldBeFound("assignmentResource.notEquals=" + UPDATED_ASSIGNMENT_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource in DEFAULT_ASSIGNMENT_RESOURCE or UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldBeFound("assignmentResource.in=" + DEFAULT_ASSIGNMENT_RESOURCE + "," + UPDATED_ASSIGNMENT_RESOURCE);

        // Get all the courseAssignmentList where assignmentResource equals to UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.in=" + UPDATED_ASSIGNMENT_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource is not null
        defaultCourseAssignmentShouldBeFound("assignmentResource.specified=true");

        // Get all the courseAssignmentList where assignmentResource is null
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource contains DEFAULT_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldBeFound("assignmentResource.contains=" + DEFAULT_ASSIGNMENT_RESOURCE);

        // Get all the courseAssignmentList where assignmentResource contains UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.contains=" + UPDATED_ASSIGNMENT_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByAssignmentResourceNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where assignmentResource does not contain DEFAULT_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldNotBeFound("assignmentResource.doesNotContain=" + DEFAULT_ASSIGNMENT_RESOURCE);

        // Get all the courseAssignmentList where assignmentResource does not contain UPDATED_ASSIGNMENT_RESOURCE
        defaultCourseAssignmentShouldBeFound("assignmentResource.doesNotContain=" + UPDATED_ASSIGNMENT_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPreviewIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPreview equals to DEFAULT_IS_PREVIEW
        defaultCourseAssignmentShouldBeFound("isPreview.equals=" + DEFAULT_IS_PREVIEW);

        // Get all the courseAssignmentList where isPreview equals to UPDATED_IS_PREVIEW
        defaultCourseAssignmentShouldNotBeFound("isPreview.equals=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPreviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPreview not equals to DEFAULT_IS_PREVIEW
        defaultCourseAssignmentShouldNotBeFound("isPreview.notEquals=" + DEFAULT_IS_PREVIEW);

        // Get all the courseAssignmentList where isPreview not equals to UPDATED_IS_PREVIEW
        defaultCourseAssignmentShouldBeFound("isPreview.notEquals=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPreviewIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPreview in DEFAULT_IS_PREVIEW or UPDATED_IS_PREVIEW
        defaultCourseAssignmentShouldBeFound("isPreview.in=" + DEFAULT_IS_PREVIEW + "," + UPDATED_IS_PREVIEW);

        // Get all the courseAssignmentList where isPreview equals to UPDATED_IS_PREVIEW
        defaultCourseAssignmentShouldNotBeFound("isPreview.in=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPreviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPreview is not null
        defaultCourseAssignmentShouldBeFound("isPreview.specified=true");

        // Get all the courseAssignmentList where isPreview is null
        defaultCourseAssignmentShouldNotBeFound("isPreview.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsDraftIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isDraft equals to DEFAULT_IS_DRAFT
        defaultCourseAssignmentShouldBeFound("isDraft.equals=" + DEFAULT_IS_DRAFT);

        // Get all the courseAssignmentList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseAssignmentShouldNotBeFound("isDraft.equals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsDraftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isDraft not equals to DEFAULT_IS_DRAFT
        defaultCourseAssignmentShouldNotBeFound("isDraft.notEquals=" + DEFAULT_IS_DRAFT);

        // Get all the courseAssignmentList where isDraft not equals to UPDATED_IS_DRAFT
        defaultCourseAssignmentShouldBeFound("isDraft.notEquals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsDraftIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isDraft in DEFAULT_IS_DRAFT or UPDATED_IS_DRAFT
        defaultCourseAssignmentShouldBeFound("isDraft.in=" + DEFAULT_IS_DRAFT + "," + UPDATED_IS_DRAFT);

        // Get all the courseAssignmentList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseAssignmentShouldNotBeFound("isDraft.in=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsDraftIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isDraft is not null
        defaultCourseAssignmentShouldBeFound("isDraft.specified=true");

        // Get all the courseAssignmentList where isDraft is null
        defaultCourseAssignmentShouldNotBeFound("isDraft.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isApproved equals to DEFAULT_IS_APPROVED
        defaultCourseAssignmentShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the courseAssignmentList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseAssignmentShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultCourseAssignmentShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the courseAssignmentList where isApproved not equals to UPDATED_IS_APPROVED
        defaultCourseAssignmentShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultCourseAssignmentShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the courseAssignmentList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseAssignmentShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isApproved is not null
        defaultCourseAssignmentShouldBeFound("isApproved.specified=true");

        // Get all the courseAssignmentList where isApproved is null
        defaultCourseAssignmentShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPublishedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPublished equals to DEFAULT_IS_PUBLISHED
        defaultCourseAssignmentShouldBeFound("isPublished.equals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseAssignmentList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseAssignmentShouldNotBeFound("isPublished.equals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPublishedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPublished not equals to DEFAULT_IS_PUBLISHED
        defaultCourseAssignmentShouldNotBeFound("isPublished.notEquals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseAssignmentList where isPublished not equals to UPDATED_IS_PUBLISHED
        defaultCourseAssignmentShouldBeFound("isPublished.notEquals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPublishedIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPublished in DEFAULT_IS_PUBLISHED or UPDATED_IS_PUBLISHED
        defaultCourseAssignmentShouldBeFound("isPublished.in=" + DEFAULT_IS_PUBLISHED + "," + UPDATED_IS_PUBLISHED);

        // Get all the courseAssignmentList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseAssignmentShouldNotBeFound("isPublished.in=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByIsPublishedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        // Get all the courseAssignmentList where isPublished is not null
        defaultCourseAssignmentShouldBeFound("isPublished.specified=true");

        // Get all the courseAssignmentList where isPublished is null
        defaultCourseAssignmentShouldNotBeFound("isPublished.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByCourseSectionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        CourseSection courseSection;
        if (TestUtil.findAll(em, CourseSection.class).isEmpty()) {
            courseSection = CourseSectionResourceIT.createEntity(em);
            em.persist(courseSection);
            em.flush();
        } else {
            courseSection = TestUtil.findAll(em, CourseSection.class).get(0);
        }
        em.persist(courseSection);
        em.flush();
        courseAssignment.setCourseSection(courseSection);
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        Long courseSectionId = courseSection.getId();

        // Get all the courseAssignmentList where courseSection equals to courseSectionId
        defaultCourseAssignmentShouldBeFound("courseSectionId.equals=" + courseSectionId);

        // Get all the courseAssignmentList where courseSection equals to (courseSectionId + 1)
        defaultCourseAssignmentShouldNotBeFound("courseSectionId.equals=" + (courseSectionId + 1));
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByCourseAssignmentInputIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        CourseAssignmentInput courseAssignmentInput;
        if (TestUtil.findAll(em, CourseAssignmentInput.class).isEmpty()) {
            courseAssignmentInput = CourseAssignmentInputResourceIT.createEntity(em);
            em.persist(courseAssignmentInput);
            em.flush();
        } else {
            courseAssignmentInput = TestUtil.findAll(em, CourseAssignmentInput.class).get(0);
        }
        em.persist(courseAssignmentInput);
        em.flush();
        courseAssignment.addCourseAssignmentInput(courseAssignmentInput);
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        Long courseAssignmentInputId = courseAssignmentInput.getId();

        // Get all the courseAssignmentList where courseAssignmentInput equals to courseAssignmentInputId
        defaultCourseAssignmentShouldBeFound("courseAssignmentInputId.equals=" + courseAssignmentInputId);

        // Get all the courseAssignmentList where courseAssignmentInput equals to (courseAssignmentInputId + 1)
        defaultCourseAssignmentShouldNotBeFound("courseAssignmentInputId.equals=" + (courseAssignmentInputId + 1));
    }

    @Test
    @Transactional
    void getAllCourseAssignmentsByCourseAssignmentOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        CourseAssignmentOutput courseAssignmentOutput;
        if (TestUtil.findAll(em, CourseAssignmentOutput.class).isEmpty()) {
            courseAssignmentOutput = CourseAssignmentOutputResourceIT.createEntity(em);
            em.persist(courseAssignmentOutput);
            em.flush();
        } else {
            courseAssignmentOutput = TestUtil.findAll(em, CourseAssignmentOutput.class).get(0);
        }
        em.persist(courseAssignmentOutput);
        em.flush();
        courseAssignment.addCourseAssignmentOutput(courseAssignmentOutput);
        courseAssignmentRepository.saveAndFlush(courseAssignment);
        Long courseAssignmentOutputId = courseAssignmentOutput.getId();

        // Get all the courseAssignmentList where courseAssignmentOutput equals to courseAssignmentOutputId
        defaultCourseAssignmentShouldBeFound("courseAssignmentOutputId.equals=" + courseAssignmentOutputId);

        // Get all the courseAssignmentList where courseAssignmentOutput equals to (courseAssignmentOutputId + 1)
        defaultCourseAssignmentShouldNotBeFound("courseAssignmentOutputId.equals=" + (courseAssignmentOutputId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseAssignmentShouldBeFound(String filter) throws Exception {
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignmentTitle").value(hasItem(DEFAULT_ASSIGNMENT_TITLE)))
            .andExpect(jsonPath("$.[*].assignmentDescription").value(hasItem(DEFAULT_ASSIGNMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sessionVideo").value(hasItem(DEFAULT_SESSION_VIDEO)))
            .andExpect(jsonPath("$.[*].sessionDuration").value(hasItem(DEFAULT_SESSION_DURATION.toString())))
            .andExpect(jsonPath("$.[*].assignmentOrder").value(hasItem(DEFAULT_ASSIGNMENT_ORDER)))
            .andExpect(jsonPath("$.[*].assignmentResource").value(hasItem(DEFAULT_ASSIGNMENT_RESOURCE)))
            .andExpect(jsonPath("$.[*].isPreview").value(hasItem(DEFAULT_IS_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())));

        // Check, that the count call also returns 1
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseAssignmentShouldNotBeFound(String filter) throws Exception {
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseAssignment() throws Exception {
        // Get the courseAssignment
        restCourseAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseAssignment() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();

        // Update the courseAssignment
        CourseAssignment updatedCourseAssignment = courseAssignmentRepository.findById(courseAssignment.getId()).get();
        // Disconnect from session so that the updates on updatedCourseAssignment are not directly saved in db
        em.detach(updatedCourseAssignment);
        updatedCourseAssignment
            .assignmentTitle(UPDATED_ASSIGNMENT_TITLE)
            .assignmentDescription(UPDATED_ASSIGNMENT_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .assignmentOrder(UPDATED_ASSIGNMENT_ORDER)
            .assignmentResource(UPDATED_ASSIGNMENT_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED);
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(updatedCourseAssignment);

        restCourseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignment testCourseAssignment = courseAssignmentList.get(courseAssignmentList.size() - 1);
        assertThat(testCourseAssignment.getAssignmentTitle()).isEqualTo(UPDATED_ASSIGNMENT_TITLE);
        assertThat(testCourseAssignment.getAssignmentDescription()).isEqualTo(UPDATED_ASSIGNMENT_DESCRIPTION);
        assertThat(testCourseAssignment.getSessionVideo()).isEqualTo(UPDATED_SESSION_VIDEO);
        assertThat(testCourseAssignment.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseAssignment.getAssignmentOrder()).isEqualTo(UPDATED_ASSIGNMENT_ORDER);
        assertThat(testCourseAssignment.getAssignmentResource()).isEqualTo(UPDATED_ASSIGNMENT_RESOURCE);
        assertThat(testCourseAssignment.getIsPreview()).isEqualTo(UPDATED_IS_PREVIEW);
        assertThat(testCourseAssignment.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseAssignment.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseAssignment.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void putNonExistingCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseAssignmentWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();

        // Update the courseAssignment using partial update
        CourseAssignment partialUpdatedCourseAssignment = new CourseAssignment();
        partialUpdatedCourseAssignment.setId(courseAssignment.getId());

        partialUpdatedCourseAssignment
            .assignmentTitle(UPDATED_ASSIGNMENT_TITLE)
            .assignmentDescription(UPDATED_ASSIGNMENT_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .assignmentOrder(UPDATED_ASSIGNMENT_ORDER)
            .isPreview(UPDATED_IS_PREVIEW)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED);

        restCourseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignment))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignment testCourseAssignment = courseAssignmentList.get(courseAssignmentList.size() - 1);
        assertThat(testCourseAssignment.getAssignmentTitle()).isEqualTo(UPDATED_ASSIGNMENT_TITLE);
        assertThat(testCourseAssignment.getAssignmentDescription()).isEqualTo(UPDATED_ASSIGNMENT_DESCRIPTION);
        assertThat(testCourseAssignment.getSessionVideo()).isEqualTo(UPDATED_SESSION_VIDEO);
        assertThat(testCourseAssignment.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseAssignment.getAssignmentOrder()).isEqualTo(UPDATED_ASSIGNMENT_ORDER);
        assertThat(testCourseAssignment.getAssignmentResource()).isEqualTo(DEFAULT_ASSIGNMENT_RESOURCE);
        assertThat(testCourseAssignment.getIsPreview()).isEqualTo(UPDATED_IS_PREVIEW);
        assertThat(testCourseAssignment.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseAssignment.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseAssignment.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void fullUpdateCourseAssignmentWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();

        // Update the courseAssignment using partial update
        CourseAssignment partialUpdatedCourseAssignment = new CourseAssignment();
        partialUpdatedCourseAssignment.setId(courseAssignment.getId());

        partialUpdatedCourseAssignment
            .assignmentTitle(UPDATED_ASSIGNMENT_TITLE)
            .assignmentDescription(UPDATED_ASSIGNMENT_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .assignmentOrder(UPDATED_ASSIGNMENT_ORDER)
            .assignmentResource(UPDATED_ASSIGNMENT_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED);

        restCourseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignment))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignment testCourseAssignment = courseAssignmentList.get(courseAssignmentList.size() - 1);
        assertThat(testCourseAssignment.getAssignmentTitle()).isEqualTo(UPDATED_ASSIGNMENT_TITLE);
        assertThat(testCourseAssignment.getAssignmentDescription()).isEqualTo(UPDATED_ASSIGNMENT_DESCRIPTION);
        assertThat(testCourseAssignment.getSessionVideo()).isEqualTo(UPDATED_SESSION_VIDEO);
        assertThat(testCourseAssignment.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseAssignment.getAssignmentOrder()).isEqualTo(UPDATED_ASSIGNMENT_ORDER);
        assertThat(testCourseAssignment.getAssignmentResource()).isEqualTo(UPDATED_ASSIGNMENT_RESOURCE);
        assertThat(testCourseAssignment.getIsPreview()).isEqualTo(UPDATED_IS_PREVIEW);
        assertThat(testCourseAssignment.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseAssignment.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseAssignment.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void patchNonExistingCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseAssignment() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentRepository.findAll().size();
        courseAssignment.setId(count.incrementAndGet());

        // Create the CourseAssignment
        CourseAssignmentDTO courseAssignmentDTO = courseAssignmentMapper.toDto(courseAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignment in the database
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseAssignment() throws Exception {
        // Initialize the database
        courseAssignmentRepository.saveAndFlush(courseAssignment);

        int databaseSizeBeforeDelete = courseAssignmentRepository.findAll().size();

        // Delete the courseAssignment
        restCourseAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseAssignment> courseAssignmentList = courseAssignmentRepository.findAll();
        assertThat(courseAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
