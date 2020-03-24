package com.ms.learnapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class SessionParticipationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionParticipationDTO.class);
        SessionParticipationDTO sessionParticipationDTO1 = new SessionParticipationDTO();
        sessionParticipationDTO1.setId("id1");
        SessionParticipationDTO sessionParticipationDTO2 = new SessionParticipationDTO();
        assertThat(sessionParticipationDTO1).isNotEqualTo(sessionParticipationDTO2);
        sessionParticipationDTO2.setId(sessionParticipationDTO1.getId());
        assertThat(sessionParticipationDTO1).isEqualTo(sessionParticipationDTO2);
        sessionParticipationDTO2.setId("id2");
        assertThat(sessionParticipationDTO1).isNotEqualTo(sessionParticipationDTO2);
        sessionParticipationDTO1.setId(null);
        assertThat(sessionParticipationDTO1).isNotEqualTo(sessionParticipationDTO2);
    }
}
