package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Branchs;
import com.matlem.pokemon.service.BranchsService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.Branchs}.
 */
@RestController
@RequestMapping("/api")
public class BranchsResource {

    private final Logger log = LoggerFactory.getLogger(BranchsResource.class);

    private static final String ENTITY_NAME = "branchs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchsService branchsService;

    public BranchsResource(BranchsService branchsService) {
        this.branchsService = branchsService;
    }

    /**
     * {@code POST  /branchs} : Create a new branchs.
     *
     * @param branchs the branchs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchs, or with status {@code 400 (Bad Request)} if the branchs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branchs")
    public ResponseEntity<Branchs> createBranchs(@Valid @RequestBody Branchs branchs) throws URISyntaxException {
        log.debug("REST request to save Branchs : {}", branchs);
        if (branchs.getId() != null) {
            throw new BadRequestAlertException("A new branchs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Branchs result = branchsService.save(branchs);
        return ResponseEntity.created(new URI("/api/branchs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branchs} : Updates an existing branchs.
     *
     * @param branchs the branchs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchs,
     * or with status {@code 400 (Bad Request)} if the branchs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branchs")
    public ResponseEntity<Branchs> updateBranchs(@Valid @RequestBody Branchs branchs) throws URISyntaxException {
        log.debug("REST request to update Branchs : {}", branchs);
        if (branchs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Branchs result = branchsService.save(branchs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, branchs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /branchs} : get all the branchs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branchs in body.
     */
    @GetMapping("/branchs")
    public ResponseEntity<List<Branchs>> getAllBranchs(Pageable pageable) {
        log.debug("REST request to get a page of Branchs");
        Page<Branchs> page = branchsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /branchs/:id} : get the "id" branchs.
     *
     * @param id the id of the branchs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branchs/{id}")
    public ResponseEntity<Branchs> getBranchs(@PathVariable Long id) {
        log.debug("REST request to get Branchs : {}", id);
        Optional<Branchs> branchs = branchsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(branchs);
    }

    /**
     * {@code DELETE  /branchs/:id} : delete the "id" branchs.
     *
     * @param id the id of the branchs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branchs/{id}")
    public ResponseEntity<Void> deleteBranchs(@PathVariable Long id) {
        log.debug("REST request to delete Branchs : {}", id);
        branchsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
