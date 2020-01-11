package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.EvolutionType;
import com.matlem.pokemon.repository.EvolutionTypeRepository;
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
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.EvolutionType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EvolutionTypeResource {

    private final Logger log = LoggerFactory.getLogger(EvolutionTypeResource.class);

    private static final String ENTITY_NAME = "evolutionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvolutionTypeRepository evolutionTypeRepository;

    public EvolutionTypeResource(EvolutionTypeRepository evolutionTypeRepository) {
        this.evolutionTypeRepository = evolutionTypeRepository;
    }

    /**
     * {@code POST  /evolution-types} : Create a new evolutionType.
     *
     * @param evolutionType the evolutionType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evolutionType, or with status {@code 400 (Bad Request)} if the evolutionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evolution-types")
    public ResponseEntity<EvolutionType> createEvolutionType(@Valid @RequestBody EvolutionType evolutionType) throws URISyntaxException {
        log.debug("REST request to save EvolutionType : {}", evolutionType);
        if (evolutionType.getId() != null) {
            throw new BadRequestAlertException("A new evolutionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvolutionType result = evolutionTypeRepository.save(evolutionType);
        return ResponseEntity.created(new URI("/api/evolution-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evolution-types} : Updates an existing evolutionType.
     *
     * @param evolutionType the evolutionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evolutionType,
     * or with status {@code 400 (Bad Request)} if the evolutionType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evolutionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evolution-types")
    public ResponseEntity<EvolutionType> updateEvolutionType(@Valid @RequestBody EvolutionType evolutionType) throws URISyntaxException {
        log.debug("REST request to update EvolutionType : {}", evolutionType);
        if (evolutionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvolutionType result = evolutionTypeRepository.save(evolutionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evolutionType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evolution-types} : get all the evolutionTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evolutionTypes in body.
     */
    @GetMapping("/evolution-types")
    public ResponseEntity<List<EvolutionType>> getAllEvolutionTypes(Pageable pageable) {
        log.debug("REST request to get a page of EvolutionTypes");
        Page<EvolutionType> page = evolutionTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evolution-types/:id} : get the "id" evolutionType.
     *
     * @param id the id of the evolutionType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evolutionType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evolution-types/{id}")
    public ResponseEntity<EvolutionType> getEvolutionType(@PathVariable Long id) {
        log.debug("REST request to get EvolutionType : {}", id);
        Optional<EvolutionType> evolutionType = evolutionTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(evolutionType);
    }

    /**
     * {@code DELETE  /evolution-types/:id} : delete the "id" evolutionType.
     *
     * @param id the id of the evolutionType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evolution-types/{id}")
    public ResponseEntity<Void> deleteEvolutionType(@PathVariable Long id) {
        log.debug("REST request to delete EvolutionType : {}", id);
        evolutionTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
