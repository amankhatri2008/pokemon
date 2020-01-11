package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.User;
import com.matlem.pokemon.domain.UserPokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the UserPokemon entity.
 */
@Repository
public interface UserPokemonRepository extends JpaRepository<UserPokemon, Long> {

    Page<UserPokemon> findByUserManies(Pageable pageable,Set<User> user);

    @Query("select distinct userPokemon from UserPokemon userPokemon left join fetch userPokemon.userManies left join fetch userPokemon.pokemonManies")
    List<UserPokemon> findAllWithEagerRelationships();

    @Query("select userPokemon from UserPokemon userPokemon left join fetch userPokemon.userManies left join fetch userPokemon.pokemonManies where userPokemon.id =:id")
    Optional<UserPokemon> findOneWithEagerRelationships(@Param("id") Long id);

}
