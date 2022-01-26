package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseSectionRepository;
import com.mycompany.myapp.service.CourseSectionQueryService;
import com.mycompany.myapp.service.CourseSectionService;
import com.mycompany.myapp.service.criteria.CourseSectionCriteria;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseSection}.
 */
@RestController
@RequestMapping("/api")
public class CourseSectionResource {

    private final Logger log = LoggerFactory.getLogger(CourseSectionResource.class);

    private static final String ENTITY_NAME = "courseSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseSectionService courseSectionService;

    private final CourseSectionRepository courseSectionRepository;

    private final CourseSectionQueryService courseSectionQueryService;

    public CourseSectionResource(
        CourseSectionService courseSectionService,
        CourseSectionRepository courseSectionRepository,
        CourseSectionQueryService courseSectionQueryService
    ) {
        this.courseSectionService = courseSectionService;
        this.courseSectionRepository = courseSectionRepository;
        this.courseSectionQueryService = courseSectionQueryService;
    }

    /**
     * {@code POST  /course-sections} : Create a new courseSection.
     *
     * @param courseSectionDTO the courseSectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseSectionDTO, or with status {@code 400 (Bad Request)} if the courseSection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-sections")
    public ResponseEntity<CourseSectionDTO> createCourseSection(@Valid @RequestBody CourseSectionDTO courseSectionDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseSection : {}", courseSectionDTO);
        if (courseSectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseSectionDTO result = courseSectionService.save(courseSectionDTO);
        return ResponseEntity
            .created(new URI("/api/course-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-sections/:id} : Updates an existing courseSection.
     *
     * @param id the id of the courseSectionDTO to save.
     * @param courseSectionDTO the courseSectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSectionDTO,
     * or with status {@code 400 (Bad Request)} if the courseSectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseSectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-sections/{id}")
    public ResponseEntity<CourseSectionDTO> updateCourseSection(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseSectionDTO courseSectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseSection : {}, {}", id, courseSectionDTO);
        if (courseSectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseSectionDTO result = courseSectionService.save(courseSectionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-sections/:id} : Partial updates given fields of an existing courseSection, field will ignore if it is null
     *
     * @param id the id of the courseSectionDTO to save.
     * @param courseSectionDTO the courseSectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSectionDTO,
     * or with status {@code 400 (Bad Request)} if the courseSectionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseSectionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseSectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-sections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseSectionDTO> partialUpdateCourseSection(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseSectionDTO courseSectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseSection partially : {}, {}", id, courseSectionDTO);
        if (courseSectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseSectionDTO> result = courseSectionService.partialUpdate(courseSectionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSectionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-sections} : get all the courseSections.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseSections in body.
     */
    @GetMapping("/course-sections")
    public ResponseEntity<List<CourseSectionDTO>> getAllCourseSections(
        CourseSectionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseSections by criteria: {}", criteria);
        Page<CourseSectionDTO> page = courseSectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-sections/count} : count all the courseSections.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-sections/count")
    public ResponseEntity<Long> countCourseSections(CourseSectionCriteria criteria) {
        log.debug("REST request to count CourseSections by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseSectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-sections/:id} : get the "id" courseSection.
     *
     * @param id the id of the courseSectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseSectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-sections/{id}")
    public ResponseEntity<CourseSectionDTO> getCourseSection(@PathVariable Long id) {
        log.debug("REST request to get CourseSection : {}", id);
        Optional<CourseSectionDTO> courseSectionDTO = courseSectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseSectionDTO);
    }

    /**
     * {@code DELETE  /course-sections/:id} : delete the "id" courseSection.
     *
     * @param id the id of the courseSectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-sections/{id}")
    public ResponseEntity<Void> deleteCourseSection(@PathVariable Long id) {
        log.debug("REST request to delete CourseSection : {}", id);
        courseSectionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
