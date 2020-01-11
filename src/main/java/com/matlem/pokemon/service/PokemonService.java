package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.Pokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Pokemon}.
 */
public interface PokemonService {

    /**
     * Save a pokemon.
     *
     * @param pokemon the entity to save.
     * @return the persisted entity.
     */
    Pokemon save(Pokemon pokemon);

    /**
     * Get all the pokemons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pokemon> findAll(Pageable pageable);

    /**
     * Get all the pokemons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Pokemon> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" pokemon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pokemon> findOne(Long id);

    /**
     * Delete the "id" pokemon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
