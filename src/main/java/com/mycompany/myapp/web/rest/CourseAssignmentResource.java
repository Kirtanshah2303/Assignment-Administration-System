package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseAssignmentRepository;
import com.mycompany.myapp.service.CourseAssignmentQueryService;
import com.mycompany.myapp.service.CourseAssignmentService;
import com.mycompany.myapp.service.criteria.CourseAssignmentCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseAssignment}.
 */
@RestController
@RequestMapping("/api")
public class CourseAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentResource.class);

    private static final String ENTITY_NAME = "courseAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseAssignmentService courseAssignmentService;

    private final CourseAssignmentRepository courseAssignmentRepository;

    private final CourseAssignmentQueryService courseAssignmentQueryService;

    public CourseAssignmentResource(
        CourseAssignmentService courseAssignmentService,
        CourseAssignmentRepository courseAssignmentRepository,
        CourseAssignmentQueryService courseAssignmentQueryService
    ) {
        this.courseAssignmentService = courseAssignmentService;
        this.courseAssignmentRepository = courseAssignmentRepository;
        this.courseAssignmentQueryService = courseAssignmentQueryService;
    }

    /**
     * {@code POST  /course-assignments} : Create a new courseAssignment.
     *
     * @param courseAssignmentDTO the courseAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseAssignmentDTO, or with status {@code 400 (Bad Request)} if the courseAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-assignments")
    public ResponseEntity<CourseAssignmentDTO> createCourseAssignment(@Valid @RequestBody CourseAssignmentDTO courseAssignmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseAssignment : {}", courseAssignmentDTO);
        if (courseAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseAssignmentDTO result = courseAssignmentService.save(courseAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/course-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-assignments/:id} : Updates an existing courseAssignment.
     *
     * @param id the id of the courseAssignmentDTO to save.
     * @param courseAssignmentDTO the courseAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-assignments/{id}")
    public ResponseEntity<CourseAssignmentDTO> updateCourseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseAssignmentDTO courseAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseAssignment : {}, {}", id, courseAssignmentDTO);
        if (courseAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseAssignmentDTO result = courseAssignmentService.save(courseAssignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-assignments/:id} : Partial updates given fields of an existing courseAssignment, field will ignore if it is null
     *
     * @param id the id of the courseAssignmentDTO to save.
     * @param courseAssignmentDTO the courseAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-assignments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseAssignmentDTO> partialUpdateCourseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseAssignmentDTO courseAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseAssignment partially : {}, {}", id, courseAssignmentDTO);
        if (courseAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseAssignmentDTO> result = courseAssignmentService.partialUpdate(courseAssignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-assignments} : get all the courseAssignments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseAssignments in body.
     */
    @GetMapping("/course-assignments")
    public ResponseEntity<List<CourseAssignmentDTO>> getAllCourseAssignments(
        CourseAssignmentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseAssignments by criteria: {}", criteria);
        Page<CourseAssignmentDTO> page = courseAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-assignments/count} : count all the courseAssignments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-assignments/count")
    public ResponseEntity<Long> countCourseAssignments(CourseAssignmentCriteria criteria) {
        log.debug("REST request to count CourseAssignments by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseAssignmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-assignments/:id} : get the "id" courseAssignment.
     *
     * @param id the id of the courseAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-assignments/{id}")
    public ResponseEntity<CourseAssignmentDTO> getCourseAssignment(@PathVariable Long id) {
        log.debug("REST request to get CourseAssignment : {}", id);
        Optional<CourseAssignmentDTO> courseAssignmentDTO = courseAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseAssignmentDTO);
    }

    /**
     * {@code DELETE  /course-assignments/:id} : delete the "id" courseAssignment.
     *
     * @param id the id of the courseAssignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-assignments/{id}")
    public ResponseEntity<Void> deleteCourseAssignment(@PathVariable Long id) {
        log.debug("REST request to delete CourseAssignment : {}", id);
        courseAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
