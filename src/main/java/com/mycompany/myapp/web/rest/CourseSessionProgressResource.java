package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseSessionProgressRepository;
import com.mycompany.myapp.service.CourseSessionProgressQueryService;
import com.mycompany.myapp.service.CourseSessionProgressService;
import com.mycompany.myapp.service.criteria.CourseSessionProgressCriteria;
import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseSessionProgress}.
 */
@RestController
@RequestMapping("/api")
public class CourseSessionProgressResource {

    private final Logger log = LoggerFactory.getLogger(CourseSessionProgressResource.class);

    private static final String ENTITY_NAME = "courseSessionProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseSessionProgressService courseSessionProgressService;

    private final CourseSessionProgressRepository courseSessionProgressRepository;

    private final CourseSessionProgressQueryService courseSessionProgressQueryService;

    public CourseSessionProgressResource(
        CourseSessionProgressService courseSessionProgressService,
        CourseSessionProgressRepository courseSessionProgressRepository,
        CourseSessionProgressQueryService courseSessionProgressQueryService
    ) {
        this.courseSessionProgressService = courseSessionProgressService;
        this.courseSessionProgressRepository = courseSessionProgressRepository;
        this.courseSessionProgressQueryService = courseSessionProgressQueryService;
    }

    /**
     * {@code POST  /course-session-progresses} : Create a new courseSessionProgress.
     *
     * @param courseSessionProgressDTO the courseSessionProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseSessionProgressDTO, or with status {@code 400 (Bad Request)} if the courseSessionProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-session-progresses")
    public ResponseEntity<CourseSessionProgressDTO> createCourseSessionProgress(
        @Valid @RequestBody CourseSessionProgressDTO courseSessionProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CourseSessionProgress : {}", courseSessionProgressDTO);
        if (courseSessionProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseSessionProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseSessionProgressDTO result = courseSessionProgressService.save(courseSessionProgressDTO);
        return ResponseEntity
            .created(new URI("/api/course-session-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-session-progresses/:id} : Updates an existing courseSessionProgress.
     *
     * @param id the id of the courseSessionProgressDTO to save.
     * @param courseSessionProgressDTO the courseSessionProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSessionProgressDTO,
     * or with status {@code 400 (Bad Request)} if the courseSessionProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseSessionProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-session-progresses/{id}")
    public ResponseEntity<CourseSessionProgressDTO> updateCourseSessionProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseSessionProgressDTO courseSessionProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseSessionProgress : {}, {}", id, courseSessionProgressDTO);
        if (courseSessionProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSessionProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSessionProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseSessionProgressDTO result = courseSessionProgressService.save(courseSessionProgressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSessionProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-session-progresses/:id} : Partial updates given fields of an existing courseSessionProgress, field will ignore if it is null
     *
     * @param id the id of the courseSessionProgressDTO to save.
     * @param courseSessionProgressDTO the courseSessionProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSessionProgressDTO,
     * or with status {@code 400 (Bad Request)} if the courseSessionProgressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseSessionProgressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseSessionProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-session-progresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseSessionProgressDTO> partialUpdateCourseSessionProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseSessionProgressDTO courseSessionProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseSessionProgress partially : {}, {}", id, courseSessionProgressDTO);
        if (courseSessionProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSessionProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSessionProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseSessionProgressDTO> result = courseSessionProgressService.partialUpdate(courseSessionProgressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSessionProgressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-session-progresses} : get all the courseSessionProgresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseSessionProgresses in body.
     */
    @GetMapping("/course-session-progresses")
    public ResponseEntity<List<CourseSessionProgressDTO>> getAllCourseSessionProgresses(
        CourseSessionProgressCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseSessionProgresses by criteria: {}", criteria);
        Page<CourseSessionProgressDTO> page = courseSessionProgressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-session-progresses/count} : count all the courseSessionProgresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-session-progresses/count")
    public ResponseEntity<Long> countCourseSessionProgresses(CourseSessionProgressCriteria criteria) {
        log.debug("REST request to count CourseSessionProgresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseSessionProgressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-session-progresses/:id} : get the "id" courseSessionProgress.
     *
     * @param id the id of the courseSessionProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseSessionProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-session-progresses/{id}")
    public ResponseEntity<CourseSessionProgressDTO> getCourseSessionProgress(@PathVariable Long id) {
        log.debug("REST request to get CourseSessionProgress : {}", id);
        Optional<CourseSessionProgressDTO> courseSessionProgressDTO = courseSessionProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseSessionProgressDTO);
    }

    /**
     * {@code DELETE  /course-session-progresses/:id} : delete the "id" courseSessionProgress.
     *
     * @param id the id of the courseSessionProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-session-progresses/{id}")
    public ResponseEntity<Void> deleteCourseSessionProgress(@PathVariable Long id) {
        log.debug("REST request to delete CourseSessionProgress : {}", id);
        courseSessionProgressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
