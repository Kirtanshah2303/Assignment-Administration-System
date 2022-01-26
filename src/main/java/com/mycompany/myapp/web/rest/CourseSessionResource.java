package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseSessionRepository;
import com.mycompany.myapp.service.CourseSessionQueryService;
import com.mycompany.myapp.service.CourseSessionService;
import com.mycompany.myapp.service.criteria.CourseSessionCriteria;
import com.mycompany.myapp.service.dto.CourseSessionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseSession}.
 */
@RestController
@RequestMapping("/api")
public class CourseSessionResource {

    private final Logger log = LoggerFactory.getLogger(CourseSessionResource.class);

    private static final String ENTITY_NAME = "courseSession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseSessionService courseSessionService;

    private final CourseSessionRepository courseSessionRepository;

    private final CourseSessionQueryService courseSessionQueryService;

    public CourseSessionResource(
        CourseSessionService courseSessionService,
        CourseSessionRepository courseSessionRepository,
        CourseSessionQueryService courseSessionQueryService
    ) {
        this.courseSessionService = courseSessionService;
        this.courseSessionRepository = courseSessionRepository;
        this.courseSessionQueryService = courseSessionQueryService;
    }

    /**
     * {@code POST  /course-sessions} : Create a new courseSession.
     *
     * @param courseSessionDTO the courseSessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseSessionDTO, or with status {@code 400 (Bad Request)} if the courseSession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-sessions")
    public ResponseEntity<CourseSessionDTO> createCourseSession(@Valid @RequestBody CourseSessionDTO courseSessionDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseSession : {}", courseSessionDTO);
        if (courseSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseSessionDTO result = courseSessionService.save(courseSessionDTO);
        return ResponseEntity
            .created(new URI("/api/course-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-sessions/:id} : Updates an existing courseSession.
     *
     * @param id the id of the courseSessionDTO to save.
     * @param courseSessionDTO the courseSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSessionDTO,
     * or with status {@code 400 (Bad Request)} if the courseSessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-sessions/{id}")
    public ResponseEntity<CourseSessionDTO> updateCourseSession(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseSessionDTO courseSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseSession : {}, {}", id, courseSessionDTO);
        if (courseSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseSessionDTO result = courseSessionService.save(courseSessionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-sessions/:id} : Partial updates given fields of an existing courseSession, field will ignore if it is null
     *
     * @param id the id of the courseSessionDTO to save.
     * @param courseSessionDTO the courseSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseSessionDTO,
     * or with status {@code 400 (Bad Request)} if the courseSessionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseSessionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-sessions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseSessionDTO> partialUpdateCourseSession(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseSessionDTO courseSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseSession partially : {}, {}", id, courseSessionDTO);
        if (courseSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseSessionDTO> result = courseSessionService.partialUpdate(courseSessionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseSessionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-sessions} : get all the courseSessions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseSessions in body.
     */
    @GetMapping("/course-sessions")
    public ResponseEntity<List<CourseSessionDTO>> getAllCourseSessions(
        CourseSessionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseSessions by criteria: {}", criteria);
        Page<CourseSessionDTO> page = courseSessionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-sessions/count} : count all the courseSessions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-sessions/count")
    public ResponseEntity<Long> countCourseSessions(CourseSessionCriteria criteria) {
        log.debug("REST request to count CourseSessions by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseSessionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-sessions/:id} : get the "id" courseSession.
     *
     * @param id the id of the courseSessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseSessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-sessions/{id}")
    public ResponseEntity<CourseSessionDTO> getCourseSession(@PathVariable Long id) {
        log.debug("REST request to get CourseSession : {}", id);
        Optional<CourseSessionDTO> courseSessionDTO = courseSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseSessionDTO);
    }

    /**
     * {@code DELETE  /course-sessions/:id} : delete the "id" courseSession.
     *
     * @param id the id of the courseSessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-sessions/{id}")
    public ResponseEntity<Void> deleteCourseSession(@PathVariable Long id) {
        log.debug("REST request to delete CourseSession : {}", id);
        courseSessionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
