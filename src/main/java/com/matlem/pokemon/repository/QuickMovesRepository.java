package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.QuickMoves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuickMoves entity.
 */

@Repository
public interface QuickMovesRepository extends JpaRepository<QuickMoves, Long> {

}
