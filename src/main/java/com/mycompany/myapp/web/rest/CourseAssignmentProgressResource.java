package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseAssignmentProgressRepository;
import com.mycompany.myapp.service.CourseAssignmentProgressQueryService;
import com.mycompany.myapp.service.CourseAssignmentProgressService;
import com.mycompany.myapp.service.criteria.CourseAssignmentProgressCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentProgressDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseAssignmentProgress}.
 */
@RestController
@RequestMapping("/api")
public class CourseAssignmentProgressResource {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentProgressResource.class);

    private static final String ENTITY_NAME = "courseAssignmentProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseAssignmentProgressService courseAssignmentProgressService;

    private final CourseAssignmentProgressRepository courseAssignmentProgressRepository;

    private final CourseAssignmentProgressQueryService courseAssignmentProgressQueryService;

    public CourseAssignmentProgressResource(
        CourseAssignmentProgressService courseAssignmentProgressService,
        CourseAssignmentProgressRepository courseAssignmentProgressRepository,
        CourseAssignmentProgressQueryService courseAssignmentProgressQueryService
    ) {
        this.courseAssignmentProgressService = courseAssignmentProgressService;
        this.courseAssignmentProgressRepository = courseAssignmentProgressRepository;
        this.courseAssignmentProgressQueryService = courseAssignmentProgressQueryService;
    }

    /**
     * {@code POST  /course-assignment-progresses} : Create a new courseAssignmentProgress.
     *
     * @param courseAssignmentProgressDTO the courseAssignmentProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseAssignmentProgressDTO, or with status {@code 400 (Bad Request)} if the courseAssignmentProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-assignment-progresses")
    public ResponseEntity<CourseAssignmentProgressDTO> createCourseAssignmentProgress(
        @Valid @RequestBody CourseAssignmentProgressDTO courseAssignmentProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CourseAssignmentProgress : {}", courseAssignmentProgressDTO);
        if (courseAssignmentProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseAssignmentProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseAssignmentProgressDTO result = courseAssignmentProgressService.save(courseAssignmentProgressDTO);
        return ResponseEntity
            .created(new URI("/api/course-assignment-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-assignment-progresses/:id} : Updates an existing courseAssignmentProgress.
     *
     * @param id the id of the courseAssignmentProgressDTO to save.
     * @param courseAssignmentProgressDTO the courseAssignmentProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentProgressDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-assignment-progresses/{id}")
    public ResponseEntity<CourseAssignmentProgressDTO> updateCourseAssignmentProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseAssignmentProgressDTO courseAssignmentProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseAssignmentProgress : {}, {}", id, courseAssignmentProgressDTO);
        if (courseAssignmentProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseAssignmentProgressDTO result = courseAssignmentProgressService.save(courseAssignmentProgressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-assignment-progresses/:id} : Partial updates given fields of an existing courseAssignmentProgress, field will ignore if it is null
     *
     * @param id the id of the courseAssignmentProgressDTO to save.
     * @param courseAssignmentProgressDTO the courseAssignmentProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentProgressDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentProgressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseAssignmentProgressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-assignment-progresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseAssignmentProgressDTO> partialUpdateCourseAssignmentProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseAssignmentProgressDTO courseAssignmentProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseAssignmentProgress partially : {}, {}", id, courseAssignmentProgressDTO);
        if (courseAssignmentProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseAssignmentProgressDTO> result = courseAssignmentProgressService.partialUpdate(courseAssignmentProgressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentProgressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-assignment-progresses} : get all the courseAssignmentProgresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseAssignmentProgresses in body.
     */
    @GetMapping("/course-assignment-progresses")
    public ResponseEntity<List<CourseAssignmentProgressDTO>> getAllCourseAssignmentProgresses(
        CourseAssignmentProgressCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseAssignmentProgresses by criteria: {}", criteria);
        Page<CourseAssignmentProgressDTO> page = courseAssignmentProgressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-assignment-progresses/count} : count all the courseAssignmentProgresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-assignment-progresses/count")
    public ResponseEntity<Long> countCourseAssignmentProgresses(CourseAssignmentProgressCriteria criteria) {
        log.debug("REST request to count CourseAssignmentProgresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseAssignmentProgressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-assignment-progresses/:id} : get the "id" courseAssignmentProgress.
     *
     * @param id the id of the courseAssignmentProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseAssignmentProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-assignment-progresses/{id}")
    public ResponseEntity<CourseAssignmentProgressDTO> getCourseAssignmentProgress(@PathVariable Long id) {
        log.debug("REST request to get CourseAssignmentProgress : {}", id);
        Optional<CourseAssignmentProgressDTO> courseAssignmentProgressDTO = courseAssignmentProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseAssignmentProgressDTO);
    }

    /**
     * {@code DELETE  /course-assignment-progresses/:id} : delete the "id" courseAssignmentProgress.
     *
     * @param id the id of the courseAssignmentProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-assignment-progresses/{id}")
    public ResponseEntity<Void> deleteCourseAssignmentProgress(@PathVariable Long id) {
        log.debug("REST request to delete CourseAssignmentProgress : {}", id);
        courseAssignmentProgressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
