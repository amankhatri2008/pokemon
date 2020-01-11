package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.CinematicMoves;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CinematicMoves}.
 */
public interface CinematicMovesService {

    /**
     * Save a cinematicMoves.
     *
     * @param cinematicMoves the entity to save.
     * @return the persisted entity.
     */
    CinematicMoves save(CinematicMoves cinematicMoves);

    /**
     * Get all the cinematicMoves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CinematicMoves> findAll(Pageable pageable);


    /**
     * Get the "id" cinematicMoves.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CinematicMoves> findOne(Long id);

    /**
     * Delete the "id" cinematicMoves.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
