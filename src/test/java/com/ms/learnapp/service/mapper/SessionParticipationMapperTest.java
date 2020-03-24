package com.ms.learnapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SessionParticipationMapperTest {

    private SessionParticipationMapper sessionParticipationMapper;

    @BeforeEach
    public void setUp() {
        sessionParticipationMapper = new SessionParticipationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sessionParticipationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sessionParticipationMapper.fromId(null)).isNull();
    }
}
