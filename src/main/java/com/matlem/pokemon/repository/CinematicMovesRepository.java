package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.CinematicMoves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CinematicMoves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CinematicMovesRepository extends JpaRepository<CinematicMoves, Long> {

}
