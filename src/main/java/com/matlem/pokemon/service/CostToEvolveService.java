package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.CostToEvolve;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CostToEvolve}.
 */
public interface CostToEvolveService {

    /**
     * Save a costToEvolve.
     *
     * @param costToEvolve the entity to save.
     * @return the persisted entity.
     */
    CostToEvolve save(CostToEvolve costToEvolve);

    /**
     * Get all the costToEvolves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CostToEvolve> findAll(Pageable pageable);


    /**
     * Get the "id" costToEvolve.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CostToEvolve> findOne(Long id);

    /**
     * Delete the "id" costToEvolve.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
