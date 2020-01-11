package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.QuickMoves;
import com.matlem.pokemon.repository.QuickMovesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuickMoves}.
 */
@Service
@Transactional
public class QuickMovesService {

    private final Logger log = LoggerFactory.getLogger(QuickMovesService.class);

    private final QuickMovesRepository quickMovesRepository;

    public QuickMovesService(QuickMovesRepository quickMovesRepository) {
        this.quickMovesRepository = quickMovesRepository;
    }

    /**
     * Save a quickMoves.
     *
     * @param quickMoves the entity to save.
     * @return the persisted entity.
     */

    public QuickMoves save(QuickMoves quickMoves) {
        log.debug("Request to save QuickMoves : {}", quickMoves);
        return quickMovesRepository.save(quickMoves);
    }

    /**
     * Get all the quickMoves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<QuickMoves> findAll(Pageable pageable) {
        log.debug("Request to get all QuickMoves");
        return quickMovesRepository.findAll(pageable);
    }


    /**
     * Get one quickMoves by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<QuickMoves> findOne(Long id) {
        log.debug("Request to get QuickMoves : {}", id);
        return quickMovesRepository.findById(id);
    }

    /**
     * Delete the quickMoves by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete QuickMoves : {}", id);
        quickMovesRepository.deleteById(id);
    }
}
