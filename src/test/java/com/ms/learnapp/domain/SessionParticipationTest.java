package com.ms.learnapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class SessionParticipationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionParticipation.class);
        SessionParticipation sessionParticipation1 = new SessionParticipation();
        sessionParticipation1.setId("id1");
        SessionParticipation sessionParticipation2 = new SessionParticipation();
        sessionParticipation2.setId(sessionParticipation1.getId());
        assertThat(sessionParticipation1).isEqualTo(sessionParticipation2);
        sessionParticipation2.setId("id2");
        assertThat(sessionParticipation1).isNotEqualTo(sessionParticipation2);
        sessionParticipation1.setId(null);
        assertThat(sessionParticipation1).isNotEqualTo(sessionParticipation2);
    }
}
