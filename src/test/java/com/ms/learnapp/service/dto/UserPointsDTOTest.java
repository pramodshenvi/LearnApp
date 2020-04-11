package com.ms.learnapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class UserPointsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPointsDTO.class);
        UserPointsDTO userPointsDTO1 = new UserPointsDTO();
        userPointsDTO1.setId("id1");
        UserPointsDTO userPointsDTO2 = new UserPointsDTO();
        assertThat(userPointsDTO1).isNotEqualTo(userPointsDTO2);
        userPointsDTO2.setId(userPointsDTO1.getId());
        assertThat(userPointsDTO1).isEqualTo(userPointsDTO2);
        userPointsDTO2.setId("id2");
        assertThat(userPointsDTO1).isNotEqualTo(userPointsDTO2);
        userPointsDTO1.setId(null);
        assertThat(userPointsDTO1).isNotEqualTo(userPointsDTO2);
    }
}
