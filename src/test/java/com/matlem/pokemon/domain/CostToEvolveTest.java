package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class CostToEvolveTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostToEvolve.class);
        CostToEvolve costToEvolve1 = new CostToEvolve();
        costToEvolve1.setId(1L);
        CostToEvolve costToEvolve2 = new CostToEvolve();
        costToEvolve2.setId(costToEvolve1.getId());
        assertThat(costToEvolve1).isEqualTo(costToEvolve2);
        costToEvolve2.setId(2L);
        assertThat(costToEvolve1).isNotEqualTo(costToEvolve2);
        costToEvolve1.setId(null);
        assertThat(costToEvolve1).isNotEqualTo(costToEvolve2);
    }
}
