package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Types;
import com.matlem.pokemon.service.TypesService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.Types}.
 */
@RestController
@RequestMapping("/api")
public class TypesResource {

    private final Logger log = LoggerFactory.getLogger(TypesResource.class);

    private static final String ENTITY_NAME = "types";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypesService typesService;

    public TypesResource(TypesService typesService) {
        this.typesService = typesService;
    }

    /**
     * {@code POST  /types} : Create a new types.
     *
     * @param types the types to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new types, or with status {@code 400 (Bad Request)} if the types has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/types")
    public ResponseEntity<Types> createTypes(@RequestBody Types types) throws URISyntaxException {
        log.debug("REST request to save Types : {}", types);
        if (types.getId() != null) {
            throw new BadRequestAlertException("A new types cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Types result = typesService.save(types);
        return ResponseEntity.created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /types} : Updates an existing types.
     *
     * @param types the types to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated types,
     * or with status {@code 400 (Bad Request)} if the types is not valid,
     * or with status {@code 500 (Internal Server Error)} if the types couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/types")
    public ResponseEntity<Types> updateTypes(@RequestBody Types types) throws URISyntaxException {
        log.debug("REST request to update Types : {}", types);
        if (types.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Types result = typesService.save(types);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, types.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /types} : get all the types.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of types in body.
     */
    @GetMapping("/types")
    public ResponseEntity<List<Types>> getAllTypes(Pageable pageable) {
        log.debug("REST request to get a page of Types");
        Page<Types> page = typesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /types/:id} : get the "id" types.
     *
     * @param id the id of the types to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the types, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/types/{id}")
    public ResponseEntity<Types> getTypes(@PathVariable Long id) {
        log.debug("REST request to get Types : {}", id);
        Optional<Types> types = typesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(types);
    }

    /**
     * {@code DELETE  /types/:id} : delete the "id" types.
     *
     * @param id the id of the types to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/types/{id}")
    public ResponseEntity<Void> deleteTypes(@PathVariable Long id) {
        log.debug("REST request to delete Types : {}", id);
        typesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }


}
