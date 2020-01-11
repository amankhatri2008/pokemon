package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class EncounterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Encounter.class);
        Encounter encounter1 = new Encounter();
        encounter1.setId(1L);
        Encounter encounter2 = new Encounter();
        encounter2.setId(encounter1.getId());
        assertThat(encounter1).isEqualTo(encounter2);
        encounter2.setId(2L);
        assertThat(encounter1).isNotEqualTo(encounter2);
        encounter1.setId(null);
        assertThat(encounter1).isNotEqualTo(encounter2);
    }
}
