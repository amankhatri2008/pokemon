package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Encounter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Encounter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

}
