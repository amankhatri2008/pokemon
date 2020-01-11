package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.Family;
import com.matlem.pokemon.repository.FamilyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Family}.
 */
@Service
@Transactional
public class FamilyService {

    private final Logger log = LoggerFactory.getLogger(FamilyService.class);

    private final FamilyRepository familyRepository;

    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    /**
     * Save a family.
     *
     * @param family the entity to save.
     * @return the persisted entity.
     */

    public Family save(Family family) {
        log.debug("Request to save Family : {}", family);
        return familyRepository.save(family);
    }

    /**
     * Get all the families.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<Family> findAll(Pageable pageable) {
        log.debug("Request to get all Families");
        return familyRepository.findAll(pageable);
    }


    /**
     * Get one family by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<Family> findOne(Long id) {
        log.debug("Request to get Family : {}", id);
        return familyRepository.findById(id);
    }

    /**
     * Delete the family by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete Family : {}", id);
        familyRepository.deleteById(id);
    }
}
