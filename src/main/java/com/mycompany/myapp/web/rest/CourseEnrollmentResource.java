package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseEnrollmentRepository;
import com.mycompany.myapp.service.CourseEnrollmentQueryService;
import com.mycompany.myapp.service.CourseEnrollmentService;
import com.mycompany.myapp.service.criteria.CourseEnrollmentCriteria;
import com.mycompany.myapp.service.dto.CourseEnrollmentDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseEnrollment}.
 */
@RestController
@RequestMapping("/api")
public class CourseEnrollmentResource {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentResource.class);

    private static final String ENTITY_NAME = "courseEnrollment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseEnrollmentService courseEnrollmentService;

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CourseEnrollmentQueryService courseEnrollmentQueryService;

    public CourseEnrollmentResource(
        CourseEnrollmentService courseEnrollmentService,
        CourseEnrollmentRepository courseEnrollmentRepository,
        CourseEnrollmentQueryService courseEnrollmentQueryService
    ) {
        this.courseEnrollmentService = courseEnrollmentService;
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseEnrollmentQueryService = courseEnrollmentQueryService;
    }

    /**
     * {@code POST  /course-enrollments} : Create a new courseEnrollment.
     *
     * @param courseEnrollmentDTO the courseEnrollmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseEnrollmentDTO, or with status {@code 400 (Bad Request)} if the courseEnrollment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-enrollments")
    public ResponseEntity<CourseEnrollmentDTO> createCourseEnrollment(@Valid @RequestBody CourseEnrollmentDTO courseEnrollmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseEnrollment : {}", courseEnrollmentDTO);
        if (courseEnrollmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseEnrollment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseEnrollmentDTO result = courseEnrollmentService.save(courseEnrollmentDTO);
        return ResponseEntity
            .created(new URI("/api/course-enrollments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-enrollments/:id} : Updates an existing courseEnrollment.
     *
     * @param id the id of the courseEnrollmentDTO to save.
     * @param courseEnrollmentDTO the courseEnrollmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseEnrollmentDTO,
     * or with status {@code 400 (Bad Request)} if the courseEnrollmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseEnrollmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-enrollments/{id}")
    public ResponseEntity<CourseEnrollmentDTO> updateCourseEnrollment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseEnrollmentDTO courseEnrollmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseEnrollment : {}, {}", id, courseEnrollmentDTO);
        if (courseEnrollmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseEnrollmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseEnrollmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseEnrollmentDTO result = courseEnrollmentService.save(courseEnrollmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseEnrollmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-enrollments/:id} : Partial updates given fields of an existing courseEnrollment, field will ignore if it is null
     *
     * @param id the id of the courseEnrollmentDTO to save.
     * @param courseEnrollmentDTO the courseEnrollmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseEnrollmentDTO,
     * or with status {@code 400 (Bad Request)} if the courseEnrollmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseEnrollmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseEnrollmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-enrollments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseEnrollmentDTO> partialUpdateCourseEnrollment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseEnrollmentDTO courseEnrollmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseEnrollment partially : {}, {}", id, courseEnrollmentDTO);
        if (courseEnrollmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseEnrollmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseEnrollmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseEnrollmentDTO> result = courseEnrollmentService.partialUpdate(courseEnrollmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseEnrollmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-enrollments} : get all the courseEnrollments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseEnrollments in body.
     */
    @GetMapping("/course-enrollments")
    public ResponseEntity<List<CourseEnrollmentDTO>> getAllCourseEnrollments(
        CourseEnrollmentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseEnrollments by criteria: {}", criteria);
        Page<CourseEnrollmentDTO> page = courseEnrollmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-enrollments/count} : count all the courseEnrollments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-enrollments/count")
    public ResponseEntity<Long> countCourseEnrollments(CourseEnrollmentCriteria criteria) {
        log.debug("REST request to count CourseEnrollments by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseEnrollmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-enrollments/:id} : get the "id" courseEnrollment.
     *
     * @param id the id of the courseEnrollmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseEnrollmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-enrollments/{id}")
    public ResponseEntity<CourseEnrollmentDTO> getCourseEnrollment(@PathVariable Long id) {
        log.debug("REST request to get CourseEnrollment : {}", id);
        Optional<CourseEnrollmentDTO> courseEnrollmentDTO = courseEnrollmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseEnrollmentDTO);
    }

    /**
     * {@code DELETE  /course-enrollments/:id} : delete the "id" courseEnrollment.
     *
     * @param id the id of the courseEnrollmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-enrollments/{id}")
    public ResponseEntity<Void> deleteCourseEnrollment(@PathVariable Long id) {
        log.debug("REST request to delete CourseEnrollment : {}", id);
        courseEnrollmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
