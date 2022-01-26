package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseAssignmentOutputRepository;
import com.mycompany.myapp.service.CourseAssignmentOutputQueryService;
import com.mycompany.myapp.service.CourseAssignmentOutputService;
import com.mycompany.myapp.service.criteria.CourseAssignmentOutputCriteria;
import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseAssignmentOutput}.
 */
@RestController
@RequestMapping("/api")
public class CourseAssignmentOutputResource {

    private final Logger log = LoggerFactory.getLogger(CourseAssignmentOutputResource.class);

    private static final String ENTITY_NAME = "courseAssignmentOutput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseAssignmentOutputService courseAssignmentOutputService;

    private final CourseAssignmentOutputRepository courseAssignmentOutputRepository;

    private final CourseAssignmentOutputQueryService courseAssignmentOutputQueryService;

    public CourseAssignmentOutputResource(
        CourseAssignmentOutputService courseAssignmentOutputService,
        CourseAssignmentOutputRepository courseAssignmentOutputRepository,
        CourseAssignmentOutputQueryService courseAssignmentOutputQueryService
    ) {
        this.courseAssignmentOutputService = courseAssignmentOutputService;
        this.courseAssignmentOutputRepository = courseAssignmentOutputRepository;
        this.courseAssignmentOutputQueryService = courseAssignmentOutputQueryService;
    }

    /**
     * {@code POST  /course-assignment-outputs} : Create a new courseAssignmentOutput.
     *
     * @param courseAssignmentOutputDTO the courseAssignmentOutputDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseAssignmentOutputDTO, or with status {@code 400 (Bad Request)} if the courseAssignmentOutput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-assignment-outputs")
    public ResponseEntity<CourseAssignmentOutputDTO> createCourseAssignmentOutput(
        @Valid @RequestBody CourseAssignmentOutputDTO courseAssignmentOutputDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CourseAssignmentOutput : {}", courseAssignmentOutputDTO);
        if (courseAssignmentOutputDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseAssignmentOutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseAssignmentOutputDTO result = courseAssignmentOutputService.save(courseAssignmentOutputDTO);
        return ResponseEntity
            .created(new URI("/api/course-assignment-outputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-assignment-outputs/:id} : Updates an existing courseAssignmentOutput.
     *
     * @param id the id of the courseAssignmentOutputDTO to save.
     * @param courseAssignmentOutputDTO the courseAssignmentOutputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentOutputDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentOutputDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentOutputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-assignment-outputs/{id}")
    public ResponseEntity<CourseAssignmentOutputDTO> updateCourseAssignmentOutput(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseAssignmentOutputDTO courseAssignmentOutputDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseAssignmentOutput : {}, {}", id, courseAssignmentOutputDTO);
        if (courseAssignmentOutputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentOutputDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseAssignmentOutputDTO result = courseAssignmentOutputService.save(courseAssignmentOutputDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentOutputDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-assignment-outputs/:id} : Partial updates given fields of an existing courseAssignmentOutput, field will ignore if it is null
     *
     * @param id the id of the courseAssignmentOutputDTO to save.
     * @param courseAssignmentOutputDTO the courseAssignmentOutputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseAssignmentOutputDTO,
     * or with status {@code 400 (Bad Request)} if the courseAssignmentOutputDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseAssignmentOutputDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseAssignmentOutputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-assignment-outputs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseAssignmentOutputDTO> partialUpdateCourseAssignmentOutput(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseAssignmentOutputDTO courseAssignmentOutputDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseAssignmentOutput partially : {}, {}", id, courseAssignmentOutputDTO);
        if (courseAssignmentOutputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseAssignmentOutputDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseAssignmentOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseAssignmentOutputDTO> result = courseAssignmentOutputService.partialUpdate(courseAssignmentOutputDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseAssignmentOutputDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-assignment-outputs} : get all the courseAssignmentOutputs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseAssignmentOutputs in body.
     */
    @GetMapping("/course-assignment-outputs")
    public ResponseEntity<List<CourseAssignmentOutputDTO>> getAllCourseAssignmentOutputs(
        CourseAssignmentOutputCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseAssignmentOutputs by criteria: {}", criteria);
        Page<CourseAssignmentOutputDTO> page = courseAssignmentOutputQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-assignment-outputs/count} : count all the courseAssignmentOutputs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-assignment-outputs/count")
    public ResponseEntity<Long> countCourseAssignmentOutputs(CourseAssignmentOutputCriteria criteria) {
        log.debug("REST request to count CourseAssignmentOutputs by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseAssignmentOutputQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-assignment-outputs/:id} : get the "id" courseAssignmentOutput.
     *
     * @param id the id of the courseAssignmentOutputDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseAssignmentOutputDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-assignment-outputs/{id}")
    public ResponseEntity<CourseAssignmentOutputDTO> getCourseAssignmentOutput(@PathVariable Long id) {
        log.debug("REST request to get CourseAssignmentOutput : {}", id);
        Optional<CourseAssignmentOutputDTO> courseAssignmentOutputDTO = courseAssignmentOutputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseAssignmentOutputDTO);
    }

    /**
     * {@code DELETE  /course-assignment-outputs/:id} : delete the "id" courseAssignmentOutput.
     *
     * @param id the id of the courseAssignmentOutputDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-assignment-outputs/{id}")
    public ResponseEntity<Void> deleteCourseAssignmentOutput(@PathVariable Long id) {
        log.debug("REST request to delete CourseAssignmentOutput : {}", id);
        courseAssignmentOutputService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
