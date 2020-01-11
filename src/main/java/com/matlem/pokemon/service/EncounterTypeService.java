package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.EncounterType;
import com.matlem.pokemon.repository.EncounterTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EncounterType}.
 */
@Service
@Transactional
public class EncounterTypeService  {

    private final Logger log = LoggerFactory.getLogger(EncounterTypeService.class);

    private final EncounterTypeRepository encounterTypeRepository;

    public EncounterTypeService(EncounterTypeRepository encounterTypeRepository) {
        this.encounterTypeRepository = encounterTypeRepository;
    }

    /**
     * Save a encounterType.
     *
     * @param encounterType the entity to save.
     * @return the persisted entity.
     */

    public EncounterType save(EncounterType encounterType) {
        log.debug("Request to save EncounterType : {}", encounterType);
        return encounterTypeRepository.save(encounterType);
    }

    /**
     * Get all the encounterTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<EncounterType> findAll(Pageable pageable) {
        log.debug("Request to get all EncounterTypes");
        return encounterTypeRepository.findAll(pageable);
    }


    /**
     * Get one encounterType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<EncounterType> findOne(Long id) {
        log.debug("Request to get EncounterType : {}", id);
        return encounterTypeRepository.findById(id);
    }

    /**
     * Delete the encounterType by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete EncounterType : {}", id);
        encounterTypeRepository.deleteById(id);
    }
}
