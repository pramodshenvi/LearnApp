package com.ms.learnapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class SessionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionDTO.class);
        SessionDTO sessionDTO1 = new SessionDTO();
        sessionDTO1.setId("id1");
        SessionDTO sessionDTO2 = new SessionDTO();
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO2.setId(sessionDTO1.getId());
        assertThat(sessionDTO1).isEqualTo(sessionDTO2);
        sessionDTO2.setId("id2");
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO1.setId(null);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
    }
}
