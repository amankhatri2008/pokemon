package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.Pokemon;
import com.matlem.pokemon.service.PokemonService;
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
 * REST controller for managing {@link com.matlem.pokemon.domain.Pokemon}.
 */
@RestController
@RequestMapping("/api")
public class PokemonResource {

    private final Logger log = LoggerFactory.getLogger(PokemonResource.class);

    private static final String ENTITY_NAME = "pokemon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PokemonService pokemonService;

    public PokemonResource(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /**
     * {@code POST  /pokemons} : Create a new pokemon.
     *
     * @param pokemon the pokemon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pokemon, or with status {@code 400 (Bad Request)} if the pokemon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pokemons")
    public ResponseEntity<Pokemon> createPokemon(@RequestBody Pokemon pokemon) throws URISyntaxException {
        log.debug("REST request to save Pokemon : {}", pokemon);
        if (pokemon.getId() != null) {
            throw new BadRequestAlertException("A new pokemon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pokemon result = pokemonService.save(pokemon);
        return ResponseEntity.created(new URI("/api/pokemons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pokemons} : Updates an existing pokemon.
     *
     * @param pokemon the pokemon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pokemon,
     * or with status {@code 400 (Bad Request)} if the pokemon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pokemon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pokemons")
    public ResponseEntity<Pokemon> updatePokemon(@RequestBody Pokemon pokemon) throws URISyntaxException {
        log.debug("REST request to update Pokemon : {}", pokemon);
        if (pokemon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pokemon result = pokemonService.save(pokemon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pokemon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pokemons} : get all the pokemons.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pokemons in body.
     */
    @GetMapping("/pokemons")
    public ResponseEntity<List<Pokemon>> getAllPokemons(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Pokemons");
        Page<Pokemon> page;
        if (eagerload) {
            page = pokemonService.findAllWithEagerRelationships(pageable);
        } else {
            page = pokemonService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pokemons/:id} : get the "id" pokemon.
     *
     * @param id the id of the pokemon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pokemon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pokemons/{id}")
    public ResponseEntity<Pokemon> getPokemon(@PathVariable Long id) {
        log.debug("REST request to get Pokemon : {}", id);
        Optional<Pokemon> pokemon = pokemonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pokemon);
    }

    /**
     * {@code DELETE  /pokemons/:id} : delete the "id" pokemon.
     *
     * @param id the id of the pokemon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pokemons/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long id) {
        log.debug("REST request to delete Pokemon : {}", id);
        pokemonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
