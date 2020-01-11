package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class BranchsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Branchs.class);
        Branchs branchs1 = new Branchs();
        branchs1.setId(1L);
        Branchs branchs2 = new Branchs();
        branchs2.setId(branchs1.getId());
        assertThat(branchs1).isEqualTo(branchs2);
        branchs2.setId(2L);
        assertThat(branchs1).isNotEqualTo(branchs2);
        branchs1.setId(null);
        assertThat(branchs1).isNotEqualTo(branchs2);
    }
}
