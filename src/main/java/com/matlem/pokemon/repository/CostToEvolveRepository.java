package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.CostToEvolve;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CostToEvolve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostToEvolveRepository extends JpaRepository<CostToEvolve, Long> {

}
