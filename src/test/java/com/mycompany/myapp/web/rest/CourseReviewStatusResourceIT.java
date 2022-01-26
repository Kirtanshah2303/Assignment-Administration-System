package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseReviewStatus;
import com.mycompany.myapp.domain.CourseSession;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseReviewStatusRepository;
import com.mycompany.myapp.service.criteria.CourseReviewStatusCriteria;
import com.mycompany.myapp.service.dto.CourseReviewStatusDTO;
import com.mycompany.myapp.service.mapper.CourseReviewStatusMapper;
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
 * Integration tests for the {@link CourseReviewStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseReviewStatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STATUS_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STATUS_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STATUS_UPDATED_ON = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-review-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseReviewStatusRepository courseReviewStatusRepository;

    @Autowired
    private CourseReviewStatusMapper courseReviewStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseReviewStatusMockMvc;

    private CourseReviewStatus courseReviewStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseReviewStatus createEntity(EntityManager em) {
        CourseReviewStatus courseReviewStatus = new CourseReviewStatus()
            .status(DEFAULT_STATUS)
            .statusUpdatedOn(DEFAULT_STATUS_UPDATED_ON)
            .feedback(DEFAULT_FEEDBACK);
        return courseReviewStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseReviewStatus createUpdatedEntity(EntityManager em) {
        CourseReviewStatus courseReviewStatus = new CourseReviewStatus()
            .status(UPDATED_STATUS)
            .statusUpdatedOn(UPDATED_STATUS_UPDATED_ON)
            .feedback(UPDATED_FEEDBACK);
        return courseReviewStatus;
    }

    @BeforeEach
    public void initTest() {
        courseReviewStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseReviewStatus() throws Exception {
        int databaseSizeBeforeCreate = courseReviewStatusRepository.findAll().size();
        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);
        restCourseReviewStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CourseReviewStatus testCourseReviewStatus = courseReviewStatusList.get(courseReviewStatusList.size() - 1);
        assertThat(testCourseReviewStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCourseReviewStatus.getStatusUpdatedOn()).isEqualTo(DEFAULT_STATUS_UPDATED_ON);
        assertThat(testCourseReviewStatus.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
    }

    @Test
    @Transactional
    void createCourseReviewStatusWithExistingId() throws Exception {
        // Create the CourseReviewStatus with an existing ID
        courseReviewStatus.setId(1L);
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        int databaseSizeBeforeCreate = courseReviewStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseReviewStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatuses() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseReviewStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusUpdatedOn").value(hasItem(DEFAULT_STATUS_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)));
    }

    @Test
    @Transactional
    void getCourseReviewStatus() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get the courseReviewStatus
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, courseReviewStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseReviewStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusUpdatedOn").value(DEFAULT_STATUS_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK));
    }

    @Test
    @Transactional
    void getCourseReviewStatusesByIdFiltering() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        Long id = courseReviewStatus.getId();

        defaultCourseReviewStatusShouldBeFound("id.equals=" + id);
        defaultCourseReviewStatusShouldNotBeFound("id.notEquals=" + id);

        defaultCourseReviewStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseReviewStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseReviewStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseReviewStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status equals to DEFAULT_STATUS
        defaultCourseReviewStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the courseReviewStatusList where status equals to UPDATED_STATUS
        defaultCourseReviewStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status not equals to DEFAULT_STATUS
        defaultCourseReviewStatusShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the courseReviewStatusList where status not equals to UPDATED_STATUS
        defaultCourseReviewStatusShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCourseReviewStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the courseReviewStatusList where status equals to UPDATED_STATUS
        defaultCourseReviewStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status is not null
        defaultCourseReviewStatusShouldBeFound("status.specified=true");

        // Get all the courseReviewStatusList where status is null
        defaultCourseReviewStatusShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status contains DEFAULT_STATUS
        defaultCourseReviewStatusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the courseReviewStatusList where status contains UPDATED_STATUS
        defaultCourseReviewStatusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where status does not contain DEFAULT_STATUS
        defaultCourseReviewStatusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the courseReviewStatusList where status does not contain UPDATED_STATUS
        defaultCourseReviewStatusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn equals to DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.equals=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn equals to UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.equals=" + UPDATED_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn not equals to DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.notEquals=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn not equals to UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.notEquals=" + UPDATED_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn in DEFAULT_STATUS_UPDATED_ON or UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.in=" + DEFAULT_STATUS_UPDATED_ON + "," + UPDATED_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn equals to UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.in=" + UPDATED_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn is not null
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.specified=true");

        // Get all the courseReviewStatusList where statusUpdatedOn is null
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn is greater than or equal to DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.greaterThanOrEqual=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn is greater than or equal to UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.greaterThanOrEqual=" + UPDATED_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn is less than or equal to DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.lessThanOrEqual=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn is less than or equal to SMALLER_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.lessThanOrEqual=" + SMALLER_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn is less than DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.lessThan=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn is less than UPDATED_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.lessThan=" + UPDATED_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByStatusUpdatedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where statusUpdatedOn is greater than DEFAULT_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldNotBeFound("statusUpdatedOn.greaterThan=" + DEFAULT_STATUS_UPDATED_ON);

        // Get all the courseReviewStatusList where statusUpdatedOn is greater than SMALLER_STATUS_UPDATED_ON
        defaultCourseReviewStatusShouldBeFound("statusUpdatedOn.greaterThan=" + SMALLER_STATUS_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback equals to DEFAULT_FEEDBACK
        defaultCourseReviewStatusShouldBeFound("feedback.equals=" + DEFAULT_FEEDBACK);

        // Get all the courseReviewStatusList where feedback equals to UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldNotBeFound("feedback.equals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback not equals to DEFAULT_FEEDBACK
        defaultCourseReviewStatusShouldNotBeFound("feedback.notEquals=" + DEFAULT_FEEDBACK);

        // Get all the courseReviewStatusList where feedback not equals to UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldBeFound("feedback.notEquals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback in DEFAULT_FEEDBACK or UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldBeFound("feedback.in=" + DEFAULT_FEEDBACK + "," + UPDATED_FEEDBACK);

        // Get all the courseReviewStatusList where feedback equals to UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldNotBeFound("feedback.in=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback is not null
        defaultCourseReviewStatusShouldBeFound("feedback.specified=true");

        // Get all the courseReviewStatusList where feedback is null
        defaultCourseReviewStatusShouldNotBeFound("feedback.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackContainsSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback contains DEFAULT_FEEDBACK
        defaultCourseReviewStatusShouldBeFound("feedback.contains=" + DEFAULT_FEEDBACK);

        // Get all the courseReviewStatusList where feedback contains UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldNotBeFound("feedback.contains=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        // Get all the courseReviewStatusList where feedback does not contain DEFAULT_FEEDBACK
        defaultCourseReviewStatusShouldNotBeFound("feedback.doesNotContain=" + DEFAULT_FEEDBACK);

        // Get all the courseReviewStatusList where feedback does not contain UPDATED_FEEDBACK
        defaultCourseReviewStatusShouldBeFound("feedback.doesNotContain=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);
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
        courseReviewStatus.setUser(user);
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);
        Long userId = user.getId();

        // Get all the courseReviewStatusList where user equals to userId
        defaultCourseReviewStatusShouldBeFound("userId.equals=" + userId);

        // Get all the courseReviewStatusList where user equals to (userId + 1)
        defaultCourseReviewStatusShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllCourseReviewStatusesByCourseSessionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);
        CourseSession courseSession;
        if (TestUtil.findAll(em, CourseSession.class).isEmpty()) {
            courseSession = CourseSessionResourceIT.createEntity(em);
            em.persist(courseSession);
            em.flush();
        } else {
            courseSession = TestUtil.findAll(em, CourseSession.class).get(0);
        }
        em.persist(courseSession);
        em.flush();
        courseReviewStatus.setCourseSession(courseSession);
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);
        Long courseSessionId = courseSession.getId();

        // Get all the courseReviewStatusList where courseSession equals to courseSessionId
        defaultCourseReviewStatusShouldBeFound("courseSessionId.equals=" + courseSessionId);

        // Get all the courseReviewStatusList where courseSession equals to (courseSessionId + 1)
        defaultCourseReviewStatusShouldNotBeFound("courseSessionId.equals=" + (courseSessionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseReviewStatusShouldBeFound(String filter) throws Exception {
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseReviewStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusUpdatedOn").value(hasItem(DEFAULT_STATUS_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)));

        // Check, that the count call also returns 1
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseReviewStatusShouldNotBeFound(String filter) throws Exception {
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseReviewStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseReviewStatus() throws Exception {
        // Get the courseReviewStatus
        restCourseReviewStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseReviewStatus() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();

        // Update the courseReviewStatus
        CourseReviewStatus updatedCourseReviewStatus = courseReviewStatusRepository.findById(courseReviewStatus.getId()).get();
        // Disconnect from session so that the updates on updatedCourseReviewStatus are not directly saved in db
        em.detach(updatedCourseReviewStatus);
        updatedCourseReviewStatus.status(UPDATED_STATUS).statusUpdatedOn(UPDATED_STATUS_UPDATED_ON).feedback(UPDATED_FEEDBACK);
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(updatedCourseReviewStatus);

        restCourseReviewStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseReviewStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseReviewStatus testCourseReviewStatus = courseReviewStatusList.get(courseReviewStatusList.size() - 1);
        assertThat(testCourseReviewStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCourseReviewStatus.getStatusUpdatedOn()).isEqualTo(UPDATED_STATUS_UPDATED_ON);
        assertThat(testCourseReviewStatus.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void putNonExistingCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseReviewStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseReviewStatusWithPatch() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();

        // Update the courseReviewStatus using partial update
        CourseReviewStatus partialUpdatedCourseReviewStatus = new CourseReviewStatus();
        partialUpdatedCourseReviewStatus.setId(courseReviewStatus.getId());

        partialUpdatedCourseReviewStatus.statusUpdatedOn(UPDATED_STATUS_UPDATED_ON).feedback(UPDATED_FEEDBACK);

        restCourseReviewStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseReviewStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseReviewStatus))
            )
            .andExpect(status().isOk());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseReviewStatus testCourseReviewStatus = courseReviewStatusList.get(courseReviewStatusList.size() - 1);
        assertThat(testCourseReviewStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCourseReviewStatus.getStatusUpdatedOn()).isEqualTo(UPDATED_STATUS_UPDATED_ON);
        assertThat(testCourseReviewStatus.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void fullUpdateCourseReviewStatusWithPatch() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();

        // Update the courseReviewStatus using partial update
        CourseReviewStatus partialUpdatedCourseReviewStatus = new CourseReviewStatus();
        partialUpdatedCourseReviewStatus.setId(courseReviewStatus.getId());

        partialUpdatedCourseReviewStatus.status(UPDATED_STATUS).statusUpdatedOn(UPDATED_STATUS_UPDATED_ON).feedback(UPDATED_FEEDBACK);

        restCourseReviewStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseReviewStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseReviewStatus))
            )
            .andExpect(status().isOk());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
        CourseReviewStatus testCourseReviewStatus = courseReviewStatusList.get(courseReviewStatusList.size() - 1);
        assertThat(testCourseReviewStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCourseReviewStatus.getStatusUpdatedOn()).isEqualTo(UPDATED_STATUS_UPDATED_ON);
        assertThat(testCourseReviewStatus.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void patchNonExistingCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseReviewStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseReviewStatus() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewStatusRepository.findAll().size();
        courseReviewStatus.setId(count.incrementAndGet());

        // Create the CourseReviewStatus
        CourseReviewStatusDTO courseReviewStatusDTO = courseReviewStatusMapper.toDto(courseReviewStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseReviewStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseReviewStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseReviewStatus in the database
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseReviewStatus() throws Exception {
        // Initialize the database
        courseReviewStatusRepository.saveAndFlush(courseReviewStatus);

        int databaseSizeBeforeDelete = courseReviewStatusRepository.findAll().size();

        // Delete the courseReviewStatus
        restCourseReviewStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseReviewStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseReviewStatus> courseReviewStatusList = courseReviewStatusRepository.findAll();
        assertThat(courseReviewStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
