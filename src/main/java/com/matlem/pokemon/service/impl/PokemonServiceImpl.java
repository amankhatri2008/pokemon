package com.matlem.pokemon.service.impl;

import com.matlem.pokemon.service.PokemonService;
import com.matlem.pokemon.domain.Pokemon;
import com.matlem.pokemon.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pokemon}.
 */
@Service
@Transactional
public class PokemonServiceImpl implements PokemonService {

    private final Logger log = LoggerFactory.getLogger(PokemonServiceImpl.class);

    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Save a pokemon.
     *
     * @param pokemon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pokemon save(Pokemon pokemon) {
        log.debug("Request to save Pokemon : {}", pokemon);
        return pokemonRepository.save(pokemon);
    }

    /**
     * Get all the pokemons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pokemon> findAll(Pageable pageable) {
        log.debug("Request to get all Pokemons");
        return pokemonRepository.findAll(pageable);
    }

    /**
     * Get all the pokemons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Pokemon> findAllWithEagerRelationships(Pageable pageable) {
        return pokemonRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one pokemon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pokemon> findOne(Long id) {
        log.debug("Request to get Pokemon : {}", id);
        return pokemonRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the pokemon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pokemon : {}", id);
        pokemonRepository.deleteById(id);
    }
}
