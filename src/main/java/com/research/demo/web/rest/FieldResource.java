package com.research.demo.web.rest;
import com.research.demo.service.FieldService;
import com.research.demo.web.rest.errors.BadRequestAlertException;
import com.research.demo.web.rest.util.HeaderUtil;
import com.research.demo.service.dto.FieldDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Field.
 */
@RestController
@RequestMapping("/api")
public class FieldResource {

    private final Logger log = LoggerFactory.getLogger(FieldResource.class);

    private static final String ENTITY_NAME = "field";

    private final FieldService fieldService;

    public FieldResource(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    /**
     * POST  /fields : Create a new field.
     *
     * @param fieldDTO the fieldDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fieldDTO, or with status 400 (Bad Request) if the field has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fields")
    public ResponseEntity<FieldDTO> createField(@RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to save Field : {}", fieldDTO);
        if (fieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new field cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity.created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fields : Updates an existing field.
     *
     * @param fieldDTO the fieldDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fieldDTO,
     * or with status 400 (Bad Request) if the fieldDTO is not valid,
     * or with status 500 (Internal Server Error) if the fieldDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fields")
    public ResponseEntity<FieldDTO> updateField(@RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to update Field : {}", fieldDTO);
        if (fieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fields : get all the fields.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fields in body
     */
    @GetMapping("/fields")
    public List<FieldDTO> getAllFields() {
        log.debug("REST request to get all Fields");
        return fieldService.findAll();
    }

    /**
     * GET  /fields/:id : get the "id" field.
     *
     * @param id the id of the fieldDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fieldDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fields/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable Long id) {
        log.debug("REST request to get Field : {}", id);
        Optional<FieldDTO> fieldDTO = fieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldDTO);
    }

    /**
     * DELETE  /fields/:id : delete the "id" field.
     *
     * @param id the id of the fieldDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        log.debug("REST request to delete Field : {}", id);
        fieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
