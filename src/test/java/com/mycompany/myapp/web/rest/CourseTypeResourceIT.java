package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseType;
import com.mycompany.myapp.repository.CourseTypeRepository;
import com.mycompany.myapp.service.criteria.CourseTypeCriteria;
import com.mycompany.myapp.service.dto.CourseTypeDTO;
import com.mycompany.myapp.service.mapper.CourseTypeMapper;
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
 * Integration tests for the {@link CourseTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseTypeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseTypeMockMvc;

    private CourseType courseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseType createEntity(EntityManager em) {
        CourseType courseType = new CourseType().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION);
        return courseType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseType createUpdatedEntity(EntityManager em) {
        CourseType courseType = new CourseType().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        return courseType;
    }

    @BeforeEach
    public void initTest() {
        courseType = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseType() throws Exception {
        int databaseSizeBeforeCreate = courseTypeRepository.findAll().size();
        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);
        restCourseTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourseType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCourseTypeWithExistingId() throws Exception {
        // Create the CourseType with an existing ID
        courseType.setId(1L);
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        int databaseSizeBeforeCreate = courseTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseTypeRepository.findAll().size();
        // set the field null
        courseType.setTitle(null);

        // Create the CourseType, which fails.
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        restCourseTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseTypes() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCourseType() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get the courseType
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, courseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getCourseTypesByIdFiltering() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        Long id = courseType.getId();

        defaultCourseTypeShouldBeFound("id.equals=" + id);
        defaultCourseTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCourseTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title equals to DEFAULT_TITLE
        defaultCourseTypeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the courseTypeList where title equals to UPDATED_TITLE
        defaultCourseTypeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title not equals to DEFAULT_TITLE
        defaultCourseTypeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the courseTypeList where title not equals to UPDATED_TITLE
        defaultCourseTypeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCourseTypeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the courseTypeList where title equals to UPDATED_TITLE
        defaultCourseTypeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title is not null
        defaultCourseTypeShouldBeFound("title.specified=true");

        // Get all the courseTypeList where title is null
        defaultCourseTypeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleContainsSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title contains DEFAULT_TITLE
        defaultCourseTypeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the courseTypeList where title contains UPDATED_TITLE
        defaultCourseTypeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseTypesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where title does not contain DEFAULT_TITLE
        defaultCourseTypeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the courseTypeList where title does not contain UPDATED_TITLE
        defaultCourseTypeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCourseTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the courseTypeList where description equals to UPDATED_DESCRIPTION
        defaultCourseTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultCourseTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the courseTypeList where description not equals to UPDATED_DESCRIPTION
        defaultCourseTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCourseTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the courseTypeList where description equals to UPDATED_DESCRIPTION
        defaultCourseTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description is not null
        defaultCourseTypeShouldBeFound("description.specified=true");

        // Get all the courseTypeList where description is null
        defaultCourseTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description contains DEFAULT_DESCRIPTION
        defaultCourseTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the courseTypeList where description contains UPDATED_DESCRIPTION
        defaultCourseTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCourseTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the courseTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCourseTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseTypeShouldBeFound(String filter) throws Exception {
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseTypeShouldNotBeFound(String filter) throws Exception {
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseType() throws Exception {
        // Get the courseType
        restCourseTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseType() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();

        // Update the courseType
        CourseType updatedCourseType = courseTypeRepository.findById(courseType.getId()).get();
        // Disconnect from session so that the updates on updatedCourseType are not directly saved in db
        em.detach(updatedCourseType);
        updatedCourseType.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(updatedCourseType);

        restCourseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseTypeWithPatch() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();

        // Update the courseType using partial update
        CourseType partialUpdatedCourseType = new CourseType();
        partialUpdatedCourseType.setId(courseType.getId());

        restCourseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseType))
            )
            .andExpect(status().isOk());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourseType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCourseTypeWithPatch() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();

        // Update the courseType using partial update
        CourseType partialUpdatedCourseType = new CourseType();
        partialUpdatedCourseType.setId(courseType.getId());

        partialUpdatedCourseType.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restCourseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseType))
            )
            .andExpect(status().isOk());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourseType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();
        courseType.setId(count.incrementAndGet());

        // Create the CourseType
        CourseTypeDTO courseTypeDTO = courseTypeMapper.toDto(courseType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courseTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseType() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        int databaseSizeBeforeDelete = courseTypeRepository.findAll().size();

        // Delete the courseType
        restCourseTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
