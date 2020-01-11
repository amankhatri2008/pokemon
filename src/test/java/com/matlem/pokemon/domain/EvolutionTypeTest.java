package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class EvolutionTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvolutionType.class);
        EvolutionType evolutionType1 = new EvolutionType();
        evolutionType1.setId(1L);
        EvolutionType evolutionType2 = new EvolutionType();
        evolutionType2.setId(evolutionType1.getId());
        assertThat(evolutionType1).isEqualTo(evolutionType2);
        evolutionType2.setId(2L);
        assertThat(evolutionType1).isNotEqualTo(evolutionType2);
        evolutionType1.setId(null);
        assertThat(evolutionType1).isNotEqualTo(evolutionType2);
    }
}
