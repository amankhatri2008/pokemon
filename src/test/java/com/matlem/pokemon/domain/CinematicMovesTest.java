package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class CinematicMovesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CinematicMoves.class);
        CinematicMoves cinematicMoves1 = new CinematicMoves();
        cinematicMoves1.setId(1L);
        CinematicMoves cinematicMoves2 = new CinematicMoves();
        cinematicMoves2.setId(cinematicMoves1.getId());
        assertThat(cinematicMoves1).isEqualTo(cinematicMoves2);
        cinematicMoves2.setId(2L);
        assertThat(cinematicMoves1).isNotEqualTo(cinematicMoves2);
        cinematicMoves1.setId(null);
        assertThat(cinematicMoves1).isNotEqualTo(cinematicMoves2);
    }
}
