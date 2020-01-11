package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.BuddySize;
import com.matlem.pokemon.repository.BuddySizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BuddySize}.
 */
@Service
@Transactional
public class BuddySizeService {

    private final Logger log = LoggerFactory.getLogger(BuddySizeService.class);

    private final BuddySizeRepository buddySizeRepository;

    public BuddySizeService(BuddySizeRepository buddySizeRepository) {
        this.buddySizeRepository = buddySizeRepository;
    }

    /**
     * Save a buddySize.
     *
     * @param buddySize the entity to save.
     * @return the persisted entity.
     */

    public BuddySize save(BuddySize buddySize) {
        log.debug("Request to save BuddySize : {}", buddySize);
        return buddySizeRepository.save(buddySize);
    }

    /**
     * Get all the buddySizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<BuddySize> findAll(Pageable pageable) {
        log.debug("Request to get all BuddySizes");
        return buddySizeRepository.findAll(pageable);
    }


    /**
     * Get one buddySize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<BuddySize> findOne(Long id) {
        log.debug("Request to get BuddySize : {}", id);
        return buddySizeRepository.findById(id);
    }

    /**
     * Delete the buddySize by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete BuddySize : {}", id);
        buddySizeRepository.deleteById(id);
    }
}
