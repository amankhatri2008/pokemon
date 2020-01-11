package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class EncounterTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncounterType.class);
        EncounterType encounterType1 = new EncounterType();
        encounterType1.setId(1L);
        EncounterType encounterType2 = new EncounterType();
        encounterType2.setId(encounterType1.getId());
        assertThat(encounterType1).isEqualTo(encounterType2);
        encounterType2.setId(2L);
        assertThat(encounterType1).isNotEqualTo(encounterType2);
        encounterType1.setId(null);
        assertThat(encounterType1).isNotEqualTo(encounterType2);
    }
}
