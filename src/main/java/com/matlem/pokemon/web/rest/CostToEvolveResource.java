package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.CostToEvolve;
import com.matlem.pokemon.service.CostToEvolveService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.CostToEvolve}.
 */
@RestController
@RequestMapping("/api")
public class CostToEvolveResource {

    private final Logger log = LoggerFactory.getLogger(CostToEvolveResource.class);

    private static final String ENTITY_NAME = "costToEvolve";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CostToEvolveService costToEvolveService;

    public CostToEvolveResource(CostToEvolveService costToEvolveService) {
        this.costToEvolveService = costToEvolveService;
    }

    /**
     * {@code POST  /cost-to-evolves} : Create a new costToEvolve.
     *
     * @param costToEvolve the costToEvolve to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new costToEvolve, or with status {@code 400 (Bad Request)} if the costToEvolve has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cost-to-evolves")
    public ResponseEntity<CostToEvolve> createCostToEvolve(@RequestBody CostToEvolve costToEvolve) throws URISyntaxException {
        log.debug("REST request to save CostToEvolve : {}", costToEvolve);
        if (costToEvolve.getId() != null) {
            throw new BadRequestAlertException("A new costToEvolve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostToEvolve result = costToEvolveService.save(costToEvolve);
        return ResponseEntity.created(new URI("/api/cost-to-evolves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cost-to-evolves} : Updates an existing costToEvolve.
     *
     * @param costToEvolve the costToEvolve to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costToEvolve,
     * or with status {@code 400 (Bad Request)} if the costToEvolve is not valid,
     * or with status {@code 500 (Internal Server Error)} if the costToEvolve couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cost-to-evolves")
    public ResponseEntity<CostToEvolve> updateCostToEvolve(@RequestBody CostToEvolve costToEvolve) throws URISyntaxException {
        log.debug("REST request to update CostToEvolve : {}", costToEvolve);
        if (costToEvolve.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CostToEvolve result = costToEvolveService.save(costToEvolve);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, costToEvolve.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cost-to-evolves} : get all the costToEvolves.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of costToEvolves in body.
     */
    @GetMapping("/cost-to-evolves")
    public ResponseEntity<List<CostToEvolve>> getAllCostToEvolves(Pageable pageable) {
        log.debug("REST request to get a page of CostToEvolves");
        Page<CostToEvolve> page = costToEvolveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cost-to-evolves/:id} : get the "id" costToEvolve.
     *
     * @param id the id of the costToEvolve to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the costToEvolve, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cost-to-evolves/{id}")
    public ResponseEntity<CostToEvolve> getCostToEvolve(@PathVariable Long id) {
        log.debug("REST request to get CostToEvolve : {}", id);
        Optional<CostToEvolve> costToEvolve = costToEvolveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(costToEvolve);
    }

    /**
     * {@code DELETE  /cost-to-evolves/:id} : delete the "id" costToEvolve.
     *
     * @param id the id of the costToEvolve to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cost-to-evolves/{id}")
    public ResponseEntity<Void> deleteCostToEvolve(@PathVariable Long id) {
        log.debug("REST request to delete CostToEvolve : {}", id);
        costToEvolveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
