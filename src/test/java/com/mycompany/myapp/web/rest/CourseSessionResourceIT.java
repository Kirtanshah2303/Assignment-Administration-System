package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.domain.CourseSession;
import com.mycompany.myapp.repository.CourseSessionRepository;
import com.mycompany.myapp.service.criteria.CourseSessionCriteria;
import com.mycompany.myapp.service.dto.CourseSessionDTO;
import com.mycompany.myapp.service.mapper.CourseSessionMapper;
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
 * Integration tests for the {@link CourseSessionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseSessionResourceIT {

    private static final String DEFAULT_SESSION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_VIDEO = "BBBBBBBBBB";

    private static final Instant DEFAULT_SESSION_DURATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SESSION_DURATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SESSION_ORDER = 1;
    private static final Integer UPDATED_SESSION_ORDER = 2;
    private static final Integer SMALLER_SESSION_ORDER = 1 - 1;

    private static final String DEFAULT_SESSION_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_RESOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PREVIEW = false;
    private static final Boolean UPDATED_IS_PREVIEW = true;

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;

    private static final String DEFAULT_SESSION_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_QUIZ_LINK = "AAAAAAAAAA";
    private static final String UPDATED_QUIZ_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseSessionRepository courseSessionRepository;

    @Autowired
    private CourseSessionMapper courseSessionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseSessionMockMvc;

    private CourseSession courseSession;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSession createEntity(EntityManager em) {
        CourseSession courseSession = new CourseSession()
            .sessionTitle(DEFAULT_SESSION_TITLE)
            .sessionDescription(DEFAULT_SESSION_DESCRIPTION)
            .sessionVideo(DEFAULT_SESSION_VIDEO)
            .sessionDuration(DEFAULT_SESSION_DURATION)
            .sessionOrder(DEFAULT_SESSION_ORDER)
            .sessionResource(DEFAULT_SESSION_RESOURCE)
            .isPreview(DEFAULT_IS_PREVIEW)
            .isDraft(DEFAULT_IS_DRAFT)
            .isApproved(DEFAULT_IS_APPROVED)
            .isPublished(DEFAULT_IS_PUBLISHED)
            .sessionLocation(DEFAULT_SESSION_LOCATION)
            .quizLink(DEFAULT_QUIZ_LINK);
        return courseSession;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSession createUpdatedEntity(EntityManager em) {
        CourseSession courseSession = new CourseSession()
            .sessionTitle(UPDATED_SESSION_TITLE)
            .sessionDescription(UPDATED_SESSION_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .sessionOrder(UPDATED_SESSION_ORDER)
            .sessionResource(UPDATED_SESSION_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .sessionLocation(UPDATED_SESSION_LOCATION)
            .quizLink(UPDATED_QUIZ_LINK);
        return courseSession;
    }

    @BeforeEach
    public void initTest() {
        courseSession = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseSession() throws Exception {
        int databaseSizeBeforeCreate = courseSessionRepository.findAll().size();
        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);
        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeCreate + 1);
        CourseSession testCourseSession = courseSessionList.get(courseSessionList.size() - 1);
        assertThat(testCourseSession.getSessionTitle()).isEqualTo(DEFAULT_SESSION_TITLE);
        assertThat(testCourseSession.getSessionDescription()).isEqualTo(DEFAULT_SESSION_DESCRIPTION);
        assertThat(testCourseSession.getSessionVideo()).isEqualTo(DEFAULT_SESSION_VIDEO);
        assertThat(testCourseSession.getSessionDuration()).isEqualTo(DEFAULT_SESSION_DURATION);
        assertThat(testCourseSession.getSessionOrder()).isEqualTo(DEFAULT_SESSION_ORDER);
        assertThat(testCourseSession.getSessionResource()).isEqualTo(DEFAULT_SESSION_RESOURCE);
        assertThat(testCourseSession.getIsPreview()).isEqualTo(DEFAULT_IS_PREVIEW);
        assertThat(testCourseSession.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseSession.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testCourseSession.getIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
        assertThat(testCourseSession.getSessionLocation()).isEqualTo(DEFAULT_SESSION_LOCATION);
        assertThat(testCourseSession.getQuizLink()).isEqualTo(DEFAULT_QUIZ_LINK);
    }

    @Test
    @Transactional
    void createCourseSessionWithExistingId() throws Exception {
        // Create the CourseSession with an existing ID
        courseSession.setId(1L);
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        int databaseSizeBeforeCreate = courseSessionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSessionTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setSessionTitle(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionVideoIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setSessionVideo(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setSessionDuration(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setSessionOrder(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPreviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setIsPreview(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setIsDraft(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setIsApproved(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPublishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionRepository.findAll().size();
        // set the field null
        courseSession.setIsPublished(null);

        // Create the CourseSession, which fails.
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        restCourseSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseSessions() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionTitle").value(hasItem(DEFAULT_SESSION_TITLE)))
            .andExpect(jsonPath("$.[*].sessionDescription").value(hasItem(DEFAULT_SESSION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sessionVideo").value(hasItem(DEFAULT_SESSION_VIDEO)))
            .andExpect(jsonPath("$.[*].sessionDuration").value(hasItem(DEFAULT_SESSION_DURATION.toString())))
            .andExpect(jsonPath("$.[*].sessionOrder").value(hasItem(DEFAULT_SESSION_ORDER)))
            .andExpect(jsonPath("$.[*].sessionResource").value(hasItem(DEFAULT_SESSION_RESOURCE)))
            .andExpect(jsonPath("$.[*].isPreview").value(hasItem(DEFAULT_IS_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].sessionLocation").value(hasItem(DEFAULT_SESSION_LOCATION)))
            .andExpect(jsonPath("$.[*].quizLink").value(hasItem(DEFAULT_QUIZ_LINK)));
    }

    @Test
    @Transactional
    void getCourseSession() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get the courseSession
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, courseSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseSession.getId().intValue()))
            .andExpect(jsonPath("$.sessionTitle").value(DEFAULT_SESSION_TITLE))
            .andExpect(jsonPath("$.sessionDescription").value(DEFAULT_SESSION_DESCRIPTION))
            .andExpect(jsonPath("$.sessionVideo").value(DEFAULT_SESSION_VIDEO))
            .andExpect(jsonPath("$.sessionDuration").value(DEFAULT_SESSION_DURATION.toString()))
            .andExpect(jsonPath("$.sessionOrder").value(DEFAULT_SESSION_ORDER))
            .andExpect(jsonPath("$.sessionResource").value(DEFAULT_SESSION_RESOURCE))
            .andExpect(jsonPath("$.isPreview").value(DEFAULT_IS_PREVIEW.booleanValue()))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.sessionLocation").value(DEFAULT_SESSION_LOCATION))
            .andExpect(jsonPath("$.quizLink").value(DEFAULT_QUIZ_LINK));
    }

    @Test
    @Transactional
    void getCourseSessionsByIdFiltering() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        Long id = courseSession.getId();

        defaultCourseSessionShouldBeFound("id.equals=" + id);
        defaultCourseSessionShouldNotBeFound("id.notEquals=" + id);

        defaultCourseSessionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseSessionShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseSessionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseSessionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle equals to DEFAULT_SESSION_TITLE
        defaultCourseSessionShouldBeFound("sessionTitle.equals=" + DEFAULT_SESSION_TITLE);

        // Get all the courseSessionList where sessionTitle equals to UPDATED_SESSION_TITLE
        defaultCourseSessionShouldNotBeFound("sessionTitle.equals=" + UPDATED_SESSION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle not equals to DEFAULT_SESSION_TITLE
        defaultCourseSessionShouldNotBeFound("sessionTitle.notEquals=" + DEFAULT_SESSION_TITLE);

        // Get all the courseSessionList where sessionTitle not equals to UPDATED_SESSION_TITLE
        defaultCourseSessionShouldBeFound("sessionTitle.notEquals=" + UPDATED_SESSION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle in DEFAULT_SESSION_TITLE or UPDATED_SESSION_TITLE
        defaultCourseSessionShouldBeFound("sessionTitle.in=" + DEFAULT_SESSION_TITLE + "," + UPDATED_SESSION_TITLE);

        // Get all the courseSessionList where sessionTitle equals to UPDATED_SESSION_TITLE
        defaultCourseSessionShouldNotBeFound("sessionTitle.in=" + UPDATED_SESSION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle is not null
        defaultCourseSessionShouldBeFound("sessionTitle.specified=true");

        // Get all the courseSessionList where sessionTitle is null
        defaultCourseSessionShouldNotBeFound("sessionTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle contains DEFAULT_SESSION_TITLE
        defaultCourseSessionShouldBeFound("sessionTitle.contains=" + DEFAULT_SESSION_TITLE);

        // Get all the courseSessionList where sessionTitle contains UPDATED_SESSION_TITLE
        defaultCourseSessionShouldNotBeFound("sessionTitle.contains=" + UPDATED_SESSION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionTitle does not contain DEFAULT_SESSION_TITLE
        defaultCourseSessionShouldNotBeFound("sessionTitle.doesNotContain=" + DEFAULT_SESSION_TITLE);

        // Get all the courseSessionList where sessionTitle does not contain UPDATED_SESSION_TITLE
        defaultCourseSessionShouldBeFound("sessionTitle.doesNotContain=" + UPDATED_SESSION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription equals to DEFAULT_SESSION_DESCRIPTION
        defaultCourseSessionShouldBeFound("sessionDescription.equals=" + DEFAULT_SESSION_DESCRIPTION);

        // Get all the courseSessionList where sessionDescription equals to UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldNotBeFound("sessionDescription.equals=" + UPDATED_SESSION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription not equals to DEFAULT_SESSION_DESCRIPTION
        defaultCourseSessionShouldNotBeFound("sessionDescription.notEquals=" + DEFAULT_SESSION_DESCRIPTION);

        // Get all the courseSessionList where sessionDescription not equals to UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldBeFound("sessionDescription.notEquals=" + UPDATED_SESSION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription in DEFAULT_SESSION_DESCRIPTION or UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldBeFound("sessionDescription.in=" + DEFAULT_SESSION_DESCRIPTION + "," + UPDATED_SESSION_DESCRIPTION);

        // Get all the courseSessionList where sessionDescription equals to UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldNotBeFound("sessionDescription.in=" + UPDATED_SESSION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription is not null
        defaultCourseSessionShouldBeFound("sessionDescription.specified=true");

        // Get all the courseSessionList where sessionDescription is null
        defaultCourseSessionShouldNotBeFound("sessionDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription contains DEFAULT_SESSION_DESCRIPTION
        defaultCourseSessionShouldBeFound("sessionDescription.contains=" + DEFAULT_SESSION_DESCRIPTION);

        // Get all the courseSessionList where sessionDescription contains UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldNotBeFound("sessionDescription.contains=" + UPDATED_SESSION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDescription does not contain DEFAULT_SESSION_DESCRIPTION
        defaultCourseSessionShouldNotBeFound("sessionDescription.doesNotContain=" + DEFAULT_SESSION_DESCRIPTION);

        // Get all the courseSessionList where sessionDescription does not contain UPDATED_SESSION_DESCRIPTION
        defaultCourseSessionShouldBeFound("sessionDescription.doesNotContain=" + UPDATED_SESSION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo equals to DEFAULT_SESSION_VIDEO
        defaultCourseSessionShouldBeFound("sessionVideo.equals=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseSessionList where sessionVideo equals to UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldNotBeFound("sessionVideo.equals=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo not equals to DEFAULT_SESSION_VIDEO
        defaultCourseSessionShouldNotBeFound("sessionVideo.notEquals=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseSessionList where sessionVideo not equals to UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldBeFound("sessionVideo.notEquals=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo in DEFAULT_SESSION_VIDEO or UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldBeFound("sessionVideo.in=" + DEFAULT_SESSION_VIDEO + "," + UPDATED_SESSION_VIDEO);

        // Get all the courseSessionList where sessionVideo equals to UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldNotBeFound("sessionVideo.in=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo is not null
        defaultCourseSessionShouldBeFound("sessionVideo.specified=true");

        // Get all the courseSessionList where sessionVideo is null
        defaultCourseSessionShouldNotBeFound("sessionVideo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo contains DEFAULT_SESSION_VIDEO
        defaultCourseSessionShouldBeFound("sessionVideo.contains=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseSessionList where sessionVideo contains UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldNotBeFound("sessionVideo.contains=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionVideoNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionVideo does not contain DEFAULT_SESSION_VIDEO
        defaultCourseSessionShouldNotBeFound("sessionVideo.doesNotContain=" + DEFAULT_SESSION_VIDEO);

        // Get all the courseSessionList where sessionVideo does not contain UPDATED_SESSION_VIDEO
        defaultCourseSessionShouldBeFound("sessionVideo.doesNotContain=" + UPDATED_SESSION_VIDEO);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDuration equals to DEFAULT_SESSION_DURATION
        defaultCourseSessionShouldBeFound("sessionDuration.equals=" + DEFAULT_SESSION_DURATION);

        // Get all the courseSessionList where sessionDuration equals to UPDATED_SESSION_DURATION
        defaultCourseSessionShouldNotBeFound("sessionDuration.equals=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDuration not equals to DEFAULT_SESSION_DURATION
        defaultCourseSessionShouldNotBeFound("sessionDuration.notEquals=" + DEFAULT_SESSION_DURATION);

        // Get all the courseSessionList where sessionDuration not equals to UPDATED_SESSION_DURATION
        defaultCourseSessionShouldBeFound("sessionDuration.notEquals=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDurationIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDuration in DEFAULT_SESSION_DURATION or UPDATED_SESSION_DURATION
        defaultCourseSessionShouldBeFound("sessionDuration.in=" + DEFAULT_SESSION_DURATION + "," + UPDATED_SESSION_DURATION);

        // Get all the courseSessionList where sessionDuration equals to UPDATED_SESSION_DURATION
        defaultCourseSessionShouldNotBeFound("sessionDuration.in=" + UPDATED_SESSION_DURATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionDuration is not null
        defaultCourseSessionShouldBeFound("sessionDuration.specified=true");

        // Get all the courseSessionList where sessionDuration is null
        defaultCourseSessionShouldNotBeFound("sessionDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder equals to DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.equals=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder equals to UPDATED_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.equals=" + UPDATED_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder not equals to DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.notEquals=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder not equals to UPDATED_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.notEquals=" + UPDATED_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder in DEFAULT_SESSION_ORDER or UPDATED_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.in=" + DEFAULT_SESSION_ORDER + "," + UPDATED_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder equals to UPDATED_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.in=" + UPDATED_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder is not null
        defaultCourseSessionShouldBeFound("sessionOrder.specified=true");

        // Get all the courseSessionList where sessionOrder is null
        defaultCourseSessionShouldNotBeFound("sessionOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder is greater than or equal to DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.greaterThanOrEqual=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder is greater than or equal to UPDATED_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.greaterThanOrEqual=" + UPDATED_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder is less than or equal to DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.lessThanOrEqual=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder is less than or equal to SMALLER_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.lessThanOrEqual=" + SMALLER_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder is less than DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.lessThan=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder is less than UPDATED_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.lessThan=" + UPDATED_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionOrder is greater than DEFAULT_SESSION_ORDER
        defaultCourseSessionShouldNotBeFound("sessionOrder.greaterThan=" + DEFAULT_SESSION_ORDER);

        // Get all the courseSessionList where sessionOrder is greater than SMALLER_SESSION_ORDER
        defaultCourseSessionShouldBeFound("sessionOrder.greaterThan=" + SMALLER_SESSION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource equals to DEFAULT_SESSION_RESOURCE
        defaultCourseSessionShouldBeFound("sessionResource.equals=" + DEFAULT_SESSION_RESOURCE);

        // Get all the courseSessionList where sessionResource equals to UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldNotBeFound("sessionResource.equals=" + UPDATED_SESSION_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource not equals to DEFAULT_SESSION_RESOURCE
        defaultCourseSessionShouldNotBeFound("sessionResource.notEquals=" + DEFAULT_SESSION_RESOURCE);

        // Get all the courseSessionList where sessionResource not equals to UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldBeFound("sessionResource.notEquals=" + UPDATED_SESSION_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource in DEFAULT_SESSION_RESOURCE or UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldBeFound("sessionResource.in=" + DEFAULT_SESSION_RESOURCE + "," + UPDATED_SESSION_RESOURCE);

        // Get all the courseSessionList where sessionResource equals to UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldNotBeFound("sessionResource.in=" + UPDATED_SESSION_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource is not null
        defaultCourseSessionShouldBeFound("sessionResource.specified=true");

        // Get all the courseSessionList where sessionResource is null
        defaultCourseSessionShouldNotBeFound("sessionResource.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource contains DEFAULT_SESSION_RESOURCE
        defaultCourseSessionShouldBeFound("sessionResource.contains=" + DEFAULT_SESSION_RESOURCE);

        // Get all the courseSessionList where sessionResource contains UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldNotBeFound("sessionResource.contains=" + UPDATED_SESSION_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionResourceNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionResource does not contain DEFAULT_SESSION_RESOURCE
        defaultCourseSessionShouldNotBeFound("sessionResource.doesNotContain=" + DEFAULT_SESSION_RESOURCE);

        // Get all the courseSessionList where sessionResource does not contain UPDATED_SESSION_RESOURCE
        defaultCourseSessionShouldBeFound("sessionResource.doesNotContain=" + UPDATED_SESSION_RESOURCE);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPreviewIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPreview equals to DEFAULT_IS_PREVIEW
        defaultCourseSessionShouldBeFound("isPreview.equals=" + DEFAULT_IS_PREVIEW);

        // Get all the courseSessionList where isPreview equals to UPDATED_IS_PREVIEW
        defaultCourseSessionShouldNotBeFound("isPreview.equals=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPreviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPreview not equals to DEFAULT_IS_PREVIEW
        defaultCourseSessionShouldNotBeFound("isPreview.notEquals=" + DEFAULT_IS_PREVIEW);

        // Get all the courseSessionList where isPreview not equals to UPDATED_IS_PREVIEW
        defaultCourseSessionShouldBeFound("isPreview.notEquals=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPreviewIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPreview in DEFAULT_IS_PREVIEW or UPDATED_IS_PREVIEW
        defaultCourseSessionShouldBeFound("isPreview.in=" + DEFAULT_IS_PREVIEW + "," + UPDATED_IS_PREVIEW);

        // Get all the courseSessionList where isPreview equals to UPDATED_IS_PREVIEW
        defaultCourseSessionShouldNotBeFound("isPreview.in=" + UPDATED_IS_PREVIEW);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPreviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPreview is not null
        defaultCourseSessionShouldBeFound("isPreview.specified=true");

        // Get all the courseSessionList where isPreview is null
        defaultCourseSessionShouldNotBeFound("isPreview.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsDraftIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isDraft equals to DEFAULT_IS_DRAFT
        defaultCourseSessionShouldBeFound("isDraft.equals=" + DEFAULT_IS_DRAFT);

        // Get all the courseSessionList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseSessionShouldNotBeFound("isDraft.equals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsDraftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isDraft not equals to DEFAULT_IS_DRAFT
        defaultCourseSessionShouldNotBeFound("isDraft.notEquals=" + DEFAULT_IS_DRAFT);

        // Get all the courseSessionList where isDraft not equals to UPDATED_IS_DRAFT
        defaultCourseSessionShouldBeFound("isDraft.notEquals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsDraftIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isDraft in DEFAULT_IS_DRAFT or UPDATED_IS_DRAFT
        defaultCourseSessionShouldBeFound("isDraft.in=" + DEFAULT_IS_DRAFT + "," + UPDATED_IS_DRAFT);

        // Get all the courseSessionList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseSessionShouldNotBeFound("isDraft.in=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsDraftIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isDraft is not null
        defaultCourseSessionShouldBeFound("isDraft.specified=true");

        // Get all the courseSessionList where isDraft is null
        defaultCourseSessionShouldNotBeFound("isDraft.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isApproved equals to DEFAULT_IS_APPROVED
        defaultCourseSessionShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the courseSessionList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseSessionShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultCourseSessionShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the courseSessionList where isApproved not equals to UPDATED_IS_APPROVED
        defaultCourseSessionShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultCourseSessionShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the courseSessionList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseSessionShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isApproved is not null
        defaultCourseSessionShouldBeFound("isApproved.specified=true");

        // Get all the courseSessionList where isApproved is null
        defaultCourseSessionShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPublishedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPublished equals to DEFAULT_IS_PUBLISHED
        defaultCourseSessionShouldBeFound("isPublished.equals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseSessionList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseSessionShouldNotBeFound("isPublished.equals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPublishedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPublished not equals to DEFAULT_IS_PUBLISHED
        defaultCourseSessionShouldNotBeFound("isPublished.notEquals=" + DEFAULT_IS_PUBLISHED);

        // Get all the courseSessionList where isPublished not equals to UPDATED_IS_PUBLISHED
        defaultCourseSessionShouldBeFound("isPublished.notEquals=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPublishedIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPublished in DEFAULT_IS_PUBLISHED or UPDATED_IS_PUBLISHED
        defaultCourseSessionShouldBeFound("isPublished.in=" + DEFAULT_IS_PUBLISHED + "," + UPDATED_IS_PUBLISHED);

        // Get all the courseSessionList where isPublished equals to UPDATED_IS_PUBLISHED
        defaultCourseSessionShouldNotBeFound("isPublished.in=" + UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByIsPublishedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where isPublished is not null
        defaultCourseSessionShouldBeFound("isPublished.specified=true");

        // Get all the courseSessionList where isPublished is null
        defaultCourseSessionShouldNotBeFound("isPublished.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation equals to DEFAULT_SESSION_LOCATION
        defaultCourseSessionShouldBeFound("sessionLocation.equals=" + DEFAULT_SESSION_LOCATION);

        // Get all the courseSessionList where sessionLocation equals to UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldNotBeFound("sessionLocation.equals=" + UPDATED_SESSION_LOCATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation not equals to DEFAULT_SESSION_LOCATION
        defaultCourseSessionShouldNotBeFound("sessionLocation.notEquals=" + DEFAULT_SESSION_LOCATION);

        // Get all the courseSessionList where sessionLocation not equals to UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldBeFound("sessionLocation.notEquals=" + UPDATED_SESSION_LOCATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation in DEFAULT_SESSION_LOCATION or UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldBeFound("sessionLocation.in=" + DEFAULT_SESSION_LOCATION + "," + UPDATED_SESSION_LOCATION);

        // Get all the courseSessionList where sessionLocation equals to UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldNotBeFound("sessionLocation.in=" + UPDATED_SESSION_LOCATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation is not null
        defaultCourseSessionShouldBeFound("sessionLocation.specified=true");

        // Get all the courseSessionList where sessionLocation is null
        defaultCourseSessionShouldNotBeFound("sessionLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation contains DEFAULT_SESSION_LOCATION
        defaultCourseSessionShouldBeFound("sessionLocation.contains=" + DEFAULT_SESSION_LOCATION);

        // Get all the courseSessionList where sessionLocation contains UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldNotBeFound("sessionLocation.contains=" + UPDATED_SESSION_LOCATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsBySessionLocationNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where sessionLocation does not contain DEFAULT_SESSION_LOCATION
        defaultCourseSessionShouldNotBeFound("sessionLocation.doesNotContain=" + DEFAULT_SESSION_LOCATION);

        // Get all the courseSessionList where sessionLocation does not contain UPDATED_SESSION_LOCATION
        defaultCourseSessionShouldBeFound("sessionLocation.doesNotContain=" + UPDATED_SESSION_LOCATION);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink equals to DEFAULT_QUIZ_LINK
        defaultCourseSessionShouldBeFound("quizLink.equals=" + DEFAULT_QUIZ_LINK);

        // Get all the courseSessionList where quizLink equals to UPDATED_QUIZ_LINK
        defaultCourseSessionShouldNotBeFound("quizLink.equals=" + UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink not equals to DEFAULT_QUIZ_LINK
        defaultCourseSessionShouldNotBeFound("quizLink.notEquals=" + DEFAULT_QUIZ_LINK);

        // Get all the courseSessionList where quizLink not equals to UPDATED_QUIZ_LINK
        defaultCourseSessionShouldBeFound("quizLink.notEquals=" + UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink in DEFAULT_QUIZ_LINK or UPDATED_QUIZ_LINK
        defaultCourseSessionShouldBeFound("quizLink.in=" + DEFAULT_QUIZ_LINK + "," + UPDATED_QUIZ_LINK);

        // Get all the courseSessionList where quizLink equals to UPDATED_QUIZ_LINK
        defaultCourseSessionShouldNotBeFound("quizLink.in=" + UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink is not null
        defaultCourseSessionShouldBeFound("quizLink.specified=true");

        // Get all the courseSessionList where quizLink is null
        defaultCourseSessionShouldNotBeFound("quizLink.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink contains DEFAULT_QUIZ_LINK
        defaultCourseSessionShouldBeFound("quizLink.contains=" + DEFAULT_QUIZ_LINK);

        // Get all the courseSessionList where quizLink contains UPDATED_QUIZ_LINK
        defaultCourseSessionShouldNotBeFound("quizLink.contains=" + UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByQuizLinkNotContainsSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        // Get all the courseSessionList where quizLink does not contain DEFAULT_QUIZ_LINK
        defaultCourseSessionShouldNotBeFound("quizLink.doesNotContain=" + DEFAULT_QUIZ_LINK);

        // Get all the courseSessionList where quizLink does not contain UPDATED_QUIZ_LINK
        defaultCourseSessionShouldBeFound("quizLink.doesNotContain=" + UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void getAllCourseSessionsByCourseSectionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);
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
        courseSession.setCourseSection(courseSection);
        courseSessionRepository.saveAndFlush(courseSession);
        Long courseSectionId = courseSection.getId();

        // Get all the courseSessionList where courseSection equals to courseSectionId
        defaultCourseSessionShouldBeFound("courseSectionId.equals=" + courseSectionId);

        // Get all the courseSessionList where courseSection equals to (courseSectionId + 1)
        defaultCourseSessionShouldNotBeFound("courseSectionId.equals=" + (courseSectionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseSessionShouldBeFound(String filter) throws Exception {
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionTitle").value(hasItem(DEFAULT_SESSION_TITLE)))
            .andExpect(jsonPath("$.[*].sessionDescription").value(hasItem(DEFAULT_SESSION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sessionVideo").value(hasItem(DEFAULT_SESSION_VIDEO)))
            .andExpect(jsonPath("$.[*].sessionDuration").value(hasItem(DEFAULT_SESSION_DURATION.toString())))
            .andExpect(jsonPath("$.[*].sessionOrder").value(hasItem(DEFAULT_SESSION_ORDER)))
            .andExpect(jsonPath("$.[*].sessionResource").value(hasItem(DEFAULT_SESSION_RESOURCE)))
            .andExpect(jsonPath("$.[*].isPreview").value(hasItem(DEFAULT_IS_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].sessionLocation").value(hasItem(DEFAULT_SESSION_LOCATION)))
            .andExpect(jsonPath("$.[*].quizLink").value(hasItem(DEFAULT_QUIZ_LINK)));

        // Check, that the count call also returns 1
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseSessionShouldNotBeFound(String filter) throws Exception {
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseSessionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseSession() throws Exception {
        // Get the courseSession
        restCourseSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseSession() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();

        // Update the courseSession
        CourseSession updatedCourseSession = courseSessionRepository.findById(courseSession.getId()).get();
        // Disconnect from session so that the updates on updatedCourseSession are not directly saved in db
        em.detach(updatedCourseSession);
        updatedCourseSession
            .sessionTitle(UPDATED_SESSION_TITLE)
            .sessionDescription(UPDATED_SESSION_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .sessionOrder(UPDATED_SESSION_ORDER)
            .sessionResource(UPDATED_SESSION_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .sessionLocation(UPDATED_SESSION_LOCATION)
            .quizLink(UPDATED_QUIZ_LINK);
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(updatedCourseSession);

        restCourseSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
        CourseSession testCourseSession = courseSessionList.get(courseSessionList.size() - 1);
        assertThat(testCourseSession.getSessionTitle()).isEqualTo(UPDATED_SESSION_TITLE);
        assertThat(testCourseSession.getSessionDescription()).isEqualTo(UPDATED_SESSION_DESCRIPTION);
        assertThat(testCourseSession.getSessionVideo()).isEqualTo(UPDATED_SESSION_VIDEO);
        assertThat(testCourseSession.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseSession.getSessionOrder()).isEqualTo(UPDATED_SESSION_ORDER);
        assertThat(testCourseSession.getSessionResource()).isEqualTo(UPDATED_SESSION_RESOURCE);
        assertThat(testCourseSession.getIsPreview()).isEqualTo(UPDATED_IS_PREVIEW);
        assertThat(testCourseSession.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseSession.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseSession.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testCourseSession.getSessionLocation()).isEqualTo(UPDATED_SESSION_LOCATION);
        assertThat(testCourseSession.getQuizLink()).isEqualTo(UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void putNonExistingCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseSessionWithPatch() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();

        // Update the courseSession using partial update
        CourseSession partialUpdatedCourseSession = new CourseSession();
        partialUpdatedCourseSession.setId(courseSession.getId());

        partialUpdatedCourseSession
            .sessionTitle(UPDATED_SESSION_TITLE)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .sessionResource(UPDATED_SESSION_RESOURCE)
            .isApproved(UPDATED_IS_APPROVED)
            .quizLink(UPDATED_QUIZ_LINK);

        restCourseSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSession))
            )
            .andExpect(status().isOk());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
        CourseSession testCourseSession = courseSessionList.get(courseSessionList.size() - 1);
        assertThat(testCourseSession.getSessionTitle()).isEqualTo(UPDATED_SESSION_TITLE);
        assertThat(testCourseSession.getSessionDescription()).isEqualTo(DEFAULT_SESSION_DESCRIPTION);
        assertThat(testCourseSession.getSessionVideo()).isEqualTo(DEFAULT_SESSION_VIDEO);
        assertThat(testCourseSession.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseSession.getSessionOrder()).isEqualTo(DEFAULT_SESSION_ORDER);
        assertThat(testCourseSession.getSessionResource()).isEqualTo(UPDATED_SESSION_RESOURCE);
        assertThat(testCourseSession.getIsPreview()).isEqualTo(DEFAULT_IS_PREVIEW);
        assertThat(testCourseSession.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseSession.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseSession.getIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
        assertThat(testCourseSession.getSessionLocation()).isEqualTo(DEFAULT_SESSION_LOCATION);
        assertThat(testCourseSession.getQuizLink()).isEqualTo(UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void fullUpdateCourseSessionWithPatch() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();

        // Update the courseSession using partial update
        CourseSession partialUpdatedCourseSession = new CourseSession();
        partialUpdatedCourseSession.setId(courseSession.getId());

        partialUpdatedCourseSession
            .sessionTitle(UPDATED_SESSION_TITLE)
            .sessionDescription(UPDATED_SESSION_DESCRIPTION)
            .sessionVideo(UPDATED_SESSION_VIDEO)
            .sessionDuration(UPDATED_SESSION_DURATION)
            .sessionOrder(UPDATED_SESSION_ORDER)
            .sessionResource(UPDATED_SESSION_RESOURCE)
            .isPreview(UPDATED_IS_PREVIEW)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .sessionLocation(UPDATED_SESSION_LOCATION)
            .quizLink(UPDATED_QUIZ_LINK);

        restCourseSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSession))
            )
            .andExpect(status().isOk());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
        CourseSession testCourseSession = courseSessionList.get(courseSessionList.size() - 1);
        assertThat(testCourseSession.getSessionTitle()).isEqualTo(UPDATED_SESSION_TITLE);
        assertThat(testCourseSession.getSessionDescription()).isEqualTo(UPDATED_SESSION_DESCRIPTION);
        assertThat(testCourseSession.getSessionVideo()).isEqualTo(UPDATED_SESSION_VIDEO);
        assertThat(testCourseSession.getSessionDuration()).isEqualTo(UPDATED_SESSION_DURATION);
        assertThat(testCourseSession.getSessionOrder()).isEqualTo(UPDATED_SESSION_ORDER);
        assertThat(testCourseSession.getSessionResource()).isEqualTo(UPDATED_SESSION_RESOURCE);
        assertThat(testCourseSession.getIsPreview()).isEqualTo(UPDATED_IS_PREVIEW);
        assertThat(testCourseSession.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseSession.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testCourseSession.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testCourseSession.getSessionLocation()).isEqualTo(UPDATED_SESSION_LOCATION);
        assertThat(testCourseSession.getQuizLink()).isEqualTo(UPDATED_QUIZ_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseSessionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseSession() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionRepository.findAll().size();
        courseSession.setId(count.incrementAndGet());

        // Create the CourseSession
        CourseSessionDTO courseSessionDTO = courseSessionMapper.toDto(courseSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSession in the database
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseSession() throws Exception {
        // Initialize the database
        courseSessionRepository.saveAndFlush(courseSession);

        int databaseSizeBeforeDelete = courseSessionRepository.findAll().size();

        // Delete the courseSession
        restCourseSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseSession.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseSession> courseSessionList = courseSessionRepository.findAll();
        assertThat(courseSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
