package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.Encounter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Encounter}.
 */
public interface EncounterService {

    /**
     * Save a encounter.
     *
     * @param encounter the entity to save.
     * @return the persisted entity.
     */
    Encounter save(Encounter encounter);

    /**
     * Get all the encounters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Encounter> findAll(Pageable pageable);


    /**
     * Get the "id" encounter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Encounter> findOne(Long id);

    /**
     * Delete the "id" encounter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
