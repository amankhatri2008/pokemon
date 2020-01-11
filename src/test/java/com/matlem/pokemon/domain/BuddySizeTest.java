package com.matlem.pokemon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.matlem.pokemon.web.rest.TestUtil;

public class BuddySizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuddySize.class);
        BuddySize buddySize1 = new BuddySize();
        buddySize1.setId(1L);
        BuddySize buddySize2 = new BuddySize();
        buddySize2.setId(buddySize1.getId());
        assertThat(buddySize1).isEqualTo(buddySize2);
        buddySize2.setId(2L);
        assertThat(buddySize1).isNotEqualTo(buddySize2);
        buddySize1.setId(null);
        assertThat(buddySize1).isNotEqualTo(buddySize2);
    }
}
