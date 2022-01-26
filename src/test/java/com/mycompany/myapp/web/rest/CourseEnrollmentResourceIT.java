package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseEnrollment;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseEnrollmentRepository;
import com.mycompany.myapp.service.criteria.CourseEnrollmentCriteria;
import com.mycompany.myapp.service.dto.CourseEnrollmentDTO;
import com.mycompany.myapp.service.mapper.CourseEnrollmentMapper;
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
 * Integration tests for the {@link CourseEnrollmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseEnrollmentResourceIT {

    private static final LocalDate DEFAULT_ENROLLEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENROLLEMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ENROLLEMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_LAST_ACCESSED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_ACCESSED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_ACCESSED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/course-enrollments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseEnrollmentMockMvc;

    private CourseEnrollment courseEnrollment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseEnrollment createEntity(EntityManager em) {
        CourseEnrollment courseEnrollment = new CourseEnrollment()
            .enrollementDate(DEFAULT_ENROLLEMENT_DATE)
            .lastAccessedDate(DEFAULT_LAST_ACCESSED_DATE);
        return courseEnrollment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseEnrollment createUpdatedEntity(EntityManager em) {
        CourseEnrollment courseEnrollment = new CourseEnrollment()
            .enrollementDate(UPDATED_ENROLLEMENT_DATE)
            .lastAccessedDate(UPDATED_LAST_ACCESSED_DATE);
        return courseEnrollment;
    }

    @BeforeEach
    public void initTest() {
        courseEnrollment = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseEnrollment() throws Exception {
        int databaseSizeBeforeCreate = courseEnrollmentRepository.findAll().size();
        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);
        restCourseEnrollmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeCreate + 1);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getEnrollementDate()).isEqualTo(DEFAULT_ENROLLEMENT_DATE);
        assertThat(testCourseEnrollment.getLastAccessedDate()).isEqualTo(DEFAULT_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void createCourseEnrollmentWithExistingId() throws Exception {
        // Create the CourseEnrollment with an existing ID
        courseEnrollment.setId(1L);
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        int databaseSizeBeforeCreate = courseEnrollmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseEnrollmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEnrollementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseEnrollmentRepository.findAll().size();
        // set the field null
        courseEnrollment.setEnrollementDate(null);

        // Create the CourseEnrollment, which fails.
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        restCourseEnrollmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastAccessedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseEnrollmentRepository.findAll().size();
        // set the field null
        courseEnrollment.setLastAccessedDate(null);

        // Create the CourseEnrollment, which fails.
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        restCourseEnrollmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseEnrollments() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enrollementDate").value(hasItem(DEFAULT_ENROLLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastAccessedDate").value(hasItem(DEFAULT_LAST_ACCESSED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get the courseEnrollment
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL_ID, courseEnrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseEnrollment.getId().intValue()))
            .andExpect(jsonPath("$.enrollementDate").value(DEFAULT_ENROLLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.lastAccessedDate").value(DEFAULT_LAST_ACCESSED_DATE.toString()));
    }

    @Test
    @Transactional
    void getCourseEnrollmentsByIdFiltering() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        Long id = courseEnrollment.getId();

        defaultCourseEnrollmentShouldBeFound("id.equals=" + id);
        defaultCourseEnrollmentShouldNotBeFound("id.notEquals=" + id);

        defaultCourseEnrollmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseEnrollmentShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseEnrollmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseEnrollmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate equals to DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.equals=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate equals to UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.equals=" + UPDATED_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate not equals to DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.notEquals=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate not equals to UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.notEquals=" + UPDATED_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate in DEFAULT_ENROLLEMENT_DATE or UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.in=" + DEFAULT_ENROLLEMENT_DATE + "," + UPDATED_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate equals to UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.in=" + UPDATED_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate is not null
        defaultCourseEnrollmentShouldBeFound("enrollementDate.specified=true");

        // Get all the courseEnrollmentList where enrollementDate is null
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate is greater than or equal to DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.greaterThanOrEqual=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate is greater than or equal to UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.greaterThanOrEqual=" + UPDATED_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate is less than or equal to DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.lessThanOrEqual=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate is less than or equal to SMALLER_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.lessThanOrEqual=" + SMALLER_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate is less than DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.lessThan=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate is less than UPDATED_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.lessThan=" + UPDATED_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByEnrollementDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where enrollementDate is greater than DEFAULT_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldNotBeFound("enrollementDate.greaterThan=" + DEFAULT_ENROLLEMENT_DATE);

        // Get all the courseEnrollmentList where enrollementDate is greater than SMALLER_ENROLLEMENT_DATE
        defaultCourseEnrollmentShouldBeFound("enrollementDate.greaterThan=" + SMALLER_ENROLLEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate equals to DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.equals=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate equals to UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.equals=" + UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate not equals to DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.notEquals=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate not equals to UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.notEquals=" + UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsInShouldWork() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate in DEFAULT_LAST_ACCESSED_DATE or UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.in=" + DEFAULT_LAST_ACCESSED_DATE + "," + UPDATED_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate equals to UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.in=" + UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate is not null
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.specified=true");

        // Get all the courseEnrollmentList where lastAccessedDate is null
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate is greater than or equal to DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.greaterThanOrEqual=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate is greater than or equal to UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.greaterThanOrEqual=" + UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate is less than or equal to DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.lessThanOrEqual=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate is less than or equal to SMALLER_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.lessThanOrEqual=" + SMALLER_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate is less than DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.lessThan=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate is less than UPDATED_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.lessThan=" + UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByLastAccessedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        // Get all the courseEnrollmentList where lastAccessedDate is greater than DEFAULT_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldNotBeFound("lastAccessedDate.greaterThan=" + DEFAULT_LAST_ACCESSED_DATE);

        // Get all the courseEnrollmentList where lastAccessedDate is greater than SMALLER_LAST_ACCESSED_DATE
        defaultCourseEnrollmentShouldBeFound("lastAccessedDate.greaterThan=" + SMALLER_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
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
        courseEnrollment.setUser(user);
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        Long userId = user.getId();

        // Get all the courseEnrollmentList where user equals to userId
        defaultCourseEnrollmentShouldBeFound("userId.equals=" + userId);

        // Get all the courseEnrollmentList where user equals to (userId + 1)
        defaultCourseEnrollmentShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllCourseEnrollmentsByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            course = CourseResourceIT.createEntity(em);
            em.persist(course);
            em.flush();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        em.persist(course);
        em.flush();
        courseEnrollment.setCourse(course);
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);
        Long courseId = course.getId();

        // Get all the courseEnrollmentList where course equals to courseId
        defaultCourseEnrollmentShouldBeFound("courseId.equals=" + courseId);

        // Get all the courseEnrollmentList where course equals to (courseId + 1)
        defaultCourseEnrollmentShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseEnrollmentShouldBeFound(String filter) throws Exception {
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseEnrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enrollementDate").value(hasItem(DEFAULT_ENROLLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastAccessedDate").value(hasItem(DEFAULT_LAST_ACCESSED_DATE.toString())));

        // Check, that the count call also returns 1
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseEnrollmentShouldNotBeFound(String filter) throws Exception {
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseEnrollmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseEnrollment() throws Exception {
        // Get the courseEnrollment
        restCourseEnrollmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();

        // Update the courseEnrollment
        CourseEnrollment updatedCourseEnrollment = courseEnrollmentRepository.findById(courseEnrollment.getId()).get();
        // Disconnect from session so that the updates on updatedCourseEnrollment are not directly saved in db
        em.detach(updatedCourseEnrollment);
        updatedCourseEnrollment.enrollementDate(UPDATED_ENROLLEMENT_DATE).lastAccessedDate(UPDATED_LAST_ACCESSED_DATE);
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(updatedCourseEnrollment);

        restCourseEnrollmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseEnrollmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getEnrollementDate()).isEqualTo(UPDATED_ENROLLEMENT_DATE);
        assertThat(testCourseEnrollment.getLastAccessedDate()).isEqualTo(UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseEnrollmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseEnrollmentWithPatch() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();

        // Update the courseEnrollment using partial update
        CourseEnrollment partialUpdatedCourseEnrollment = new CourseEnrollment();
        partialUpdatedCourseEnrollment.setId(courseEnrollment.getId());

        partialUpdatedCourseEnrollment.enrollementDate(UPDATED_ENROLLEMENT_DATE);

        restCourseEnrollmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseEnrollment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseEnrollment))
            )
            .andExpect(status().isOk());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getEnrollementDate()).isEqualTo(UPDATED_ENROLLEMENT_DATE);
        assertThat(testCourseEnrollment.getLastAccessedDate()).isEqualTo(DEFAULT_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCourseEnrollmentWithPatch() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();

        // Update the courseEnrollment using partial update
        CourseEnrollment partialUpdatedCourseEnrollment = new CourseEnrollment();
        partialUpdatedCourseEnrollment.setId(courseEnrollment.getId());

        partialUpdatedCourseEnrollment.enrollementDate(UPDATED_ENROLLEMENT_DATE).lastAccessedDate(UPDATED_LAST_ACCESSED_DATE);

        restCourseEnrollmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseEnrollment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseEnrollment))
            )
            .andExpect(status().isOk());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
        CourseEnrollment testCourseEnrollment = courseEnrollmentList.get(courseEnrollmentList.size() - 1);
        assertThat(testCourseEnrollment.getEnrollementDate()).isEqualTo(UPDATED_ENROLLEMENT_DATE);
        assertThat(testCourseEnrollment.getLastAccessedDate()).isEqualTo(UPDATED_LAST_ACCESSED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseEnrollmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = courseEnrollmentRepository.findAll().size();
        courseEnrollment.setId(count.incrementAndGet());

        // Create the CourseEnrollment
        CourseEnrollmentDTO courseEnrollmentDTO = courseEnrollmentMapper.toDto(courseEnrollment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseEnrollmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseEnrollmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseEnrollment in the database
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseEnrollment() throws Exception {
        // Initialize the database
        courseEnrollmentRepository.saveAndFlush(courseEnrollment);

        int databaseSizeBeforeDelete = courseEnrollmentRepository.findAll().size();

        // Delete the courseEnrollment
        restCourseEnrollmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseEnrollment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseEnrollment> courseEnrollmentList = courseEnrollmentRepository.findAll();
        assertThat(courseEnrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
