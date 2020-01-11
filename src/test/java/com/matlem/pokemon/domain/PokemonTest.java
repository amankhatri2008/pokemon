package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class PokemonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pokemon.class);
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(1L);
        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(pokemon1.getId());
        assertThat(pokemon1).isEqualTo(pokemon2);
        pokemon2.setId(2L);
        assertThat(pokemon1).isNotEqualTo(pokemon2);
        pokemon1.setId(null);
        assertThat(pokemon1).isNotEqualTo(pokemon2);
    }
}
