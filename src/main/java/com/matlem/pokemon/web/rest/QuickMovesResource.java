package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.QuickMoves;
import com.matlem.pokemon.service.QuickMovesService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.QuickMoves}.
 */
@RestController
@RequestMapping("/api")
public class QuickMovesResource {

    private final Logger log = LoggerFactory.getLogger(QuickMovesResource.class);

    private static final String ENTITY_NAME = "quickMoves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuickMovesService quickMovesService;

    public QuickMovesResource(QuickMovesService quickMovesService) {
        this.quickMovesService = quickMovesService;
    }

    /**
     * {@code POST  /quick-moves} : Create a new quickMoves.
     *
     * @param quickMoves the quickMoves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quickMoves, or with status {@code 400 (Bad Request)} if the quickMoves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quick-moves")
    public ResponseEntity<QuickMoves> createQuickMoves(@Valid @RequestBody QuickMoves quickMoves) throws URISyntaxException {
        log.debug("REST request to save QuickMoves : {}", quickMoves);
        if (quickMoves.getId() != null) {
            throw new BadRequestAlertException("A new quickMoves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuickMoves result = quickMovesService.save(quickMoves);
        return ResponseEntity.created(new URI("/api/quick-moves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quick-moves} : Updates an existing quickMoves.
     *
     * @param quickMoves the quickMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quickMoves,
     * or with status {@code 400 (Bad Request)} if the quickMoves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quickMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quick-moves")
    public ResponseEntity<QuickMoves> updateQuickMoves(@Valid @RequestBody QuickMoves quickMoves) throws URISyntaxException {
        log.debug("REST request to update QuickMoves : {}", quickMoves);
        if (quickMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuickMoves result = quickMovesService.save(quickMoves);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quickMoves.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quick-moves} : get all the quickMoves.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quickMoves in body.
     */
    @GetMapping("/quick-moves")
    public ResponseEntity<List<QuickMoves>> getAllQuickMoves(Pageable pageable) {
        log.debug("REST request to get a page of QuickMoves");
        Page<QuickMoves> page = quickMovesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quick-moves/:id} : get the "id" quickMoves.
     *
     * @param id the id of the quickMoves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quickMoves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quick-moves/{id}")
    public ResponseEntity<QuickMoves> getQuickMoves(@PathVariable Long id) {
        log.debug("REST request to get QuickMoves : {}", id);
        Optional<QuickMoves> quickMoves = quickMovesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quickMoves);
    }

    /**
     * {@code DELETE  /quick-moves/:id} : delete the "id" quickMoves.
     *
     * @param id the id of the quickMoves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quick-moves/{id}")
    public ResponseEntity<Void> deleteQuickMoves(@PathVariable Long id) {
        log.debug("REST request to delete QuickMoves : {}", id);
        quickMovesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
