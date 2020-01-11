package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.domain.User;
import com.matlem.pokemon.domain.UserPokemon;
import com.matlem.pokemon.security.SecurityUtils;
import com.matlem.pokemon.service.UserPokemonService;
import com.matlem.pokemon.service.UserService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.matlem.pokemon.domain.UserPokemon}.
 */
@RestController
@RequestMapping("/api")
public class UserPokemonResource {

    private final Logger log = LoggerFactory.getLogger(UserPokemonResource.class);

    private static final String ENTITY_NAME = "userPokemon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final UserService userService;
    private final UserPokemonService userPokemonService;

    public UserPokemonResource(UserPokemonService userPokemonService,UserService userService) {
        this.userPokemonService = userPokemonService;
        this.userService= userService;
    }

    /**
     * {@code POST  /user-pokemons} : Create a new userPokemon.
     *
     * @param userPokemon the userPokemon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPokemon, or with status {@code 400 (Bad Request)} if the userPokemon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-pokemons")
    public ResponseEntity<UserPokemon> createUserPokemon(@RequestBody UserPokemon userPokemon) throws URISyntaxException {
        log.debug("REST request to save UserPokemon : {}", userPokemon);
        if (userPokemon.getId() != null) {
            throw new BadRequestAlertException("A new userPokemon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user=userService.getUserWithAuthorities().orElse(null);
        userPokemon.addUserMany(user);
        UserPokemon result = userPokemonService.save(userPokemon);
        return ResponseEntity.created(new URI("/api/user-pokemons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-pokemons} : Updates an existing userPokemon.
     *
     * @param userPokemon the userPokemon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPokemon,
     * or with status {@code 400 (Bad Request)} if the userPokemon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPokemon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-pokemons")
    public ResponseEntity<UserPokemon> updateUserPokemon(@RequestBody UserPokemon userPokemon) throws URISyntaxException {
        log.debug("REST request to update UserPokemon : {}", userPokemon);
        User user=userService.getUserWithAuthorities().orElse(null);
        userPokemon.addUserMany(user);
        UserPokemon result = userPokemonService.save(userPokemon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPokemon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-pokemons} : get all the userPokemons.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPokemons in body.
     */
    @GetMapping("/user-pokemons")
    public ResponseEntity<List<UserPokemon>> getAllUserPokemons(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of UserPokemons");
        Page<UserPokemon> page;

        User user=userService.getUserWithAuthorities().orElse(null);
        Set<User> userSet= new HashSet<>();
        userSet.add(user);
        if (eagerload) {
            page = userPokemonService.findAllWithEagerRelationships(pageable,userSet);
        } else {
            page = userPokemonService.findAll(pageable,userSet);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-pokemons/:id} : get the "id" userPokemon.
     *
     * @param id the id of the userPokemon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPokemon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-pokemons/{id}")
    public ResponseEntity<UserPokemon> getUserPokemon(@PathVariable Long id) {
        log.debug("REST request to get UserPokemon : {}", id);
        Optional<UserPokemon> userPokemon = userPokemonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPokemon);
    }

    /**
     * {@code DELETE  /user-pokemons/:id} : delete the "id" userPokemon.
     *
     * @param id the id of the userPokemon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-pokemons/{id}")
    public ResponseEntity<Void> deleteUserPokemon(@PathVariable Long id) {
        log.debug("REST request to delete UserPokemon : {}", id);
        userPokemonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
