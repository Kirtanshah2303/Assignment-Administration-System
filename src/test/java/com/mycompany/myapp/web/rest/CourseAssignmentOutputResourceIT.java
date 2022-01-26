package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.domain.CourseAssignmentOutput;
import com.mycompany.myapp.repository.CourseAssignmentOutputRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentOutputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentOutputMapper;
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
 * Integration tests for the {@link CourseAssignmentOutputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseAssignmentOutputResourceIT {

    private static final String DEFAULT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-assignment-outputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseAssignmentOutputRepository courseAssignmentOutputRepository;

    @Autowired
    private CourseAssignmentOutputMapper courseAssignmentOutputMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseAssignmentOutputMockMvc;

    private CourseAssignmentOutput courseAssignmentOutput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentOutput createEntity(EntityManager em) {
        CourseAssignmentOutput courseAssignmentOutput = new CourseAssignmentOutput().output(DEFAULT_OUTPUT);
        return courseAssignmentOutput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentOutput createUpdatedEntity(EntityManager em) {
        CourseAssignmentOutput courseAssignmentOutput = new CourseAssignmentOutput().output(UPDATED_OUTPUT);
        return courseAssignmentOutput;
    }

    @BeforeEach
    public void initTest() {
        courseAssignmentOutput = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeCreate = courseAssignmentOutputRepository.findAll().size();
        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);
        restCourseAssignmentOutputMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeCreate + 1);
        CourseAssignmentOutput testCourseAssignmentOutput = courseAssignmentOutputList.get(courseAssignmentOutputList.size() - 1);
        assertThat(testCourseAssignmentOutput.getOutput()).isEqualTo(DEFAULT_OUTPUT);
    }

    @Test
    @Transactional
    void createCourseAssignmentOutputWithExistingId() throws Exception {
        // Create the CourseAssignmentOutput with an existing ID
        courseAssignmentOutput.setId(1L);
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        int databaseSizeBeforeCreate = courseAssignmentOutputRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseAssignmentOutputMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputs() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)));
    }

    @Test
    @Transactional
    void getCourseAssignmentOutput() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get the courseAssignmentOutput
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL_ID, courseAssignmentOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseAssignmentOutput.getId().intValue()))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT));
    }

    @Test
    @Transactional
    void getCourseAssignmentOutputsByIdFiltering() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        Long id = courseAssignmentOutput.getId();

        defaultCourseAssignmentOutputShouldBeFound("id.equals=" + id);
        defaultCourseAssignmentOutputShouldNotBeFound("id.notEquals=" + id);

        defaultCourseAssignmentOutputShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseAssignmentOutputShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseAssignmentOutputShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseAssignmentOutputShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output equals to DEFAULT_OUTPUT
        defaultCourseAssignmentOutputShouldBeFound("output.equals=" + DEFAULT_OUTPUT);

        // Get all the courseAssignmentOutputList where output equals to UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldNotBeFound("output.equals=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output not equals to DEFAULT_OUTPUT
        defaultCourseAssignmentOutputShouldNotBeFound("output.notEquals=" + DEFAULT_OUTPUT);

        // Get all the courseAssignmentOutputList where output not equals to UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldBeFound("output.notEquals=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output in DEFAULT_OUTPUT or UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldBeFound("output.in=" + DEFAULT_OUTPUT + "," + UPDATED_OUTPUT);

        // Get all the courseAssignmentOutputList where output equals to UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldNotBeFound("output.in=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output is not null
        defaultCourseAssignmentOutputShouldBeFound("output.specified=true");

        // Get all the courseAssignmentOutputList where output is null
        defaultCourseAssignmentOutputShouldNotBeFound("output.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output contains DEFAULT_OUTPUT
        defaultCourseAssignmentOutputShouldBeFound("output.contains=" + DEFAULT_OUTPUT);

        // Get all the courseAssignmentOutputList where output contains UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldNotBeFound("output.contains=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByOutputNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        // Get all the courseAssignmentOutputList where output does not contain DEFAULT_OUTPUT
        defaultCourseAssignmentOutputShouldNotBeFound("output.doesNotContain=" + DEFAULT_OUTPUT);

        // Get all the courseAssignmentOutputList where output does not contain UPDATED_OUTPUT
        defaultCourseAssignmentOutputShouldBeFound("output.doesNotContain=" + UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentOutputsByCourseAssignmentIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);
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
        courseAssignmentOutput.setCourseAssignment(courseAssignment);
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);
        Long courseAssignmentId = courseAssignment.getId();

        // Get all the courseAssignmentOutputList where courseAssignment equals to courseAssignmentId
        defaultCourseAssignmentOutputShouldBeFound("courseAssignmentId.equals=" + courseAssignmentId);

        // Get all the courseAssignmentOutputList where courseAssignment equals to (courseAssignmentId + 1)
        defaultCourseAssignmentOutputShouldNotBeFound("courseAssignmentId.equals=" + (courseAssignmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseAssignmentOutputShouldBeFound(String filter) throws Exception {
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)));

        // Check, that the count call also returns 1
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseAssignmentOutputShouldNotBeFound(String filter) throws Exception {
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseAssignmentOutputMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseAssignmentOutput() throws Exception {
        // Get the courseAssignmentOutput
        restCourseAssignmentOutputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseAssignmentOutput() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();

        // Update the courseAssignmentOutput
        CourseAssignmentOutput updatedCourseAssignmentOutput = courseAssignmentOutputRepository
            .findById(courseAssignmentOutput.getId())
            .get();
        // Disconnect from session so that the updates on updatedCourseAssignmentOutput are not directly saved in db
        em.detach(updatedCourseAssignmentOutput);
        updatedCourseAssignmentOutput.output(UPDATED_OUTPUT);
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(updatedCourseAssignmentOutput);

        restCourseAssignmentOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentOutputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentOutput testCourseAssignmentOutput = courseAssignmentOutputList.get(courseAssignmentOutputList.size() - 1);
        assertThat(testCourseAssignmentOutput.getOutput()).isEqualTo(UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void putNonExistingCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentOutputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseAssignmentOutputWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();

        // Update the courseAssignmentOutput using partial update
        CourseAssignmentOutput partialUpdatedCourseAssignmentOutput = new CourseAssignmentOutput();
        partialUpdatedCourseAssignmentOutput.setId(courseAssignmentOutput.getId());

        restCourseAssignmentOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentOutput))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentOutput testCourseAssignmentOutput = courseAssignmentOutputList.get(courseAssignmentOutputList.size() - 1);
        assertThat(testCourseAssignmentOutput.getOutput()).isEqualTo(DEFAULT_OUTPUT);
    }

    @Test
    @Transactional
    void fullUpdateCourseAssignmentOutputWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();

        // Update the courseAssignmentOutput using partial update
        CourseAssignmentOutput partialUpdatedCourseAssignmentOutput = new CourseAssignmentOutput();
        partialUpdatedCourseAssignmentOutput.setId(courseAssignmentOutput.getId());

        partialUpdatedCourseAssignmentOutput.output(UPDATED_OUTPUT);

        restCourseAssignmentOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentOutput))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentOutput testCourseAssignmentOutput = courseAssignmentOutputList.get(courseAssignmentOutputList.size() - 1);
        assertThat(testCourseAssignmentOutput.getOutput()).isEqualTo(UPDATED_OUTPUT);
    }

    @Test
    @Transactional
    void patchNonExistingCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseAssignmentOutputDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseAssignmentOutput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentOutputRepository.findAll().size();
        courseAssignmentOutput.setId(count.incrementAndGet());

        // Create the CourseAssignmentOutput
        CourseAssignmentOutputDTO courseAssignmentOutputDTO = courseAssignmentOutputMapper.toDto(courseAssignmentOutput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentOutputMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentOutputDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentOutput in the database
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseAssignmentOutput() throws Exception {
        // Initialize the database
        courseAssignmentOutputRepository.saveAndFlush(courseAssignmentOutput);

        int databaseSizeBeforeDelete = courseAssignmentOutputRepository.findAll().size();

        // Delete the courseAssignmentOutput
        restCourseAssignmentOutputMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseAssignmentOutput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseAssignmentOutput> courseAssignmentOutputList = courseAssignmentOutputRepository.findAll();
        assertThat(courseAssignmentOutputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
