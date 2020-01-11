package com.matlem.pokemon.service.impl;

import com.matlem.pokemon.service.EncounterService;
import com.matlem.pokemon.domain.Encounter;
import com.matlem.pokemon.repository.EncounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Encounter}.
 */
@Service
@Transactional
public class EncounterServiceImpl implements EncounterService {

    private final Logger log = LoggerFactory.getLogger(EncounterServiceImpl.class);

    private final EncounterRepository encounterRepository;

    public EncounterServiceImpl(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    /**
     * Save a encounter.
     *
     * @param encounter the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Encounter save(Encounter encounter) {
        log.debug("Request to save Encounter : {}", encounter);
        return encounterRepository.save(encounter);
    }

    /**
     * Get all the encounters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Encounter> findAll(Pageable pageable) {
        log.debug("Request to get all Encounters");
        return encounterRepository.findAll(pageable);
    }


    /**
     * Get one encounter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Encounter> findOne(Long id) {
        log.debug("Request to get Encounter : {}", id);
        return encounterRepository.findById(id);
    }

    /**
     * Delete the encounter by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Encounter : {}", id);
        encounterRepository.deleteById(id);
    }
}
