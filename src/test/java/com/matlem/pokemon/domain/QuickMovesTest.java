package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class QuickMovesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuickMoves.class);
        QuickMoves quickMoves1 = new QuickMoves();
        quickMoves1.setId(1L);
        QuickMoves quickMoves2 = new QuickMoves();
        quickMoves2.setId(quickMoves1.getId());
        assertThat(quickMoves1).isEqualTo(quickMoves2);
        quickMoves2.setId(2L);
        assertThat(quickMoves1).isNotEqualTo(quickMoves2);
        quickMoves1.setId(null);
        assertThat(quickMoves1).isNotEqualTo(quickMoves2);
    }
}
