package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.domain.CourseAssignmentInput;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CourseAssignmentInputRepository;
import com.mycompany.myapp.service.criteria.CourseAssignmentInputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
import com.mycompany.myapp.service.mapper.CourseAssignmentInputMapper;
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
 * Integration tests for the {@link CourseAssignmentInputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseAssignmentInputResourceIT {

    private static final String DEFAULT_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_INPUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-assignment-inputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseAssignmentInputRepository courseAssignmentInputRepository;

    @Autowired
    private CourseAssignmentInputMapper courseAssignmentInputMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseAssignmentInputMockMvc;

    private CourseAssignmentInput courseAssignmentInput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentInput createEntity(EntityManager em) {
        CourseAssignmentInput courseAssignmentInput = new CourseAssignmentInput().input(DEFAULT_INPUT);
        return courseAssignmentInput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseAssignmentInput createUpdatedEntity(EntityManager em) {
        CourseAssignmentInput courseAssignmentInput = new CourseAssignmentInput().input(UPDATED_INPUT);
        return courseAssignmentInput;
    }

    @BeforeEach
    public void initTest() {
        courseAssignmentInput = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeCreate = courseAssignmentInputRepository.findAll().size();
        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);
        restCourseAssignmentInputMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeCreate + 1);
        CourseAssignmentInput testCourseAssignmentInput = courseAssignmentInputList.get(courseAssignmentInputList.size() - 1);
        assertThat(testCourseAssignmentInput.getInput()).isEqualTo(DEFAULT_INPUT);
    }

    @Test
    @Transactional
    void createCourseAssignmentInputWithExistingId() throws Exception {
        // Create the CourseAssignmentInput with an existing ID
        courseAssignmentInput.setId(1L);
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        int databaseSizeBeforeCreate = courseAssignmentInputRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseAssignmentInputMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputs() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)));
    }

    @Test
    @Transactional
    void getCourseAssignmentInput() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get the courseAssignmentInput
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL_ID, courseAssignmentInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseAssignmentInput.getId().intValue()))
            .andExpect(jsonPath("$.input").value(DEFAULT_INPUT));
    }

    @Test
    @Transactional
    void getCourseAssignmentInputsByIdFiltering() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        Long id = courseAssignmentInput.getId();

        defaultCourseAssignmentInputShouldBeFound("id.equals=" + id);
        defaultCourseAssignmentInputShouldNotBeFound("id.notEquals=" + id);

        defaultCourseAssignmentInputShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseAssignmentInputShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseAssignmentInputShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseAssignmentInputShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input equals to DEFAULT_INPUT
        defaultCourseAssignmentInputShouldBeFound("input.equals=" + DEFAULT_INPUT);

        // Get all the courseAssignmentInputList where input equals to UPDATED_INPUT
        defaultCourseAssignmentInputShouldNotBeFound("input.equals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input not equals to DEFAULT_INPUT
        defaultCourseAssignmentInputShouldNotBeFound("input.notEquals=" + DEFAULT_INPUT);

        // Get all the courseAssignmentInputList where input not equals to UPDATED_INPUT
        defaultCourseAssignmentInputShouldBeFound("input.notEquals=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputIsInShouldWork() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input in DEFAULT_INPUT or UPDATED_INPUT
        defaultCourseAssignmentInputShouldBeFound("input.in=" + DEFAULT_INPUT + "," + UPDATED_INPUT);

        // Get all the courseAssignmentInputList where input equals to UPDATED_INPUT
        defaultCourseAssignmentInputShouldNotBeFound("input.in=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input is not null
        defaultCourseAssignmentInputShouldBeFound("input.specified=true");

        // Get all the courseAssignmentInputList where input is null
        defaultCourseAssignmentInputShouldNotBeFound("input.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input contains DEFAULT_INPUT
        defaultCourseAssignmentInputShouldBeFound("input.contains=" + DEFAULT_INPUT);

        // Get all the courseAssignmentInputList where input contains UPDATED_INPUT
        defaultCourseAssignmentInputShouldNotBeFound("input.contains=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByInputNotContainsSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        // Get all the courseAssignmentInputList where input does not contain DEFAULT_INPUT
        defaultCourseAssignmentInputShouldNotBeFound("input.doesNotContain=" + DEFAULT_INPUT);

        // Get all the courseAssignmentInputList where input does not contain UPDATED_INPUT
        defaultCourseAssignmentInputShouldBeFound("input.doesNotContain=" + UPDATED_INPUT);
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByCourseAssignmentIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);
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
        courseAssignmentInput.setCourseAssignment(courseAssignment);
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);
        Long courseAssignmentId = courseAssignment.getId();

        // Get all the courseAssignmentInputList where courseAssignment equals to courseAssignmentId
        defaultCourseAssignmentInputShouldBeFound("courseAssignmentId.equals=" + courseAssignmentId);

        // Get all the courseAssignmentInputList where courseAssignment equals to (courseAssignmentId + 1)
        defaultCourseAssignmentInputShouldNotBeFound("courseAssignmentId.equals=" + (courseAssignmentId + 1));
    }

    @Test
    @Transactional
    void getAllCourseAssignmentInputsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);
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
        courseAssignmentInput.setUser(user);
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);
        Long userId = user.getId();

        // Get all the courseAssignmentInputList where user equals to userId
        defaultCourseAssignmentInputShouldBeFound("userId.equals=" + userId);

        // Get all the courseAssignmentInputList where user equals to (userId + 1)
        defaultCourseAssignmentInputShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseAssignmentInputShouldBeFound(String filter) throws Exception {
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseAssignmentInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].input").value(hasItem(DEFAULT_INPUT)));

        // Check, that the count call also returns 1
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseAssignmentInputShouldNotBeFound(String filter) throws Exception {
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseAssignmentInputMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseAssignmentInput() throws Exception {
        // Get the courseAssignmentInput
        restCourseAssignmentInputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseAssignmentInput() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();

        // Update the courseAssignmentInput
        CourseAssignmentInput updatedCourseAssignmentInput = courseAssignmentInputRepository.findById(courseAssignmentInput.getId()).get();
        // Disconnect from session so that the updates on updatedCourseAssignmentInput are not directly saved in db
        em.detach(updatedCourseAssignmentInput);
        updatedCourseAssignmentInput.input(UPDATED_INPUT);
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(updatedCourseAssignmentInput);

        restCourseAssignmentInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentInputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentInput testCourseAssignmentInput = courseAssignmentInputList.get(courseAssignmentInputList.size() - 1);
        assertThat(testCourseAssignmentInput.getInput()).isEqualTo(UPDATED_INPUT);
    }

    @Test
    @Transactional
    void putNonExistingCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseAssignmentInputDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseAssignmentInputWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();

        // Update the courseAssignmentInput using partial update
        CourseAssignmentInput partialUpdatedCourseAssignmentInput = new CourseAssignmentInput();
        partialUpdatedCourseAssignmentInput.setId(courseAssignmentInput.getId());

        partialUpdatedCourseAssignmentInput.input(UPDATED_INPUT);

        restCourseAssignmentInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentInput))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentInput testCourseAssignmentInput = courseAssignmentInputList.get(courseAssignmentInputList.size() - 1);
        assertThat(testCourseAssignmentInput.getInput()).isEqualTo(UPDATED_INPUT);
    }

    @Test
    @Transactional
    void fullUpdateCourseAssignmentInputWithPatch() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();

        // Update the courseAssignmentInput using partial update
        CourseAssignmentInput partialUpdatedCourseAssignmentInput = new CourseAssignmentInput();
        partialUpdatedCourseAssignmentInput.setId(courseAssignmentInput.getId());

        partialUpdatedCourseAssignmentInput.input(UPDATED_INPUT);

        restCourseAssignmentInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseAssignmentInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseAssignmentInput))
            )
            .andExpect(status().isOk());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
        CourseAssignmentInput testCourseAssignmentInput = courseAssignmentInputList.get(courseAssignmentInputList.size() - 1);
        assertThat(testCourseAssignmentInput.getInput()).isEqualTo(UPDATED_INPUT);
    }

    @Test
    @Transactional
    void patchNonExistingCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseAssignmentInputDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseAssignmentInput() throws Exception {
        int databaseSizeBeforeUpdate = courseAssignmentInputRepository.findAll().size();
        courseAssignmentInput.setId(count.incrementAndGet());

        // Create the CourseAssignmentInput
        CourseAssignmentInputDTO courseAssignmentInputDTO = courseAssignmentInputMapper.toDto(courseAssignmentInput);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseAssignmentInputMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseAssignmentInputDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseAssignmentInput in the database
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseAssignmentInput() throws Exception {
        // Initialize the database
        courseAssignmentInputRepository.saveAndFlush(courseAssignmentInput);

        int databaseSizeBeforeDelete = courseAssignmentInputRepository.findAll().size();

        // Delete the courseAssignmentInput
        restCourseAssignmentInputMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseAssignmentInput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseAssignmentInput> courseAssignmentInputList = courseAssignmentInputRepository.findAll();
        assertThat(courseAssignmentInputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
