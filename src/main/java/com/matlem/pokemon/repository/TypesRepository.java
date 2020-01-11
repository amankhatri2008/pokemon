package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Types;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Types entity.
 */

@Repository
public interface TypesRepository extends JpaRepository<Types, Long> {

}
