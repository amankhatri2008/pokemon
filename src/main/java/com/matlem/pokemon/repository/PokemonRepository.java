package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Pokemon entity.
 */
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    @Query(value = "select distinct pokemon from Pokemon pokemon left join fetch pokemon.cinematicMovesManies left join fetch pokemon.quickMovesManies left join fetch pokemon.formManies left join fetch pokemon.userManies",
        countQuery = "select count(distinct pokemon) from Pokemon pokemon")
    Page<Pokemon> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pokemon from Pokemon pokemon left join fetch pokemon.cinematicMovesManies left join fetch pokemon.quickMovesManies left join fetch pokemon.formManies left join fetch pokemon.userManies")
    List<Pokemon> findAllWithEagerRelationships();

    @Query("select pokemon from Pokemon pokemon left join fetch pokemon.cinematicMovesManies left join fetch pokemon.quickMovesManies left join fetch pokemon.formManies left join fetch pokemon.userManies where pokemon.id =:id")
    Optional<Pokemon> findOneWithEagerRelationships(@Param("id") Long id);

}
