package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.Branchs;
import com.matlem.pokemon.repository.BranchsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Branchs}.
 */
@Service
@Transactional
public class BranchsService {

    private final Logger log = LoggerFactory.getLogger(BranchsService.class);

    private final BranchsRepository branchsRepository;

    public BranchsService(BranchsRepository branchsRepository) {
        this.branchsRepository = branchsRepository;
    }

    /**
     * Save a branchs.
     *
     * @param branchs the entity to save.
     * @return the persisted entity.
     */

    public Branchs save(Branchs branchs) {
        log.debug("Request to save Branchs : {}", branchs);
        return branchsRepository.save(branchs);
    }

    /**
     * Get all the branchs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<Branchs> findAll(Pageable pageable) {
        log.debug("Request to get all Branchs");
        return branchsRepository.findAll(pageable);
    }


    /**
     * Get one branchs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<Branchs> findOne(Long id) {
        log.debug("Request to get Branchs : {}", id);
        return branchsRepository.findById(id);
    }

    /**
     * Delete the branchs by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete Branchs : {}", id);
        branchsRepository.deleteById(id);
    }
}
