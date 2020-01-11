package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Family;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Family entity.
 */

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

}
