package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseSession;
import com.mycompany.myapp.domain.CourseSessionProgress;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseSessionProgressRepository;
import com.mycompany.myapp.service.criteria.CourseSessionProgressCriteria;
import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
import com.mycompany.myapp.service.mapper.CourseSessionProgressMapper;
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
 * Integration tests for the {@link CourseSessionProgressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseSessionProgressResourceIT {

    private static final Long DEFAULT_WATCH_SECONDS = 1L;
    private static final Long UPDATED_WATCH_SECONDS = 2L;
    private static final Long SMALLER_WATCH_SECONDS = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/course-session-progresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseSessionProgressRepository courseSessionProgressRepository;

    @Autowired
    private CourseSessionProgressMapper courseSessionProgressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseSessionProgressMockMvc;

    private CourseSessionProgress courseSessionProgress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSessionProgress createEntity(EntityManager em) {
        CourseSessionProgress courseSessionProgress = new CourseSessionProgress().watchSeconds(DEFAULT_WATCH_SECONDS);
        return courseSessionProgress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSessionProgress createUpdatedEntity(EntityManager em) {
        CourseSessionProgress courseSessionProgress = new CourseSessionProgress().watchSeconds(UPDATED_WATCH_SECONDS);
        return courseSessionProgress;
    }

    @BeforeEach
    public void initTest() {
        courseSessionProgress = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseSessionProgress() throws Exception {
        int databaseSizeBeforeCreate = courseSessionProgressRepository.findAll().size();
        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);
        restCourseSessionProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeCreate + 1);
        CourseSessionProgress testCourseSessionProgress = courseSessionProgressList.get(courseSessionProgressList.size() - 1);
        assertThat(testCourseSessionProgress.getWatchSeconds()).isEqualTo(DEFAULT_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void createCourseSessionProgressWithExistingId() throws Exception {
        // Create the CourseSessionProgress with an existing ID
        courseSessionProgress.setId(1L);
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        int databaseSizeBeforeCreate = courseSessionProgressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseSessionProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWatchSecondsIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSessionProgressRepository.findAll().size();
        // set the field null
        courseSessionProgress.setWatchSeconds(null);

        // Create the CourseSessionProgress, which fails.
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        restCourseSessionProgressMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgresses() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSessionProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].watchSeconds").value(hasItem(DEFAULT_WATCH_SECONDS.intValue())));
    }

    @Test
    @Transactional
    void getCourseSessionProgress() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get the courseSessionProgress
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL_ID, courseSessionProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseSessionProgress.getId().intValue()))
            .andExpect(jsonPath("$.watchSeconds").value(DEFAULT_WATCH_SECONDS.intValue()));
    }

    @Test
    @Transactional
    void getCourseSessionProgressesByIdFiltering() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        Long id = courseSessionProgress.getId();

        defaultCourseSessionProgressShouldBeFound("id.equals=" + id);
        defaultCourseSessionProgressShouldNotBeFound("id.notEquals=" + id);

        defaultCourseSessionProgressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseSessionProgressShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseSessionProgressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseSessionProgressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds equals to DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.equals=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds equals to UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.equals=" + UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds not equals to DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.notEquals=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds not equals to UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.notEquals=" + UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsInShouldWork() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds in DEFAULT_WATCH_SECONDS or UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.in=" + DEFAULT_WATCH_SECONDS + "," + UPDATED_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds equals to UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.in=" + UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds is not null
        defaultCourseSessionProgressShouldBeFound("watchSeconds.specified=true");

        // Get all the courseSessionProgressList where watchSeconds is null
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds is greater than or equal to DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.greaterThanOrEqual=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds is greater than or equal to UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.greaterThanOrEqual=" + UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds is less than or equal to DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.lessThanOrEqual=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds is less than or equal to SMALLER_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.lessThanOrEqual=" + SMALLER_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsLessThanSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds is less than DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.lessThan=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds is less than UPDATED_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.lessThan=" + UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByWatchSecondsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        // Get all the courseSessionProgressList where watchSeconds is greater than DEFAULT_WATCH_SECONDS
        defaultCourseSessionProgressShouldNotBeFound("watchSeconds.greaterThan=" + DEFAULT_WATCH_SECONDS);

        // Get all the courseSessionProgressList where watchSeconds is greater than SMALLER_WATCH_SECONDS
        defaultCourseSessionProgressShouldBeFound("watchSeconds.greaterThan=" + SMALLER_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);
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
        courseSessionProgress.setUser(user);
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);
        Long userId = user.getId();

        // Get all the courseSessionProgressList where user equals to userId
        defaultCourseSessionProgressShouldBeFound("userId.equals=" + userId);

        // Get all the courseSessionProgressList where user equals to (userId + 1)
        defaultCourseSessionProgressShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllCourseSessionProgressesByCourseSessionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);
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
        courseSessionProgress.setCourseSession(courseSession);
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);
        Long courseSessionId = courseSession.getId();

        // Get all the courseSessionProgressList where courseSession equals to courseSessionId
        defaultCourseSessionProgressShouldBeFound("courseSessionId.equals=" + courseSessionId);

        // Get all the courseSessionProgressList where courseSession equals to (courseSessionId + 1)
        defaultCourseSessionProgressShouldNotBeFound("courseSessionId.equals=" + (courseSessionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseSessionProgressShouldBeFound(String filter) throws Exception {
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSessionProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].watchSeconds").value(hasItem(DEFAULT_WATCH_SECONDS.intValue())));

        // Check, that the count call also returns 1
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseSessionProgressShouldNotBeFound(String filter) throws Exception {
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseSessionProgressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseSessionProgress() throws Exception {
        // Get the courseSessionProgress
        restCourseSessionProgressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseSessionProgress() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();

        // Update the courseSessionProgress
        CourseSessionProgress updatedCourseSessionProgress = courseSessionProgressRepository.findById(courseSessionProgress.getId()).get();
        // Disconnect from session so that the updates on updatedCourseSessionProgress are not directly saved in db
        em.detach(updatedCourseSessionProgress);
        updatedCourseSessionProgress.watchSeconds(UPDATED_WATCH_SECONDS);
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(updatedCourseSessionProgress);

        restCourseSessionProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSessionProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseSessionProgress testCourseSessionProgress = courseSessionProgressList.get(courseSessionProgressList.size() - 1);
        assertThat(testCourseSessionProgress.getWatchSeconds()).isEqualTo(UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void putNonExistingCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSessionProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseSessionProgressWithPatch() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();

        // Update the courseSessionProgress using partial update
        CourseSessionProgress partialUpdatedCourseSessionProgress = new CourseSessionProgress();
        partialUpdatedCourseSessionProgress.setId(courseSessionProgress.getId());

        restCourseSessionProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSessionProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSessionProgress))
            )
            .andExpect(status().isOk());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseSessionProgress testCourseSessionProgress = courseSessionProgressList.get(courseSessionProgressList.size() - 1);
        assertThat(testCourseSessionProgress.getWatchSeconds()).isEqualTo(DEFAULT_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void fullUpdateCourseSessionProgressWithPatch() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();

        // Update the courseSessionProgress using partial update
        CourseSessionProgress partialUpdatedCourseSessionProgress = new CourseSessionProgress();
        partialUpdatedCourseSessionProgress.setId(courseSessionProgress.getId());

        partialUpdatedCourseSessionProgress.watchSeconds(UPDATED_WATCH_SECONDS);

        restCourseSessionProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSessionProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSessionProgress))
            )
            .andExpect(status().isOk());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
        CourseSessionProgress testCourseSessionProgress = courseSessionProgressList.get(courseSessionProgressList.size() - 1);
        assertThat(testCourseSessionProgress.getWatchSeconds()).isEqualTo(UPDATED_WATCH_SECONDS);
    }

    @Test
    @Transactional
    void patchNonExistingCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseSessionProgressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseSessionProgress() throws Exception {
        int databaseSizeBeforeUpdate = courseSessionProgressRepository.findAll().size();
        courseSessionProgress.setId(count.incrementAndGet());

        // Create the CourseSessionProgress
        CourseSessionProgressDTO courseSessionProgressDTO = courseSessionProgressMapper.toDto(courseSessionProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSessionProgressMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSessionProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSessionProgress in the database
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseSessionProgress() throws Exception {
        // Initialize the database
        courseSessionProgressRepository.saveAndFlush(courseSessionProgress);

        int databaseSizeBeforeDelete = courseSessionProgressRepository.findAll().size();

        // Delete the courseSessionProgress
        restCourseSessionProgressMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseSessionProgress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseSessionProgress> courseSessionProgressList = courseSessionProgressRepository.findAll();
        assertThat(courseSessionProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
