package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.EncounterType;
import com.matlem.pokemon.service.EncounterTypeService;
import com.matlem.pokemon.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.EncounterType}.
 */
@RestController
@RequestMapping("/api")
public class EncounterTypeResource {

    private final Logger log = LoggerFactory.getLogger(EncounterTypeResource.class);

    private static final String ENTITY_NAME = "encounterType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncounterTypeService encounterTypeService;

    public EncounterTypeResource(EncounterTypeService encounterTypeService) {
        this.encounterTypeService = encounterTypeService;
    }

    /**
     * {@code POST  /encounter-types} : Create a new encounterType.
     *
     * @param encounterType the encounterType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encounterType, or with status {@code 400 (Bad Request)} if the encounterType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encounter-types")
    public ResponseEntity<EncounterType> createEncounterType(@Valid @RequestBody EncounterType encounterType) throws URISyntaxException {
        log.debug("REST request to save EncounterType : {}", encounterType);
        if (encounterType.getId() != null) {
            throw new BadRequestAlertException("A new encounterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncounterType result = encounterTypeService.save(encounterType);
        return ResponseEntity.created(new URI("/api/encounter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encounter-types} : Updates an existing encounterType.
     *
     * @param encounterType the encounterType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounterType,
     * or with status {@code 400 (Bad Request)} if the encounterType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encounterType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encounter-types")
    public ResponseEntity<EncounterType> updateEncounterType(@Valid @RequestBody EncounterType encounterType) throws URISyntaxException {
        log.debug("REST request to update EncounterType : {}", encounterType);
        if (encounterType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EncounterType result = encounterTypeService.save(encounterType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, encounterType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encounter-types} : get all the encounterTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encounterTypes in body.
     */
    @GetMapping("/encounter-types")
    public ResponseEntity<List<EncounterType>> getAllEncounterTypes(Pageable pageable) {
        log.debug("REST request to get a page of EncounterTypes");
        Page<EncounterType> page = encounterTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encounter-types/:id} : get the "id" encounterType.
     *
     * @param id the id of the encounterType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encounterType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encounter-types/{id}")
    public ResponseEntity<EncounterType> getEncounterType(@PathVariable Long id) {
        log.debug("REST request to get EncounterType : {}", id);
        Optional<EncounterType> encounterType = encounterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encounterType);
    }

    /**
     * {@code DELETE  /encounter-types/:id} : delete the "id" encounterType.
     *
     * @param id the id of the encounterType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encounter-types/{id}")
    public ResponseEntity<Void> deleteEncounterType(@PathVariable Long id) {
        log.debug("REST request to delete EncounterType : {}", id);
        encounterTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
