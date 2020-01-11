package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Branchs;
import org.springframework.data.jpa.repository.*;

import org.springframework.stereotype.Repository;



/**
 * Spring Data  repository for the Branchs entity.
 * Spring Data  repository for the Branchs entity.
 */

@Repository
public interface BranchsRepository extends JpaRepository<Branchs, Long> {

}
