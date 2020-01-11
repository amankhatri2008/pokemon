package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Encounter;
import com.matlem.pokemon.service.EncounterService;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.Encounter}.
 */
@RestController
@RequestMapping("/api")
public class EncounterResource {

    private final Logger log = LoggerFactory.getLogger(EncounterResource.class);

    private static final String ENTITY_NAME = "encounter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncounterService encounterService;

    public EncounterResource(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    /**
     * {@code POST  /encounters} : Create a new encounter.
     *
     * @param encounter the encounter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encounter, or with status {@code 400 (Bad Request)} if the encounter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encounters")
    public ResponseEntity<Encounter> createEncounter(@RequestBody Encounter encounter) throws URISyntaxException {
        log.debug("REST request to save Encounter : {}", encounter);
        if (encounter.getId() != null) {
            throw new BadRequestAlertException("A new encounter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Encounter result = encounterService.save(encounter);
        return ResponseEntity.created(new URI("/api/encounters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encounters} : Updates an existing encounter.
     *
     * @param encounter the encounter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounter,
     * or with status {@code 400 (Bad Request)} if the encounter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encounter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encounters")
    public ResponseEntity<Encounter> updateEncounter(@RequestBody Encounter encounter) throws URISyntaxException {
        log.debug("REST request to update Encounter : {}", encounter);
        if (encounter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Encounter result = encounterService.save(encounter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, encounter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encounters} : get all the encounters.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encounters in body.
     */
    @GetMapping("/encounters")
    public ResponseEntity<List<Encounter>> getAllEncounters(Pageable pageable) {
        log.debug("REST request to get a page of Encounters");
        Page<Encounter> page = encounterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encounters/:id} : get the "id" encounter.
     *
     * @param id the id of the encounter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encounter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encounters/{id}")
    public ResponseEntity<Encounter> getEncounter(@PathVariable Long id) {
        log.debug("REST request to get Encounter : {}", id);
        Optional<Encounter> encounter = encounterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encounter);
    }

    /**
     * {@code DELETE  /encounters/:id} : delete the "id" encounter.
     *
     * @param id the id of the encounter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encounters/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable Long id) {
        log.debug("REST request to delete Encounter : {}", id);
        encounterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
