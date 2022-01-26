package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.domain.CourseAssignmentProgress;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseAssignmentProgressRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentProgressCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentProgressDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentProgressMapper;
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
 * Integration tests for the {@link CourseAssignmentProgressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseAssignmentProgressResourceIT {

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    private static final LocalDate DEFAULT_COMPLETED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COMPLETED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/course-assignment-progresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseAssignmentProgressRepository courseAssignmentProgressRepository;

    @Autowired
    private CourseAssignmentProgressMapper courseAssignmentProgressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseAssignmentProgressMockMvc;

    private CourseAssignmentProgress courseAssignmentProgress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentProgress createEntity(EntityManager em) {
        CourseAssignmentProgress courseAssignmentProgress = new CourseAssignmentProgress()
            .completed(DEFAULT_COMPLETED)
            .completedDate(DEFAULT_COMPLETED_DATE);
        return courseAssignmentProgress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentProgress createUpdatedEntity(EntityManager em) {
        CourseAssignmentProgress courseAssignmentProgress = new CourseAssignmentProgress()
            .completed(UPDATED_COMPLETED)
            .completedDate(UPDATED_COMPLETED_DATE);
        return courseAssignmentProgress;
    }

    @BeforeEach
    public void initTest() {
        courseAssignmentProgress = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeCreate = courseAssignmentProgressRepository.findAll().size();
        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);
        restCourseAssignmentProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeCreate + 1);
        CourseAssignmentProgress testCourseAssignmentProgress = courseAssignmentProgressList.get(courseAssignmentProgressList.size() - 1);
        assertThat(testCourseAssignmentProgress.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
        assertThat(testCourseAssignmentProgress.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void createCourseAssignmentProgressWithExistingId() throws Exception {
        // Create the CourseAssignmentProgress with an existing ID
        courseAssignmentProgress.setId(1L);
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        int databaseSizeBeforeCreate = courseAssignmentProgressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseAssignmentProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseAssignmentProgressRepository.findAll().size();
        // set the field null
        courseAssignmentProgress.setCompleted(null);

        // Create the CourseAssignmentProgress, which fails.
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        restCourseAssignmentProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgresses() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(DEFAULT_COMPLETED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCourseAssignmentProgress() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get the courseAssignmentProgress
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL_ID, courseAssignmentProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseAssignmentProgress.getId().intValue()))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.completedDate").value(DEFAULT_COMPLETED_DATE.toString()));
    }

    @Test
    @Transactional
    void getCourseAssignmentProgressesByIdFiltering() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        Long id = courseAssignmentProgress.getId();

        defaultCourseAssignmentProgressShouldBeFound("id.equals=" + id);
        defaultCourseAssignmentProgressShouldNotBeFound("id.notEquals=" + id);

        defaultCourseAssignmentProgressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseAssignmentProgressShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseAssignmentProgressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseAssignmentProgressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completed equals to DEFAULT_COMPLETED
        defaultCourseAssignmentProgressShouldBeFound("completed.equals=" + DEFAULT_COMPLETED);

        // Get all the courseAssignmentProgressList where completed equals to UPDATED_COMPLETED
        defaultCourseAssignmentProgressShouldNotBeFound("completed.equals=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completed not equals to DEFAULT_COMPLETED
        defaultCourseAssignmentProgressShouldNotBeFound("completed.notEquals=" + DEFAULT_COMPLETED);

        // Get all the courseAssignmentProgressList where completed not equals to UPDATED_COMPLETED
        defaultCourseAssignmentProgressShouldBeFound("completed.notEquals=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completed in DEFAULT_COMPLETED or UPDATED_COMPLETED
        defaultCourseAssignmentProgressShouldBeFound("completed.in=" + DEFAULT_COMPLETED + "," + UPDATED_COMPLETED);

        // Get all the courseAssignmentProgressList where completed equals to UPDATED_COMPLETED
        defaultCourseAssignmentProgressShouldNotBeFound("completed.in=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completed is not null
        defaultCourseAssignmentProgressShouldBeFound("completed.specified=true");

        // Get all the courseAssignmentProgressList where completed is null
        defaultCourseAssignmentProgressShouldNotBeFound("completed.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate equals to DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.equals=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate equals to UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.equals=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate not equals to DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.notEquals=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate not equals to UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.notEquals=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate in DEFAULT_COMPLETED_DATE or UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.in=" + DEFAULT_COMPLETED_DATE + "," + UPDATED_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate equals to UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.in=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate is not null
        defaultCourseAssignmentProgressShouldBeFound("completedDate.specified=true");

        // Get all the courseAssignmentProgressList where completedDate is null
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate is greater than or equal to DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.greaterThanOrEqual=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate is greater than or equal to UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.greaterThanOrEqual=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate is less than or equal to DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.lessThanOrEqual=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate is less than or equal to SMALLER_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.lessThanOrEqual=" + SMALLER_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate is less than DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.lessThan=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate is less than UPDATED_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.lessThan=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCompletedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        // Get all the courseAssignmentProgressList where completedDate is greater than DEFAULT_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldNotBeFound("completedDate.greaterThan=" + DEFAULT_COMPLETED_DATE);

        // Get all the courseAssignmentProgressList where completedDate is greater than SMALLER_COMPLETED_DATE
        defaultCourseAssignmentProgressShouldBeFound("completedDate.greaterThan=" + SMALLER_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);
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
        courseAssignmentProgress.setUser(user);
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);
        Long userId = user.getId();

        // Get all the courseAssignmentProgressList where user equals to userId
        defaultCourseAssignmentProgressShouldBeFound("userId.equals=" + userId);

        // Get all the courseAssignmentProgressList where user equals to (userId + 1)
        defaultCourseAssignmentProgressShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllCourseAssignmentProgressesByCourseAssignmentIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);
        CourseAssignment courseAssignment;
        if (TestUtil.findAll(em, CourseAssignment.class).isEmpty()) {
            courseAssignment = CourseAssignmentResourceIT.createEntity(em);
            em.persist(courseAssignment);
            em.flush();
        } else {
            courseAssignment = TestUtil.findAll(em, CourseAssignment.class).get(0);
        }
        em.persist(courseAssignment);
        em.flush();
        courseAssignmentProgress.setCourseAssignment(courseAssignment);
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);
        Long courseAssignmentId = courseAssignment.getId();

        // Get all the courseAssignmentProgressList where courseAssignment equals to courseAssignmentId
        defaultCourseAssignmentProgressShouldBeFound("courseAssignmentId.equals=" + courseAssignmentId);

        // Get all the courseAssignmentProgressList where courseAssignment equals to (courseAssignmentId + 1)
        defaultCourseAssignmentProgressShouldNotBeFound("courseAssignmentId.equals=" + (courseAssignmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseAssignmentProgressShouldBeFound(String filter) throws Exception {
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(DEFAULT_COMPLETED_DATE.toString())));

        // Check, that the count call also returns 1
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseAssignmentProgressShouldNotBeFound(String filter) throws Exception {
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseAssignmentProgressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseAssignmentProgress() throws Exception {
        // Get the courseAssignmentProgress
        restCourseAssignmentProgressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseAssignmentProgress() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();

        // Update the courseAssignmentProgress
        CourseAssignmentProgress updatedCourseAssignmentProgress = courseAssignmentProgressRepository
            .findById(courseAssignmentProgress.getId())
            .get();
        // Disconnect from session so that the updates on updatedCourseAssignmentProgress are not directly saved in db
        em.detach(updatedCourseAssignmentProgress);
        updatedCourseAssignmentProgress.completed(UPDATED_COMPLETED).completedDate(UPDATED_COMPLETED_DATE);
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(updatedCourseAssignmentProgress);

        restCourseAssignmentProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentProgress testCourseAssignmentProgress = courseAssignmentProgressList.get(courseAssignmentProgressList.size() - 1);
        assertThat(testCourseAssignmentProgress.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testCourseAssignmentProgress.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseAssignmentProgressWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();

        // Update the courseAssignmentProgress using partial update
        CourseAssignmentProgress partialUpdatedCourseAssignmentProgress = new CourseAssignmentProgress();
        partialUpdatedCourseAssignmentProgress.setId(courseAssignmentProgress.getId());

        partialUpdatedCourseAssignmentProgress.completed(UPDATED_COMPLETED);

        restCourseAssignmentProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentProgress))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentProgress testCourseAssignmentProgress = courseAssignmentProgressList.get(courseAssignmentProgressList.size() - 1);
        assertThat(testCourseAssignmentProgress.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testCourseAssignmentProgress.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCourseAssignmentProgressWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();

        // Update the courseAssignmentProgress using partial update
        CourseAssignmentProgress partialUpdatedCourseAssignmentProgress = new CourseAssignmentProgress();
        partialUpdatedCourseAssignmentProgress.setId(courseAssignmentProgress.getId());

        partialUpdatedCourseAssignmentProgress.completed(UPDATED_COMPLETED).completedDate(UPDATED_COMPLETED_DATE);

        restCourseAssignmentProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentProgress))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentProgress testCourseAssignmentProgress = courseAssignmentProgressList.get(courseAssignmentProgressList.size() - 1);
        assertThat(testCourseAssignmentProgress.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testCourseAssignmentProgress.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseAssignmentProgressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseAssignmentProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentProgressRepository.findAll().size();
        courseAssignmentProgress.setId(count.incrementAndGet());

        // Create the CourseAssignmentProgress
        CourseAssignmentProgressDTO courseAssignmentProgressDTO = courseAssignmentProgressMapper.toDto(courseAssignmentProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentProgressMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentProgress in the database
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseAssignmentProgress() throws Exception {
        // Initialize the database
        courseAssignmentProgressRepository.saveAndFlush(courseAssignmentProgress);

        int databaseSizeBeforeDelete = courseAssignmentProgressRepository.findAll().size();

        // Delete the courseAssignmentProgress
        restCourseAssignmentProgressMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseAssignmentProgress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseAssignmentProgress> courseAssignmentProgressList = courseAssignmentProgressRepository.findAll();
        assertThat(courseAssignmentProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
