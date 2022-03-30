package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseLevel;
import com.mycompany.myapp.repository.CourseLevelRepository;
import com.mycompany.myapp.service.criteria.CourseLevelCriteria;
import com.mycompany.myapp.service.dto.CourseLevelDTO;
import com.mycompany.myapp.service.mapper.CourseLevelMapper;
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
 * Integration tests for the {@link CourseLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseLevelResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseLevelRepository courseLevelRepository;

    @Autowired
    private CourseLevelMapper courseLevelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseLevelMockMvc;

    private CourseLevel courseLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseLevel createEntity(EntityManager em) {
        CourseLevel courseLevel = new CourseLevel().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).id(DEFAULT_ID);
        return courseLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseLevel createUpdatedEntity(EntityManager em) {
        CourseLevel courseLevel = new CourseLevel().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        return courseLevel;
    }

    @BeforeEach
    public void initTest() {
        courseLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseLevel() throws Exception {
        int databaseSizeBeforeCreate = courseLevelRepository.findAll().size();
        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);
        restCourseLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeCreate + 1);
        CourseLevel testCourseLevel = courseLevelList.get(courseLevelList.size() - 1);
        assertThat(testCourseLevel.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourseLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCourseLevelWithExistingId() throws Exception {
        // Create the CourseLevel with an existing ID
        courseLevel.setId(1L);
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        int databaseSizeBeforeCreate = courseLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseLevels() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCourseLevel() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get the courseLevel
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, courseLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseLevel.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getCourseLevelsByIdFiltering() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        Long id = courseLevel.getId();

        defaultCourseLevelShouldBeFound("id.equals=" + id);
        defaultCourseLevelShouldNotBeFound("id.notEquals=" + id);

        defaultCourseLevelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseLevelShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseLevelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseLevelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title equals to DEFAULT_TITLE
        defaultCourseLevelShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the courseLevelList where title equals to UPDATED_TITLE
        defaultCourseLevelShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title not equals to DEFAULT_TITLE
        defaultCourseLevelShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the courseLevelList where title not equals to UPDATED_TITLE
        defaultCourseLevelShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCourseLevelShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the courseLevelList where title equals to UPDATED_TITLE
        defaultCourseLevelShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title is not null
        defaultCourseLevelShouldBeFound("title.specified=true");

        // Get all the courseLevelList where title is null
        defaultCourseLevelShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleContainsSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title contains DEFAULT_TITLE
        defaultCourseLevelShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the courseLevelList where title contains UPDATED_TITLE
        defaultCourseLevelShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where title does not contain DEFAULT_TITLE
        defaultCourseLevelShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the courseLevelList where title does not contain UPDATED_TITLE
        defaultCourseLevelShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description equals to DEFAULT_DESCRIPTION
        defaultCourseLevelShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the courseLevelList where description equals to UPDATED_DESCRIPTION
        defaultCourseLevelShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description not equals to DEFAULT_DESCRIPTION
        defaultCourseLevelShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the courseLevelList where description not equals to UPDATED_DESCRIPTION
        defaultCourseLevelShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCourseLevelShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the courseLevelList where description equals to UPDATED_DESCRIPTION
        defaultCourseLevelShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description is not null
        defaultCourseLevelShouldBeFound("description.specified=true");

        // Get all the courseLevelList where description is null
        defaultCourseLevelShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description contains DEFAULT_DESCRIPTION
        defaultCourseLevelShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the courseLevelList where description contains UPDATED_DESCRIPTION
        defaultCourseLevelShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseLevelsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        // Get all the courseLevelList where description does not contain DEFAULT_DESCRIPTION
        defaultCourseLevelShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the courseLevelList where description does not contain UPDATED_DESCRIPTION
        defaultCourseLevelShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseLevelShouldBeFound(String filter) throws Exception {
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseLevelShouldNotBeFound(String filter) throws Exception {
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseLevel() throws Exception {
        // Get the courseLevel
        restCourseLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseLevel() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();

        // Update the courseLevel
        CourseLevel updatedCourseLevel = courseLevelRepository.findById(courseLevel.getId()).get();
        // Disconnect from session so that the updates on updatedCourseLevel are not directly saved in db
        em.detach(updatedCourseLevel);
        updatedCourseLevel.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(updatedCourseLevel);

        restCourseLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
        CourseLevel testCourseLevel = courseLevelList.get(courseLevelList.size() - 1);
        assertThat(testCourseLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseLevelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseLevelWithPatch() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();

        // Update the courseLevel using partial update
        CourseLevel partialUpdatedCourseLevel = new CourseLevel();
        partialUpdatedCourseLevel.setId(courseLevel.getId());

        partialUpdatedCourseLevel.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restCourseLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseLevel))
            )
            .andExpect(status().isOk());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
        CourseLevel testCourseLevel = courseLevelList.get(courseLevelList.size() - 1);
        assertThat(testCourseLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCourseLevelWithPatch() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();

        // Update the courseLevel using partial update
        CourseLevel partialUpdatedCourseLevel = new CourseLevel();
        partialUpdatedCourseLevel.setId(courseLevel.getId());

        partialUpdatedCourseLevel.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restCourseLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseLevel))
            )
            .andExpect(status().isOk());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
        CourseLevel testCourseLevel = courseLevelList.get(courseLevelList.size() - 1);
        assertThat(testCourseLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseLevelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseLevel() throws Exception {
        int databaseSizeBeforeUpdate = courseLevelRepository.findAll().size();
        courseLevel.setId(count.incrementAndGet());

        // Create the CourseLevel
        CourseLevelDTO courseLevelDTO = courseLevelMapper.toDto(courseLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseLevelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courseLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseLevel in the database
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseLevel() throws Exception {
        // Initialize the database
        courseLevelRepository.saveAndFlush(courseLevel);

        int databaseSizeBeforeDelete = courseLevelRepository.findAll().size();

        // Delete the courseLevel
        restCourseLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseLevel> courseLevelList = courseLevelRepository.findAll();
        assertThat(courseLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
