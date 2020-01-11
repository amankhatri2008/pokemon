package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.User;
import com.matlem.pokemon.domain.UserPokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing {@link UserPokemon}.
 */
public interface UserPokemonService {

    /**
     * Save a userPokemon.
     *
     * @param userPokemon the entity to save.
     * @return the persisted entity.
     */
    UserPokemon save(UserPokemon userPokemon);

    /**
     * Get all the userPokemons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserPokemon> findAll(Pageable pageable, Set<User> user);

    /**
     * Get all the userPokemons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UserPokemon> findAllWithEagerRelationships(Pageable pageable, Set<User> user);

    /**
     * Get the "id" userPokemon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserPokemon> findOne(Long id);

    /**
     * Delete the "id" userPokemon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
