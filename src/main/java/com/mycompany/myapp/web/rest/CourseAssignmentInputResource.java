package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseAssignmentInputRepository;
import com.mycompany.myapp.service.CourseAssignmentInputQueryService;
import com.mycompany.myapp.service.CourseAssignmentInputService;
import com.mycompany.myapp.service.criteria.CourseAssignmentInputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseAssignmentInput}.
 */
@RestController
@RequestMapping("/api")
public class CourseAssignmentInputResource {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentInputResource.class);

    private static final String ENTITY_NAME = "courseAssignmentInput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseAssignmentInputService courseAssignmentInputService;

    private final CourseAssignmentInputRepository courseAssignmentInputRepository;

    private final CourseAssignmentInputQueryService courseAssignmentInputQueryService;

    public CourseAssignmentInputResource(
        CourseAssignmentInputService courseAssignmentInputService,
        CourseAssignmentInputRepository courseAssignmentInputRepository,
        CourseAssignmentInputQueryService courseAssignmentInputQueryService
    ) {
        this.courseAssignmentInputService = courseAssignmentInputService;
        this.courseAssignmentInputRepository = courseAssignmentInputRepository;
        this.courseAssignmentInputQueryService = courseAssignmentInputQueryService;
    }

    /**
     * {@code POST  /course-assignment-inputs} : Create a new courseAssignmentInput.
     *
     * @param courseAssignmentInputDTO the courseAssignmentInputDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseAssignmentInputDTO, or with status {@code 400 (Bad Request)} if the courseAssignmentInput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-assignment-inputs")
    public ResponseEntity<CourseAssignmentInputDTO> createCourseAssignmentInput(
        @Valid @RequestBody CourseAssignmentInputDTO courseAssignmentInputDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CourseAssignmentInput : {}", courseAssignmentInputDTO);
        if (courseAssignmentInputDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseAssignmentInput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseAssignmentInputDTO result = courseAssignmentInputService.save(courseAssignmentInputDTO);
        return ResponseEntity
            .created(new URI("/api/course-assignment-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-assignment-inputs/:id} : Updates an existing courseAssignmentInput.
     *
     * @param id the id of the courseAssignmentInputDTO to save.
     * @param courseAssignmentInputDTO the courseAssignmentInputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentInputDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentInputDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentInputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-assignment-inputs/{id}")
    public ResponseEntity<CourseAssignmentInputDTO> updateCourseAssignmentInput(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseAssignmentInputDTO courseAssignmentInputDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseAssignmentInput : {}, {}", id, courseAssignmentInputDTO);
        if (courseAssignmentInputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentInputDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseAssignmentInputDTO result = courseAssignmentInputService.save(courseAssignmentInputDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentInputDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-assignment-inputs/:id} : Partial updates given fields of an existing courseAssignmentInput, field will ignore if it is null
     *
     * @param id the id of the courseAssignmentInputDTO to save.
     * @param courseAssignmentInputDTO the courseAssignmentInputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentInputDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentInputDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseAssignmentInputDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentInputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-assignment-inputs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseAssignmentInputDTO> partialUpdateCourseAssignmentInput(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseAssignmentInputDTO courseAssignmentInputDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseAssignmentInput partially : {}, {}", id, courseAssignmentInputDTO);
        if (courseAssignmentInputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentInputDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseAssignmentInputDTO> result = courseAssignmentInputService.partialUpdate(courseAssignmentInputDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentInputDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-assignment-inputs} : get all the courseAssignmentInputs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseAssignmentInputs in body.
     */
    @GetMapping("/course-assignment-inputs")
    public ResponseEntity<List<CourseAssignmentInputDTO>> getAllCourseAssignmentInputs(
        CourseAssignmentInputCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseAssignmentInputs by criteria: {}", criteria);
        Page<CourseAssignmentInputDTO> page = courseAssignmentInputQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-assignment-inputs/count} : count all the courseAssignmentInputs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-assignment-inputs/count")
    public ResponseEntity<Long> countCourseAssignmentInputs(CourseAssignmentInputCriteria criteria) {
        log.debug("REST request to count CourseAssignmentInputs by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseAssignmentInputQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-assignment-inputs/:id} : get the "id" courseAssignmentInput.
     *
     * @param id the id of the courseAssignmentInputDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseAssignmentInputDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-assignment-inputs/{id}")
    public ResponseEntity<CourseAssignmentInputDTO> getCourseAssignmentInput(@PathVariable Long id) {
        log.debug("REST request to get CourseAssignmentInput : {}", id);
        Optional<CourseAssignmentInputDTO> courseAssignmentInputDTO = courseAssignmentInputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseAssignmentInputDTO);
    }

    /**
     * {@code DELETE  /course-assignment-inputs/:id} : delete the "id" courseAssignmentInput.
     *
     * @param id the id of the courseAssignmentInputDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-assignment-inputs/{id}")
    public ResponseEntity<Void> deleteCourseAssignmentInput(@PathVariable Long id) {
        log.debug("REST request to delete CourseAssignmentInput : {}", id);
        courseAssignmentInputService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
