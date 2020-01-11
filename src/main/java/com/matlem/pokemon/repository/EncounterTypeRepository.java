package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.EncounterType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EncounterType entity.
 */

@Repository
public interface EncounterTypeRepository extends JpaRepository<EncounterType, Long> {

}
