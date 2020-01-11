package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Form;
import com.matlem.pokemon.service.FormService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.Form}.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormService formService;

    public FormResource(FormService formService) {
        this.formService = formService;
    }

    /**
     * {@code POST  /forms} : Create a new form.
     *
     * @param form the form to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new form, or with status {@code 400 (Bad Request)} if the form has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forms")
    public ResponseEntity<Form> createForm(@Valid @RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to save Form : {}", form);
        if (form.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Form result = formService.save(form);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forms} : Updates an existing form.
     *
     * @param form the form to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated form,
     * or with status {@code 400 (Bad Request)} if the form is not valid,
     * or with status {@code 500 (Internal Server Error)} if the form couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forms")
    public ResponseEntity<Form> updateForm(@Valid @RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to update Form : {}", form);
        if (form.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Form result = formService.save(form);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, form.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forms} : get all the forms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forms in body.
     */
    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms(Pageable pageable) {
        log.debug("REST request to get a page of Forms");
        Page<Form> page = formService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forms/:id} : get the "id" form.
     *
     * @param id the id of the form to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the form, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forms/{id}")
    public ResponseEntity<Form> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Optional<Form> form = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(form);
    }

    /**
     * {@code DELETE  /forms/:id} : delete the "id" form.
     *
     * @param id the id of the form to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
