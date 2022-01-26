package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseLevelRepository;
import com.mycompany.myapp.service.CourseLevelQueryService;
import com.mycompany.myapp.service.CourseLevelService;
import com.mycompany.myapp.service.criteria.CourseLevelCriteria;
import com.mycompany.myapp.service.dto.CourseLevelDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseLevel}.
 */
@RestController
@RequestMapping("/api")
public class CourseLevelResource {

    private final Logger log = LoggerFactory.getLogger(CourseLevelResource.class);

    private static final String ENTITY_NAME = "courseLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseLevelService courseLevelService;

    private final CourseLevelRepository courseLevelRepository;

    private final CourseLevelQueryService courseLevelQueryService;

    public CourseLevelResource(
        CourseLevelService courseLevelService,
        CourseLevelRepository courseLevelRepository,
        CourseLevelQueryService courseLevelQueryService
    ) {
        this.courseLevelService = courseLevelService;
        this.courseLevelRepository = courseLevelRepository;
        this.courseLevelQueryService = courseLevelQueryService;
    }

    /**
     * {@code POST  /course-levels} : Create a new courseLevel.
     *
     * @param courseLevelDTO the courseLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseLevelDTO, or with status {@code 400 (Bad Request)} if the courseLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-levels")
    public ResponseEntity<CourseLevelDTO> createCourseLevel(@Valid @RequestBody CourseLevelDTO courseLevelDTO) throws URISyntaxException {
        log.debug("REST request to save CourseLevel : {}", courseLevelDTO);
        if (courseLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseLevelDTO result = courseLevelService.save(courseLevelDTO);
        return ResponseEntity
            .created(new URI("/api/course-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-levels/:id} : Updates an existing courseLevel.
     *
     * @param id the id of the courseLevelDTO to save.
     * @param courseLevelDTO the courseLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseLevelDTO,
     * or with status {@code 400 (Bad Request)} if the courseLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-levels/{id}")
    public ResponseEntity<CourseLevelDTO> updateCourseLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseLevelDTO courseLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseLevel : {}, {}", id, courseLevelDTO);
        if (courseLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseLevelDTO result = courseLevelService.save(courseLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-levels/:id} : Partial updates given fields of an existing courseLevel, field will ignore if it is null
     *
     * @param id the id of the courseLevelDTO to save.
     * @param courseLevelDTO the courseLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseLevelDTO,
     * or with status {@code 400 (Bad Request)} if the courseLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseLevelDTO> partialUpdateCourseLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseLevelDTO courseLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseLevel partially : {}, {}", id, courseLevelDTO);
        if (courseLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseLevelDTO> result = courseLevelService.partialUpdate(courseLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-levels} : get all the courseLevels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseLevels in body.
     */
    @GetMapping("/course-levels")
    public ResponseEntity<List<CourseLevelDTO>> getAllCourseLevels(
        CourseLevelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseLevels by criteria: {}", criteria);
        Page<CourseLevelDTO> page = courseLevelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-levels/count} : count all the courseLevels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-levels/count")
    public ResponseEntity<Long> countCourseLevels(CourseLevelCriteria criteria) {
        log.debug("REST request to count CourseLevels by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseLevelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-levels/:id} : get the "id" courseLevel.
     *
     * @param id the id of the courseLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-levels/{id}")
    public ResponseEntity<CourseLevelDTO> getCourseLevel(@PathVariable Long id) {
        log.debug("REST request to get CourseLevel : {}", id);
        Optional<CourseLevelDTO> courseLevelDTO = courseLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseLevelDTO);
    }

    /**
     * {@code DELETE  /course-levels/:id} : delete the "id" courseLevel.
     *
     * @param id the id of the courseLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-levels/{id}")
    public ResponseEntity<Void> deleteCourseLevel(@PathVariable Long id) {
        log.debug("REST request to delete CourseLevel : {}", id);
        courseLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
