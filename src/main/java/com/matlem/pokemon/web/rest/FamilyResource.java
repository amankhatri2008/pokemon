package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Family;
import com.matlem.pokemon.service.FamilyService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.Family}.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {

    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);

    private static final String ENTITY_NAME = "family";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyService familyService;

    public FamilyResource(FamilyService familyService) {
        this.familyService = familyService;
    }

    /**
     * {@code POST  /families} : Create a new family.
     *
     * @param family the family to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new family, or with status {@code 400 (Bad Request)} if the family has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/families")
    public ResponseEntity<Family> createFamily(@Valid @RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to save Family : {}", family);
        if (family.getId() != null) {
            throw new BadRequestAlertException("A new family cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Family result = familyService.save(family);
        return ResponseEntity.created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /families} : Updates an existing family.
     *
     * @param family the family to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated family,
     * or with status {@code 400 (Bad Request)} if the family is not valid,
     * or with status {@code 500 (Internal Server Error)} if the family couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/families")
    public ResponseEntity<Family> updateFamily(@Valid @RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to update Family : {}", family);
        if (family.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Family result = familyService.save(family);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, family.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /families} : get all the families.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of families in body.
     */
    @GetMapping("/families")
    public ResponseEntity<List<Family>> getAllFamilies(Pageable pageable) {
        log.debug("REST request to get a page of Families");
        Page<Family> page = familyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /families/:id} : get the "id" family.
     *
     * @param id the id of the family to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the family, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/families/{id}")
    public ResponseEntity<Family> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Optional<Family> family = familyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(family);
    }

    /**
     * {@code DELETE  /families/:id} : delete the "id" family.
     *
     * @param id the id of the family to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/families/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);
        familyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
