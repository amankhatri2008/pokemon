package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.BuddySize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BuddySize entity.
 */

@Repository
public interface BuddySizeRepository extends JpaRepository<BuddySize, Long> {

}
