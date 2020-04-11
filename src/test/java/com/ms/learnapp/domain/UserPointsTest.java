package com.ms.learnapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class UserPointsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPoints.class);
        UserPoints userPoints1 = new UserPoints();
        userPoints1.setId("id1");
        UserPoints userPoints2 = new UserPoints();
        userPoints2.setId(userPoints1.getId());
        assertThat(userPoints1).isEqualTo(userPoints2);
        userPoints2.setId("id2");
        assertThat(userPoints1).isNotEqualTo(userPoints2);
        userPoints1.setId(null);
        assertThat(userPoints1).isNotEqualTo(userPoints2);
    }
}
