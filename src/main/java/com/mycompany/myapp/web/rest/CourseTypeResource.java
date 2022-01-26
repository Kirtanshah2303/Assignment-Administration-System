package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourseTypeRepository;
import com.mycompany.myapp.service.CourseTypeQueryService;
import com.mycompany.myapp.service.CourseTypeService;
import com.mycompany.myapp.service.criteria.CourseTypeCriteria;
import com.mycompany.myapp.service.dto.CourseTypeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CourseType}.
 */
@RestController
@RequestMapping("/api")
public class CourseTypeResource {

    private final Logger log = LoggerFactory.getLogger(CourseTypeResource.class);

    private static final String ENTITY_NAME = "courseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseTypeService courseTypeService;

    private final CourseTypeRepository courseTypeRepository;

    private final CourseTypeQueryService courseTypeQueryService;

    public CourseTypeResource(
        CourseTypeService courseTypeService,
        CourseTypeRepository courseTypeRepository,
        CourseTypeQueryService courseTypeQueryService
    ) {
        this.courseTypeService = courseTypeService;
        this.courseTypeRepository = courseTypeRepository;
        this.courseTypeQueryService = courseTypeQueryService;
    }

    /**
     * {@code POST  /course-types} : Create a new courseType.
     *
     * @param courseTypeDTO the courseTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseTypeDTO, or with status {@code 400 (Bad Request)} if the courseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-types")
    public ResponseEntity<CourseTypeDTO> createCourseType(@Valid @RequestBody CourseTypeDTO courseTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CourseType : {}", courseTypeDTO);
        if (courseTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseTypeDTO result = courseTypeService.save(courseTypeDTO);
        return ResponseEntity
            .created(new URI("/api/course-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-types/:id} : Updates an existing courseType.
     *
     * @param id the id of the courseTypeDTO to save.
     * @param courseTypeDTO the courseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the courseTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-types/{id}")
    public ResponseEntity<CourseTypeDTO> updateCourseType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseTypeDTO courseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseType : {}, {}", id, courseTypeDTO);
        if (courseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseTypeDTO result = courseTypeService.save(courseTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-types/:id} : Partial updates given fields of an existing courseType, field will ignore if it is null
     *
     * @param id the id of the courseTypeDTO to save.
     * @param courseTypeDTO the courseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the courseTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseTypeDTO> partialUpdateCourseType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseTypeDTO courseTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseType partially : {}, {}", id, courseTypeDTO);
        if (courseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseTypeDTO> result = courseTypeService.partialUpdate(courseTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-types} : get all the courseTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseTypes in body.
     */
    @GetMapping("/course-types")
    public ResponseEntity<List<CourseTypeDTO>> getAllCourseTypes(
        CourseTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseTypes by criteria: {}", criteria);
        Page<CourseTypeDTO> page = courseTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-types/count} : count all the courseTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-types/count")
    public ResponseEntity<Long> countCourseTypes(CourseTypeCriteria criteria) {
        log.debug("REST request to count CourseTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-types/:id} : get the "id" courseType.
     *
     * @param id the id of the courseTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-types/{id}")
    public ResponseEntity<CourseTypeDTO> getCourseType(@PathVariable Long id) {
        log.debug("REST request to get CourseType : {}", id);
        Optional<CourseTypeDTO> courseTypeDTO = courseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseTypeDTO);
    }

    /**
     * {@code DELETE  /course-types/:id} : delete the "id" courseType.
     *
     * @param id the id of the courseTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-types/{id}")
    public ResponseEntity<Void> deleteCourseType(@PathVariable Long id) {
        log.debug("REST request to delete CourseType : {}", id);
        courseTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
