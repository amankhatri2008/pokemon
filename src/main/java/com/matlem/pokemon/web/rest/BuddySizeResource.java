package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.BuddySize;
import com.matlem.pokemon.service.BuddySizeService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.BuddySize}.
 */
@RestController
@RequestMapping("/api")
public class BuddySizeResource {

    private final Logger log = LoggerFactory.getLogger(BuddySizeResource.class);

    private static final String ENTITY_NAME = "buddySize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuddySizeService buddySizeService;

    public BuddySizeResource(BuddySizeService buddySizeService) {
        this.buddySizeService = buddySizeService;
    }

    /**
     * {@code POST  /buddy-sizes} : Create a new buddySize.
     *
     * @param buddySize the buddySize to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buddySize, or with status {@code 400 (Bad Request)} if the buddySize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buddy-sizes")
    public ResponseEntity<BuddySize> createBuddySize(@Valid @RequestBody BuddySize buddySize) throws URISyntaxException {
        log.debug("REST request to save BuddySize : {}", buddySize);
        if (buddySize.getId() != null) {
            throw new BadRequestAlertException("A new buddySize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuddySize result = buddySizeService.save(buddySize);
        return ResponseEntity.created(new URI("/api/buddy-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buddy-sizes} : Updates an existing buddySize.
     *
     * @param buddySize the buddySize to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buddySize,
     * or with status {@code 400 (Bad Request)} if the buddySize is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buddySize couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buddy-sizes")
    public ResponseEntity<BuddySize> updateBuddySize(@Valid @RequestBody BuddySize buddySize) throws URISyntaxException {
        log.debug("REST request to update BuddySize : {}", buddySize);
        if (buddySize.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuddySize result = buddySizeService.save(buddySize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buddySize.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /buddy-sizes} : get all the buddySizes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buddySizes in body.
     */
    @GetMapping("/buddy-sizes")
    public ResponseEntity<List<BuddySize>> getAllBuddySizes(Pageable pageable) {
        log.debug("REST request to get a page of BuddySizes");
        Page<BuddySize> page = buddySizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buddy-sizes/:id} : get the "id" buddySize.
     *
     * @param id the id of the buddySize to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buddySize, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buddy-sizes/{id}")
    public ResponseEntity<BuddySize> getBuddySize(@PathVariable Long id) {
        log.debug("REST request to get BuddySize : {}", id);
        Optional<BuddySize> buddySize = buddySizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buddySize);
    }

    /**
     * {@code DELETE  /buddy-sizes/:id} : delete the "id" buddySize.
     *
     * @param id the id of the buddySize to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buddy-sizes/{id}")
    public ResponseEntity<Void> deleteBuddySize(@PathVariable Long id) {
        log.debug("REST request to delete BuddySize : {}", id);
        buddySizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
