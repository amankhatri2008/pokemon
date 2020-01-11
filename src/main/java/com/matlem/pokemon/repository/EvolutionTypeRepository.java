package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.EvolutionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EvolutionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvolutionTypeRepository extends JpaRepository<EvolutionType, Long> {

}
