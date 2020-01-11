package com.matlem.pokemon.service.impl;

import com.matlem.pokemon.domain.User;
import com.matlem.pokemon.service.UserPokemonService;
import com.matlem.pokemon.domain.UserPokemon;
import com.matlem.pokemon.repository.UserPokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link UserPokemon}.
 */
@Service
@Transactional
public class UserPokemonServiceImpl implements UserPokemonService {

    private final Logger log = LoggerFactory.getLogger(UserPokemonServiceImpl.class);

    private final UserPokemonRepository userPokemonRepository;

    public UserPokemonServiceImpl(UserPokemonRepository userPokemonRepository) {
        this.userPokemonRepository = userPokemonRepository;
    }

    /**
     * Save a userPokemon.
     *
     * @param userPokemon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserPokemon save(UserPokemon userPokemon) {
        log.debug("Request to save UserPokemon : {}", userPokemon);
        return userPokemonRepository.save(userPokemon);
    }

    /**
     * Get all the userPokemons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserPokemon> findAll(Pageable pageable, Set<User> user) {
        log.debug("Request to get all UserPokemons");
        return userPokemonRepository.findByUserManies(pageable, user);
    }

    /**
     * Get all the userPokemons with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserPokemon> findAllWithEagerRelationships(Pageable pageable, Set<User> user) {
        return userPokemonRepository.findByUserManies(pageable,user);
    }


    /**
     * Get one userPokemon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserPokemon> findOne(Long id) {
        log.debug("Request to get UserPokemon : {}", id);
        return userPokemonRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the userPokemon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserPokemon : {}", id);
        userPokemonRepository.deleteById(id);
    }
}
