package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.repository.CourseSectionRepository;
import com.mycompany.myapp.service.criteria.CourseSectionCriteria;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
import com.mycompany.myapp.service.mapper.CourseSectionMapper;
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
 * Integration tests for the {@link CourseSectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseSectionResourceIT {

    private static final String DEFAULT_SECTION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SECTION_ID = 1L;

    private static final String DEFAULT_SECTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SECTION_ORDER = 1;
    private static final Integer UPDATED_SECTION_ORDER = 2;
    private static final Integer SMALLER_SECTION_ORDER = 1 - 1;

    private static final Boolean DEFAULT_IS_DRAFT = false;
    private static final Boolean UPDATED_IS_DRAFT = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final String ENTITY_API_URL = "/api/course-sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    @Autowired
    private CourseSectionMapper courseSectionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseSectionMockMvc;

    private CourseSection courseSection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSection createEntity(EntityManager em) {
        CourseSection courseSection = new CourseSection()
            .sectionTitle(DEFAULT_SECTION_TITLE)
            .sectionDescription(DEFAULT_SECTION_DESCRIPTION)
            .sectionOrder(DEFAULT_SECTION_ORDER)
            .isDraft(DEFAULT_IS_DRAFT)
            .isApproved(DEFAULT_IS_APPROVED);
        courseSection.setId(DEFAULT_SECTION_ID);
        return courseSection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseSection createUpdatedEntity(EntityManager em) {
        CourseSection courseSection = new CourseSection()
            .sectionTitle(UPDATED_SECTION_TITLE)
            .sectionDescription(UPDATED_SECTION_DESCRIPTION)
            .sectionOrder(UPDATED_SECTION_ORDER)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED);
        return courseSection;
    }

    @BeforeEach
    public void initTest() {
        courseSection = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseSection() throws Exception {
        int databaseSizeBeforeCreate = courseSectionRepository.findAll().size();
        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);
        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeCreate + 1);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getSectionTitle()).isEqualTo(DEFAULT_SECTION_TITLE);
        assertThat(testCourseSection.getSectionDescription()).isEqualTo(DEFAULT_SECTION_DESCRIPTION);
        assertThat(testCourseSection.getSectionOrder()).isEqualTo(DEFAULT_SECTION_ORDER);
        assertThat(testCourseSection.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseSection.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
    }

    @Test
    @Transactional
    void createCourseSectionWithExistingId() throws Exception {
        // Create the CourseSection with an existing ID
        courseSection.setId(1L);
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        int databaseSizeBeforeCreate = courseSectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSectionTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSectionRepository.findAll().size();
        // set the field null
        courseSection.setSectionTitle(null);

        // Create the CourseSection, which fails.
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSectionOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSectionRepository.findAll().size();
        // set the field null
        courseSection.setSectionOrder(null);

        // Create the CourseSection, which fails.
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDraftIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSectionRepository.findAll().size();
        // set the field null
        courseSection.setIsDraft(null);

        // Create the CourseSection, which fails.
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseSectionRepository.findAll().size();
        // set the field null
        courseSection.setIsApproved(null);

        // Create the CourseSection, which fails.
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        restCourseSectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseSections() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionTitle").value(hasItem(DEFAULT_SECTION_TITLE)))
            .andExpect(jsonPath("$.[*].sectionDescription").value(hasItem(DEFAULT_SECTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sectionOrder").value(hasItem(DEFAULT_SECTION_ORDER)))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())));
    }

    @Test
    @Transactional
    void getCourseSection() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get the courseSection
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL_ID, courseSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseSection.getId().intValue()))
            .andExpect(jsonPath("$.sectionTitle").value(DEFAULT_SECTION_TITLE))
            .andExpect(jsonPath("$.sectionDescription").value(DEFAULT_SECTION_DESCRIPTION))
            .andExpect(jsonPath("$.sectionOrder").value(DEFAULT_SECTION_ORDER))
            .andExpect(jsonPath("$.isDraft").value(DEFAULT_IS_DRAFT.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()));
    }

    @Test
    @Transactional
    void getCourseSectionsByIdFiltering() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        Long id = courseSection.getId();

        defaultCourseSectionShouldBeFound("id.equals=" + id);
        defaultCourseSectionShouldNotBeFound("id.notEquals=" + id);

        defaultCourseSectionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseSectionShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseSectionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseSectionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle equals to DEFAULT_SECTION_TITLE
        defaultCourseSectionShouldBeFound("sectionTitle.equals=" + DEFAULT_SECTION_TITLE);

        // Get all the courseSectionList where sectionTitle equals to UPDATED_SECTION_TITLE
        defaultCourseSectionShouldNotBeFound("sectionTitle.equals=" + UPDATED_SECTION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle not equals to DEFAULT_SECTION_TITLE
        defaultCourseSectionShouldNotBeFound("sectionTitle.notEquals=" + DEFAULT_SECTION_TITLE);

        // Get all the courseSectionList where sectionTitle not equals to UPDATED_SECTION_TITLE
        defaultCourseSectionShouldBeFound("sectionTitle.notEquals=" + UPDATED_SECTION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle in DEFAULT_SECTION_TITLE or UPDATED_SECTION_TITLE
        defaultCourseSectionShouldBeFound("sectionTitle.in=" + DEFAULT_SECTION_TITLE + "," + UPDATED_SECTION_TITLE);

        // Get all the courseSectionList where sectionTitle equals to UPDATED_SECTION_TITLE
        defaultCourseSectionShouldNotBeFound("sectionTitle.in=" + UPDATED_SECTION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle is not null
        defaultCourseSectionShouldBeFound("sectionTitle.specified=true");

        // Get all the courseSectionList where sectionTitle is null
        defaultCourseSectionShouldNotBeFound("sectionTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleContainsSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle contains DEFAULT_SECTION_TITLE
        defaultCourseSectionShouldBeFound("sectionTitle.contains=" + DEFAULT_SECTION_TITLE);

        // Get all the courseSectionList where sectionTitle contains UPDATED_SECTION_TITLE
        defaultCourseSectionShouldNotBeFound("sectionTitle.contains=" + UPDATED_SECTION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionTitle does not contain DEFAULT_SECTION_TITLE
        defaultCourseSectionShouldNotBeFound("sectionTitle.doesNotContain=" + DEFAULT_SECTION_TITLE);

        // Get all the courseSectionList where sectionTitle does not contain UPDATED_SECTION_TITLE
        defaultCourseSectionShouldBeFound("sectionTitle.doesNotContain=" + UPDATED_SECTION_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription equals to DEFAULT_SECTION_DESCRIPTION
        defaultCourseSectionShouldBeFound("sectionDescription.equals=" + DEFAULT_SECTION_DESCRIPTION);

        // Get all the courseSectionList where sectionDescription equals to UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldNotBeFound("sectionDescription.equals=" + UPDATED_SECTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription not equals to DEFAULT_SECTION_DESCRIPTION
        defaultCourseSectionShouldNotBeFound("sectionDescription.notEquals=" + DEFAULT_SECTION_DESCRIPTION);

        // Get all the courseSectionList where sectionDescription not equals to UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldBeFound("sectionDescription.notEquals=" + UPDATED_SECTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription in DEFAULT_SECTION_DESCRIPTION or UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldBeFound("sectionDescription.in=" + DEFAULT_SECTION_DESCRIPTION + "," + UPDATED_SECTION_DESCRIPTION);

        // Get all the courseSectionList where sectionDescription equals to UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldNotBeFound("sectionDescription.in=" + UPDATED_SECTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription is not null
        defaultCourseSectionShouldBeFound("sectionDescription.specified=true");

        // Get all the courseSectionList where sectionDescription is null
        defaultCourseSectionShouldNotBeFound("sectionDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription contains DEFAULT_SECTION_DESCRIPTION
        defaultCourseSectionShouldBeFound("sectionDescription.contains=" + DEFAULT_SECTION_DESCRIPTION);

        // Get all the courseSectionList where sectionDescription contains UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldNotBeFound("sectionDescription.contains=" + UPDATED_SECTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionDescription does not contain DEFAULT_SECTION_DESCRIPTION
        defaultCourseSectionShouldNotBeFound("sectionDescription.doesNotContain=" + DEFAULT_SECTION_DESCRIPTION);

        // Get all the courseSectionList where sectionDescription does not contain UPDATED_SECTION_DESCRIPTION
        defaultCourseSectionShouldBeFound("sectionDescription.doesNotContain=" + UPDATED_SECTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder equals to DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.equals=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder equals to UPDATED_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.equals=" + UPDATED_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder not equals to DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.notEquals=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder not equals to UPDATED_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.notEquals=" + UPDATED_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsInShouldWork() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder in DEFAULT_SECTION_ORDER or UPDATED_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.in=" + DEFAULT_SECTION_ORDER + "," + UPDATED_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder equals to UPDATED_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.in=" + UPDATED_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder is not null
        defaultCourseSectionShouldBeFound("sectionOrder.specified=true");

        // Get all the courseSectionList where sectionOrder is null
        defaultCourseSectionShouldNotBeFound("sectionOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder is greater than or equal to DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.greaterThanOrEqual=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder is greater than or equal to UPDATED_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.greaterThanOrEqual=" + UPDATED_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder is less than or equal to DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.lessThanOrEqual=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder is less than or equal to SMALLER_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.lessThanOrEqual=" + SMALLER_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder is less than DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.lessThan=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder is less than UPDATED_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.lessThan=" + UPDATED_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsBySectionOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where sectionOrder is greater than DEFAULT_SECTION_ORDER
        defaultCourseSectionShouldNotBeFound("sectionOrder.greaterThan=" + DEFAULT_SECTION_ORDER);

        // Get all the courseSectionList where sectionOrder is greater than SMALLER_SECTION_ORDER
        defaultCourseSectionShouldBeFound("sectionOrder.greaterThan=" + SMALLER_SECTION_ORDER);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsDraftIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isDraft equals to DEFAULT_IS_DRAFT
        defaultCourseSectionShouldBeFound("isDraft.equals=" + DEFAULT_IS_DRAFT);

        // Get all the courseSectionList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseSectionShouldNotBeFound("isDraft.equals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsDraftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isDraft not equals to DEFAULT_IS_DRAFT
        defaultCourseSectionShouldNotBeFound("isDraft.notEquals=" + DEFAULT_IS_DRAFT);

        // Get all the courseSectionList where isDraft not equals to UPDATED_IS_DRAFT
        defaultCourseSectionShouldBeFound("isDraft.notEquals=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsDraftIsInShouldWork() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isDraft in DEFAULT_IS_DRAFT or UPDATED_IS_DRAFT
        defaultCourseSectionShouldBeFound("isDraft.in=" + DEFAULT_IS_DRAFT + "," + UPDATED_IS_DRAFT);

        // Get all the courseSectionList where isDraft equals to UPDATED_IS_DRAFT
        defaultCourseSectionShouldNotBeFound("isDraft.in=" + UPDATED_IS_DRAFT);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsDraftIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isDraft is not null
        defaultCourseSectionShouldBeFound("isDraft.specified=true");

        // Get all the courseSectionList where isDraft is null
        defaultCourseSectionShouldNotBeFound("isDraft.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isApproved equals to DEFAULT_IS_APPROVED
        defaultCourseSectionShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the courseSectionList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseSectionShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultCourseSectionShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the courseSectionList where isApproved not equals to UPDATED_IS_APPROVED
        defaultCourseSectionShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultCourseSectionShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the courseSectionList where isApproved equals to UPDATED_IS_APPROVED
        defaultCourseSectionShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllCourseSectionsByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        // Get all the courseSectionList where isApproved is not null
        defaultCourseSectionShouldBeFound("isApproved.specified=true");

        // Get all the courseSectionList where isApproved is null
        defaultCourseSectionShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseSectionsByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);
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
        courseSection.setCourse(course);
        courseSectionRepository.saveAndFlush(courseSection);
        Long courseId = course.getId();

        // Get all the courseSectionList where course equals to courseId
        defaultCourseSectionShouldBeFound("courseId.equals=" + courseId);

        // Get all the courseSectionList where course equals to (courseId + 1)
        defaultCourseSectionShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseSectionShouldBeFound(String filter) throws Exception {
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionTitle").value(hasItem(DEFAULT_SECTION_TITLE)))
            .andExpect(jsonPath("$.[*].sectionDescription").value(hasItem(DEFAULT_SECTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sectionOrder").value(hasItem(DEFAULT_SECTION_ORDER)))
            .andExpect(jsonPath("$.[*].isDraft").value(hasItem(DEFAULT_IS_DRAFT.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())));

        // Check, that the count call also returns 1
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseSectionShouldNotBeFound(String filter) throws Exception {
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseSectionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseSection() throws Exception {
        // Get the courseSection
        restCourseSectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseSection() throws Exception {
        // Initialize the database
        // courseSectionRepository.saveAndFlush(courseSection);

        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();

        // Update the courseSection
        CourseSection updatedCourseSection = courseSectionRepository.findById(courseSection.getId()).get();
        // Disconnect from session so that the updates on updatedCourseSection are not directly saved in db
        em.detach(updatedCourseSection);
        updatedCourseSection
            .sectionTitle(UPDATED_SECTION_TITLE)
            .sectionDescription(UPDATED_SECTION_DESCRIPTION)
            .sectionOrder(UPDATED_SECTION_ORDER)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED);
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(updatedCourseSection);

        restCourseSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSectionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getSectionTitle()).isEqualTo(UPDATED_SECTION_TITLE);
        assertThat(testCourseSection.getSectionDescription()).isEqualTo(UPDATED_SECTION_DESCRIPTION);
        assertThat(testCourseSection.getSectionOrder()).isEqualTo(UPDATED_SECTION_ORDER);
        assertThat(testCourseSection.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseSection.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void putNonExistingCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseSectionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseSectionWithPatch() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();

        // Update the courseSection using partial update
        CourseSection partialUpdatedCourseSection = new CourseSection();
        partialUpdatedCourseSection.setId(courseSection.getId());

        partialUpdatedCourseSection.isApproved(UPDATED_IS_APPROVED);

        restCourseSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSection))
            )
            .andExpect(status().isOk());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getSectionTitle()).isEqualTo(DEFAULT_SECTION_TITLE);
        assertThat(testCourseSection.getSectionDescription()).isEqualTo(DEFAULT_SECTION_DESCRIPTION);
        assertThat(testCourseSection.getSectionOrder()).isEqualTo(DEFAULT_SECTION_ORDER);
        assertThat(testCourseSection.getIsDraft()).isEqualTo(DEFAULT_IS_DRAFT);
        assertThat(testCourseSection.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void fullUpdateCourseSectionWithPatch() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();

        // Update the courseSection using partial update
        CourseSection partialUpdatedCourseSection = new CourseSection();
        partialUpdatedCourseSection.setId(courseSection.getId());

        partialUpdatedCourseSection
            .sectionTitle(UPDATED_SECTION_TITLE)
            .sectionDescription(UPDATED_SECTION_DESCRIPTION)
            .sectionOrder(UPDATED_SECTION_ORDER)
            .isDraft(UPDATED_IS_DRAFT)
            .isApproved(UPDATED_IS_APPROVED);

        restCourseSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseSection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseSection))
            )
            .andExpect(status().isOk());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
        CourseSection testCourseSection = courseSectionList.get(courseSectionList.size() - 1);
        assertThat(testCourseSection.getSectionTitle()).isEqualTo(UPDATED_SECTION_TITLE);
        assertThat(testCourseSection.getSectionDescription()).isEqualTo(UPDATED_SECTION_DESCRIPTION);
        assertThat(testCourseSection.getSectionOrder()).isEqualTo(UPDATED_SECTION_ORDER);
        assertThat(testCourseSection.getIsDraft()).isEqualTo(UPDATED_IS_DRAFT);
        assertThat(testCourseSection.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void patchNonExistingCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseSectionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseSection() throws Exception {
        int databaseSizeBeforeUpdate = courseSectionRepository.findAll().size();
        courseSection.setId(count.incrementAndGet());

        // Create the CourseSection
        CourseSectionDTO courseSectionDTO = courseSectionMapper.toDto(courseSection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseSectionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseSectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseSection in the database
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseSection() throws Exception {
        // Initialize the database
        courseSectionRepository.saveAndFlush(courseSection);

        int databaseSizeBeforeDelete = courseSectionRepository.findAll().size();

        // Delete the courseSection
        restCourseSectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseSection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseSection> courseSectionList = courseSectionRepository.findAll();
        assertThat(courseSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
