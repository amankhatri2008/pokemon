package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class UserPokemonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPokemon.class);
        UserPokemon userPokemon1 = new UserPokemon();
        userPokemon1.setId(1L);
        UserPokemon userPokemon2 = new UserPokemon();
        userPokemon2.setId(userPokemon1.getId());
        assertThat(userPokemon1).isEqualTo(userPokemon2);
        userPokemon2.setId(2L);
        assertThat(userPokemon1).isNotEqualTo(userPokemon2);
        userPokemon1.setId(null);
        assertThat(userPokemon1).isNotEqualTo(userPokemon2);
    }
}
