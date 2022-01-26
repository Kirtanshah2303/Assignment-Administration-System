package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseReviewStatusRepository;
import com.mycompany.myapp.service.CourseReviewStatusQueryService;
import com.mycompany.myapp.service.CourseReviewStatusService;
import com.mycompany.myapp.service.criteria.CourseReviewStatusCriteria;
import com.mycompany.myapp.service.dto.CourseReviewStatusDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseReviewStatus}.
 */
@RestController
@RequestMapping("/api")
public class CourseReviewStatusResource {

    private final Logger log = LoggerFactory.getLogger(CourseReviewStatusResource.class);

    private static final String ENTITY_NAME = "courseReviewStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseReviewStatusService courseReviewStatusService;

    private final CourseReviewStatusRepository courseReviewStatusRepository;

    private final CourseReviewStatusQueryService courseReviewStatusQueryService;

    public CourseReviewStatusResource(
        CourseReviewStatusService courseReviewStatusService,
        CourseReviewStatusRepository courseReviewStatusRepository,
        CourseReviewStatusQueryService courseReviewStatusQueryService
    ) {
        this.courseReviewStatusService = courseReviewStatusService;
        this.courseReviewStatusRepository = courseReviewStatusRepository;
        this.courseReviewStatusQueryService = courseReviewStatusQueryService;
    }

    /**
     * {@code POST  /course-review-statuses} : Create a new courseReviewStatus.
     *
     * @param courseReviewStatusDTO the courseReviewStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseReviewStatusDTO, or with status {@code 400 (Bad Request)} if the courseReviewStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-review-statuses")
    public ResponseEntity<CourseReviewStatusDTO> createCourseReviewStatus(@Valid @RequestBody CourseReviewStatusDTO courseReviewStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseReviewStatus : {}", courseReviewStatusDTO);
        if (courseReviewStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseReviewStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseReviewStatusDTO result = courseReviewStatusService.save(courseReviewStatusDTO);
        return ResponseEntity
            .created(new URI("/api/course-review-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-review-statuses/:id} : Updates an existing courseReviewStatus.
     *
     * @param id the id of the courseReviewStatusDTO to save.
     * @param courseReviewStatusDTO the courseReviewStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseReviewStatusDTO,
     * or with status {@code 400 (Bad Request)} if the courseReviewStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseReviewStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-review-statuses/{id}")
    public ResponseEntity<CourseReviewStatusDTO> updateCourseReviewStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseReviewStatusDTO courseReviewStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseReviewStatus : {}, {}", id, courseReviewStatusDTO);
        if (courseReviewStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseReviewStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseReviewStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseReviewStatusDTO result = courseReviewStatusService.save(courseReviewStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseReviewStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-review-statuses/:id} : Partial updates given fields of an existing courseReviewStatus, field will ignore if it is null
     *
     * @param id the id of the courseReviewStatusDTO to save.
     * @param courseReviewStatusDTO the courseReviewStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseReviewStatusDTO,
     * or with status {@code 400 (Bad Request)} if the courseReviewStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseReviewStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseReviewStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-review-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseReviewStatusDTO> partialUpdateCourseReviewStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseReviewStatusDTO courseReviewStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseReviewStatus partially : {}, {}", id, courseReviewStatusDTO);
        if (courseReviewStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseReviewStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseReviewStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseReviewStatusDTO> result = courseReviewStatusService.partialUpdate(courseReviewStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseReviewStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-review-statuses} : get all the courseReviewStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseReviewStatuses in body.
     */
    @GetMapping("/course-review-statuses")
    public ResponseEntity<List<CourseReviewStatusDTO>> getAllCourseReviewStatuses(
        CourseReviewStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseReviewStatuses by criteria: {}", criteria);
        Page<CourseReviewStatusDTO> page = courseReviewStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-review-statuses/count} : count all the courseReviewStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-review-statuses/count")
    public ResponseEntity<Long> countCourseReviewStatuses(CourseReviewStatusCriteria criteria) {
        log.debug("REST request to count CourseReviewStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseReviewStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-review-statuses/:id} : get the "id" courseReviewStatus.
     *
     * @param id the id of the courseReviewStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseReviewStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-review-statuses/{id}")
    public ResponseEntity<CourseReviewStatusDTO> getCourseReviewStatus(@PathVariable Long id) {
        log.debug("REST request to get CourseReviewStatus : {}", id);
        Optional<CourseReviewStatusDTO> courseReviewStatusDTO = courseReviewStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseReviewStatusDTO);
    }

    /**
     * {@code DELETE  /course-review-statuses/:id} : delete the "id" courseReviewStatus.
     *
     * @param id the id of the courseReviewStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-review-statuses/{id}")
    public ResponseEntity<Void> deleteCourseReviewStatus(@PathVariable Long id) {
        log.debug("REST request to delete CourseReviewStatus : {}", id);
        courseReviewStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
