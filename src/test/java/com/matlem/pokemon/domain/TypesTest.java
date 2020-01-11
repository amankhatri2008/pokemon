package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class TypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Types.class);
        Types types1 = new Types();
        types1.setId(1L);
        Types types2 = new Types();
        types2.setId(types1.getId());
        assertThat(types1).isEqualTo(types2);
        types2.setId(2L);
        assertThat(types1).isNotEqualTo(types2);
        types1.setId(null);
        assertThat(types1).isNotEqualTo(types2);
    }
}
