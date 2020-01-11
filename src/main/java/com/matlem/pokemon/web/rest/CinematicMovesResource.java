package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.CinematicMoves;
import com.matlem.pokemon.service.CinematicMovesService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.CinematicMoves}.
 */
@RestController
@RequestMapping("/api")
public class CinematicMovesResource {

    private final Logger log = LoggerFactory.getLogger(CinematicMovesResource.class);

    private static final String ENTITY_NAME = "cinematicMoves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CinematicMovesService cinematicMovesService;

    public CinematicMovesResource(CinematicMovesService cinematicMovesService) {
        this.cinematicMovesService = cinematicMovesService;
    }

    /**
     * {@code POST  /cinematic-moves} : Create a new cinematicMoves.
     *
     * @param cinematicMoves the cinematicMoves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cinematicMoves, or with status {@code 400 (Bad Request)} if the cinematicMoves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cinematic-moves")
    public ResponseEntity<CinematicMoves> createCinematicMoves(@Valid @RequestBody CinematicMoves cinematicMoves) throws URISyntaxException {
        log.debug("REST request to save CinematicMoves : {}", cinematicMoves);
        if (cinematicMoves.getId() != null) {
            throw new BadRequestAlertException("A new cinematicMoves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CinematicMoves result = cinematicMovesService.save(cinematicMoves);
        return ResponseEntity.created(new URI("/api/cinematic-moves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cinematic-moves} : Updates an existing cinematicMoves.
     *
     * @param cinematicMoves the cinematicMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cinematicMoves,
     * or with status {@code 400 (Bad Request)} if the cinematicMoves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cinematicMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cinematic-moves")
    public ResponseEntity<CinematicMoves> updateCinematicMoves(@Valid @RequestBody CinematicMoves cinematicMoves) throws URISyntaxException {
        log.debug("REST request to update CinematicMoves : {}", cinematicMoves);
        if (cinematicMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CinematicMoves result = cinematicMovesService.save(cinematicMoves);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cinematicMoves.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cinematic-moves} : get all the cinematicMoves.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cinematicMoves in body.
     */
    @GetMapping("/cinematic-moves")
    public ResponseEntity<List<CinematicMoves>> getAllCinematicMoves(Pageable pageable) {
        log.debug("REST request to get a page of CinematicMoves");
        Page<CinematicMoves> page = cinematicMovesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cinematic-moves/:id} : get the "id" cinematicMoves.
     *
     * @param id the id of the cinematicMoves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cinematicMoves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cinematic-moves/{id}")
    public ResponseEntity<CinematicMoves> getCinematicMoves(@PathVariable Long id) {
        log.debug("REST request to get CinematicMoves : {}", id);
        Optional<CinematicMoves> cinematicMoves = cinematicMovesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cinematicMoves);
    }

    /**
     * {@code DELETE  /cinematic-moves/:id} : delete the "id" cinematicMoves.
     *
     * @param id the id of the cinematicMoves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cinematic-moves/{id}")
    public ResponseEntity<Void> deleteCinematicMoves(@PathVariable Long id) {
        log.debug("REST request to delete CinematicMoves : {}", id);
        cinematicMovesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
