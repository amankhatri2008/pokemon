package com.matlem.pokemon.service.impl;

import com.matlem.pokemon.service.CinematicMovesService;
import com.matlem.pokemon.domain.CinematicMoves;
import com.matlem.pokemon.repository.CinematicMovesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CinematicMoves}.
 */
@Service
@Transactional
public class CinematicMovesServiceImpl implements CinematicMovesService {

    private final Logger log = LoggerFactory.getLogger(CinematicMovesServiceImpl.class);

    private final CinematicMovesRepository cinematicMovesRepository;

    public CinematicMovesServiceImpl(CinematicMovesRepository cinematicMovesRepository) {
        this.cinematicMovesRepository = cinematicMovesRepository;
    }

    /**
     * Save a cinematicMoves.
     *
     * @param cinematicMoves the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CinematicMoves save(CinematicMoves cinematicMoves) {
        log.debug("Request to save CinematicMoves : {}", cinematicMoves);
        return cinematicMovesRepository.save(cinematicMoves);
    }

    /**
     * Get all the cinematicMoves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CinematicMoves> findAll(Pageable pageable) {
        log.debug("Request to get all CinematicMoves");
        return cinematicMovesRepository.findAll(pageable);
    }


    /**
     * Get one cinematicMoves by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CinematicMoves> findOne(Long id) {
        log.debug("Request to get CinematicMoves : {}", id);
        return cinematicMovesRepository.findById(id);
    }

    /**
     * Delete the cinematicMoves by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CinematicMoves : {}", id);
        cinematicMovesRepository.deleteById(id);
    }
}
