package com.matlem.pokemon.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserPokemon.
 */
@Entity
@Table(name = "user_pokemon")
public class UserPokemon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "user_pokemon_user_many",
               joinColumns = @JoinColumn(name = "user_pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_many_id", referencedColumnName = "id"))
    private Set<User> userManies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_pokemon_pokemon_many",
               joinColumns = @JoinColumn(name = "user_pokemon_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pokemon_many_id", referencedColumnName = "id"))
    private Set<Pokemon> pokemonManies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUserManies() {
        return userManies;
    }

    public UserPokemon userManies(Set<User> users) {
        this.userManies = users;
        return this;
    }

    public UserPokemon addUserMany(User user) {
        this.userManies.add(user);
        return this;
    }

    public UserPokemon removeUserMany(User user) {
        this.userManies.remove(user);
        return this;
    }

    public void setUserManies(Set<User> users) {
        this.userManies = users;
    }

    public Set<Pokemon> getPokemonManies() {
        return pokemonManies;
    }

    public UserPokemon pokemonManies(Set<Pokemon> pokemons) {
        this.pokemonManies = pokemons;
        return this;
    }

    public UserPokemon addPokemonMany(Pokemon pokemon) {
        this.pokemonManies.add(pokemon);

        return this;
    }

    public UserPokemon removePokemonMany(Pokemon pokemon) {
        this.pokemonManies.remove(pokemon);

        return this;
    }

    public void setPokemonManies(Set<Pokemon> pokemons) {
        this.pokemonManies = pokemons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPokemon)) {
            return false;
        }
        return id != null && id.equals(((UserPokemon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserPokemon{" +
            "id=" + getId() +
            "}";
    }
}
