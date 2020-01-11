package com.matlem.pokemon.service;

import com.matlem.pokemon.domain.Types;
import com.matlem.pokemon.repository.TypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Types}.
 */
@Service
@Transactional
public class TypesService  {

    private final Logger log = LoggerFactory.getLogger(TypesService.class);

    private final TypesRepository typesRepository;


    public TypesService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;

    }

    /**
     * Save a types.
     *
     * @param types the entity to save.
     * @return the persisted entity.
     */

    public Types save(Types types) {
        log.debug("Request to save Types : {}", types);
        Types result = typesRepository.save(types);

        return result;
    }

    /**
     * Get all the types.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    @Transactional(readOnly = true)
    public Page<Types> findAll(Pageable pageable) {
        log.debug("Request to get all Types");
        return typesRepository.findAll(pageable);
    }


    /**
     * Get one types by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<Types> findOne(Long id) {
        log.debug("Request to get Types : {}", id);
        return typesRepository.findById(id);
    }

    /**
     * Delete the types by id.
     *
     * @param id the id of the entity.
     */

    public void delete(Long id) {
        log.debug("Request to delete Types : {}", id);
        typesRepository.deleteById(id);

    }


}
