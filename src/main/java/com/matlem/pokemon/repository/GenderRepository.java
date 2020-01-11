package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Gender;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gender entity.
 */

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

}
