package com.matlem.pokemon.service.impl;

import com.matlem.pokemon.service.CostToEvolveService;
import com.matlem.pokemon.domain.CostToEvolve;
import com.matlem.pokemon.repository.CostToEvolveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CostToEvolve}.
 */
@Service
@Transactional
public class CostToEvolveServiceImpl implements CostToEvolveService {

    private final Logger log = LoggerFactory.getLogger(CostToEvolveServiceImpl.class);

    private final CostToEvolveRepository costToEvolveRepository;

    public CostToEvolveServiceImpl(CostToEvolveRepository costToEvolveRepository) {
        this.costToEvolveRepository = costToEvolveRepository;
    }

    /**
     * Save a costToEvolve.
     *
     * @param costToEvolve the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CostToEvolve save(CostToEvolve costToEvolve) {
        log.debug("Request to save CostToEvolve : {}", costToEvolve);
        return costToEvolveRepository.save(costToEvolve);
    }

    /**
     * Get all the costToEvolves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostToEvolve> findAll(Pageable pageable) {
        log.debug("Request to get all CostToEvolves");
        return costToEvolveRepository.findAll(pageable);
    }


    /**
     * Get one costToEvolve by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CostToEvolve> findOne(Long id) {
        log.debug("Request to get CostToEvolve : {}", id);
        return costToEvolveRepository.findById(id);
    }

    /**
     * Delete the costToEvolve by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CostToEvolve : {}", id);
        costToEvolveRepository.deleteById(id);
    }
}
