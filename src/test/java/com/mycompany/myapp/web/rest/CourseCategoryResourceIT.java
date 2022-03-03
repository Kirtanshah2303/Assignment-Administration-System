package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.repository.CourseCategoryRepository;
import com.mycompany.myapp.service.criteria.CourseCategoryCriteria;
import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import com.mycompany.myapp.service.mapper.CourseCategoryMapper;
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
 * Integration tests for the {@link CourseCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourseCategoryResourceIT {

    private static final String DEFAULT_COURSE_CATEGORY_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_CATEGORY_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PARENT = false;
    private static final Boolean UPDATED_IS_PARENT = true;

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;
    private static final Integer SMALLER_PARENT_ID = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseCategoryRepository courseCategoryRepository;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseCategoryMockMvc;

    private CourseCategory courseCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseCategory createEntity(EntityManager em) {
        CourseCategory courseCategory = new CourseCategory()
            .courseCategoryTitle(DEFAULT_COURSE_CATEGORY_TITLE)
            .logo(DEFAULT_LOGO)
            .isParent(DEFAULT_IS_PARENT)
            .parentId(DEFAULT_PARENT_ID)
            .description(DEFAULT_DESCRIPTION);
        return courseCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseCategory createUpdatedEntity(EntityManager em) {
        CourseCategory courseCategory = new CourseCategory()
            .courseCategoryTitle(UPDATED_COURSE_CATEGORY_TITLE)
            .logo(UPDATED_LOGO)
            .isParent(UPDATED_IS_PARENT)
            .parentId(UPDATED_PARENT_ID)
            .description(UPDATED_DESCRIPTION);
        return courseCategory;
    }

    @BeforeEach
    public void initTest() {
        courseCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseCategory() throws Exception {
        int databaseSizeBeforeCreate = courseCategoryRepository.findAll().size();
        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);
        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CourseCategory testCourseCategory = courseCategoryList.get(courseCategoryList.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryTitle()).isEqualTo(DEFAULT_COURSE_CATEGORY_TITLE);
        assertThat(testCourseCategory.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCourseCategory.getIsParent()).isEqualTo(DEFAULT_IS_PARENT);
        assertThat(testCourseCategory.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testCourseCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCourseCategoryWithExistingId() throws Exception {
        // Create the CourseCategory with an existing ID
        courseCategory.setId(1L);
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        int databaseSizeBeforeCreate = courseCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCourseCategoryTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseCategoryRepository.findAll().size();
        // set the field null
        courseCategory.setCourseCategoryTitle(null);

        // Create the CourseCategory, which fails.
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseCategoryRepository.findAll().size();
        // set the field null
        courseCategory.setLogo(null);

        // Create the CourseCategory, which fails.
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsParentIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseCategoryRepository.findAll().size();
        // set the field null
        courseCategory.setIsParent(null);

        // Create the CourseCategory, which fails.
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseCategoryRepository.findAll().size();
        // set the field null
        courseCategory.setParentId(null);

        // Create the CourseCategory, which fails.
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        restCourseCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseCategories() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseCategoryTitle").value(hasItem(DEFAULT_COURSE_CATEGORY_TITLE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].isParent").value(hasItem(DEFAULT_IS_PARENT.booleanValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get the courseCategory
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, courseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseCategory.getId().intValue()))
            .andExpect(jsonPath("$.courseCategoryTitle").value(DEFAULT_COURSE_CATEGORY_TITLE))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.isParent").value(DEFAULT_IS_PARENT.booleanValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getCourseCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        Long id = courseCategory.getId();

        defaultCourseCategoryShouldBeFound("id.equals=" + id);
        defaultCourseCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCourseCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle equals to DEFAULT_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.equals=" + DEFAULT_COURSE_CATEGORY_TITLE);

        // Get all the courseCategoryList where courseCategoryTitle equals to UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.equals=" + UPDATED_COURSE_CATEGORY_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle not equals to DEFAULT_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.notEquals=" + DEFAULT_COURSE_CATEGORY_TITLE);

        // Get all the courseCategoryList where courseCategoryTitle not equals to UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.notEquals=" + UPDATED_COURSE_CATEGORY_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleIsInShouldWork() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle in DEFAULT_COURSE_CATEGORY_TITLE or UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.in=" + DEFAULT_COURSE_CATEGORY_TITLE + "," + UPDATED_COURSE_CATEGORY_TITLE);

        // Get all the courseCategoryList where courseCategoryTitle equals to UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.in=" + UPDATED_COURSE_CATEGORY_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle is not null
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.specified=true");

        // Get all the courseCategoryList where courseCategoryTitle is null
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle contains DEFAULT_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.contains=" + DEFAULT_COURSE_CATEGORY_TITLE);

        // Get all the courseCategoryList where courseCategoryTitle contains UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.contains=" + UPDATED_COURSE_CATEGORY_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByCourseCategoryTitleNotContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where courseCategoryTitle does not contain DEFAULT_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldNotBeFound("courseCategoryTitle.doesNotContain=" + DEFAULT_COURSE_CATEGORY_TITLE);

        // Get all the courseCategoryList where courseCategoryTitle does not contain UPDATED_COURSE_CATEGORY_TITLE
        defaultCourseCategoryShouldBeFound("courseCategoryTitle.doesNotContain=" + UPDATED_COURSE_CATEGORY_TITLE);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoIsEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo equals to DEFAULT_LOGO
        defaultCourseCategoryShouldBeFound("logo.equals=" + DEFAULT_LOGO);

        // Get all the courseCategoryList where logo equals to UPDATED_LOGO
        defaultCourseCategoryShouldNotBeFound("logo.equals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo not equals to DEFAULT_LOGO
        defaultCourseCategoryShouldNotBeFound("logo.notEquals=" + DEFAULT_LOGO);

        // Get all the courseCategoryList where logo not equals to UPDATED_LOGO
        defaultCourseCategoryShouldBeFound("logo.notEquals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoIsInShouldWork() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo in DEFAULT_LOGO or UPDATED_LOGO
        defaultCourseCategoryShouldBeFound("logo.in=" + DEFAULT_LOGO + "," + UPDATED_LOGO);

        // Get all the courseCategoryList where logo equals to UPDATED_LOGO
        defaultCourseCategoryShouldNotBeFound("logo.in=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo is not null
        defaultCourseCategoryShouldBeFound("logo.specified=true");

        // Get all the courseCategoryList where logo is null
        defaultCourseCategoryShouldNotBeFound("logo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo contains DEFAULT_LOGO
        defaultCourseCategoryShouldBeFound("logo.contains=" + DEFAULT_LOGO);

        // Get all the courseCategoryList where logo contains UPDATED_LOGO
        defaultCourseCategoryShouldNotBeFound("logo.contains=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByLogoNotContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where logo does not contain DEFAULT_LOGO
        defaultCourseCategoryShouldNotBeFound("logo.doesNotContain=" + DEFAULT_LOGO);

        // Get all the courseCategoryList where logo does not contain UPDATED_LOGO
        defaultCourseCategoryShouldBeFound("logo.doesNotContain=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByIsParentIsEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where isParent equals to DEFAULT_IS_PARENT
        defaultCourseCategoryShouldBeFound("isParent.equals=" + DEFAULT_IS_PARENT);

        // Get all the courseCategoryList where isParent equals to UPDATED_IS_PARENT
        defaultCourseCategoryShouldNotBeFound("isParent.equals=" + UPDATED_IS_PARENT);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByIsParentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where isParent not equals to DEFAULT_IS_PARENT
        defaultCourseCategoryShouldNotBeFound("isParent.notEquals=" + DEFAULT_IS_PARENT);

        // Get all the courseCategoryList where isParent not equals to UPDATED_IS_PARENT
        defaultCourseCategoryShouldBeFound("isParent.notEquals=" + UPDATED_IS_PARENT);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByIsParentIsInShouldWork() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where isParent in DEFAULT_IS_PARENT or UPDATED_IS_PARENT
        defaultCourseCategoryShouldBeFound("isParent.in=" + DEFAULT_IS_PARENT + "," + UPDATED_IS_PARENT);

        // Get all the courseCategoryList where isParent equals to UPDATED_IS_PARENT
        defaultCourseCategoryShouldNotBeFound("isParent.in=" + UPDATED_IS_PARENT);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByIsParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where isParent is not null
        defaultCourseCategoryShouldBeFound("isParent.specified=true");

        // Get all the courseCategoryList where isParent is null
        defaultCourseCategoryShouldNotBeFound("isParent.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId equals to DEFAULT_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.equals=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId equals to UPDATED_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.equals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId not equals to DEFAULT_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.notEquals=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId not equals to UPDATED_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.notEquals=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsInShouldWork() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId in DEFAULT_PARENT_ID or UPDATED_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.in=" + DEFAULT_PARENT_ID + "," + UPDATED_PARENT_ID);

        // Get all the courseCategoryList where parentId equals to UPDATED_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.in=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId is not null
        defaultCourseCategoryShouldBeFound("parentId.specified=true");

        // Get all the courseCategoryList where parentId is null
        defaultCourseCategoryShouldNotBeFound("parentId.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId is greater than or equal to DEFAULT_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.greaterThanOrEqual=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId is greater than or equal to UPDATED_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.greaterThanOrEqual=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId is less than or equal to DEFAULT_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.lessThanOrEqual=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId is less than or equal to SMALLER_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.lessThanOrEqual=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId is less than DEFAULT_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.lessThan=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId is less than UPDATED_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.lessThan=" + UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByParentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where parentId is greater than DEFAULT_PARENT_ID
        defaultCourseCategoryShouldNotBeFound("parentId.greaterThan=" + DEFAULT_PARENT_ID);

        // Get all the courseCategoryList where parentId is greater than SMALLER_PARENT_ID
        defaultCourseCategoryShouldBeFound("parentId.greaterThan=" + SMALLER_PARENT_ID);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultCourseCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the courseCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCourseCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultCourseCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the courseCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultCourseCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCourseCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the courseCategoryList where description equals to UPDATED_DESCRIPTION
        defaultCourseCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description is not null
        defaultCourseCategoryShouldBeFound("description.specified=true");

        // Get all the courseCategoryList where description is null
        defaultCourseCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description contains DEFAULT_DESCRIPTION
        defaultCourseCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the courseCategoryList where description contains UPDATED_DESCRIPTION
        defaultCourseCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCourseCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultCourseCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the courseCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultCourseCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseCategoryShouldBeFound(String filter) throws Exception {
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseCategoryTitle").value(hasItem(DEFAULT_COURSE_CATEGORY_TITLE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].isParent").value(hasItem(DEFAULT_IS_PARENT.booleanValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseCategoryShouldNotBeFound(String filter) throws Exception {
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourseCategory() throws Exception {
        // Get the courseCategory
        restCourseCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();

        // Update the courseCategory
        CourseCategory updatedCourseCategory = courseCategoryRepository.findById(courseCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCourseCategory are not directly saved in db
        em.detach(updatedCourseCategory);
        updatedCourseCategory
            .courseCategoryTitle(UPDATED_COURSE_CATEGORY_TITLE)
            .logo(UPDATED_LOGO)
            .isParent(UPDATED_IS_PARENT)
            .parentId(UPDATED_PARENT_ID)
            .description(UPDATED_DESCRIPTION);
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(updatedCourseCategory);

        restCourseCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
        CourseCategory testCourseCategory = courseCategoryList.get(courseCategoryList.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryTitle()).isEqualTo(UPDATED_COURSE_CATEGORY_TITLE);
        assertThat(testCourseCategory.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCourseCategory.getIsParent()).isEqualTo(UPDATED_IS_PARENT);
        assertThat(testCourseCategory.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCourseCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courseCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourseCategoryWithPatch() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();

        // Update the courseCategory using partial update
        CourseCategory partialUpdatedCourseCategory = new CourseCategory();
        partialUpdatedCourseCategory.setId(courseCategory.getId());

        partialUpdatedCourseCategory
            .courseCategoryTitle(UPDATED_COURSE_CATEGORY_TITLE)
            .isParent(UPDATED_IS_PARENT)
            .parentId(UPDATED_PARENT_ID);

        restCourseCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseCategory))
            )
            .andExpect(status().isOk());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
        CourseCategory testCourseCategory = courseCategoryList.get(courseCategoryList.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryTitle()).isEqualTo(UPDATED_COURSE_CATEGORY_TITLE);
        assertThat(testCourseCategory.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCourseCategory.getIsParent()).isEqualTo(UPDATED_IS_PARENT);
        assertThat(testCourseCategory.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCourseCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCourseCategoryWithPatch() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();

        // Update the courseCategory using partial update
        CourseCategory partialUpdatedCourseCategory = new CourseCategory();
        partialUpdatedCourseCategory.setId(courseCategory.getId());

        partialUpdatedCourseCategory
            .courseCategoryTitle(UPDATED_COURSE_CATEGORY_TITLE)
            .logo(UPDATED_LOGO)
            .isParent(UPDATED_IS_PARENT)
            .parentId(UPDATED_PARENT_ID)
            .description(UPDATED_DESCRIPTION);

        restCourseCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourseCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourseCategory))
            )
            .andExpect(status().isOk());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
        CourseCategory testCourseCategory = courseCategoryList.get(courseCategoryList.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryTitle()).isEqualTo(UPDATED_COURSE_CATEGORY_TITLE);
        assertThat(testCourseCategory.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCourseCategory.getIsParent()).isEqualTo(UPDATED_IS_PARENT);
        assertThat(testCourseCategory.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCourseCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courseCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourseCategory() throws Exception {
        int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();
        courseCategory.setId(count.incrementAndGet());

        // Create the CourseCategory
        CourseCategoryDTO courseCategoryDTO = courseCategoryMapper.toDto(courseCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourseCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courseCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        int databaseSizeBeforeDelete = courseCategoryRepository.findAll().size();

        // Delete the courseCategory
        restCourseCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, courseCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseCategory> courseCategoryList = courseCategoryRepository.findAll();
        assertThat(courseCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
