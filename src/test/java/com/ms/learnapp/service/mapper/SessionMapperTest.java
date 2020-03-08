package com.ms.learnapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SessionMapperTest {

    private SessionMapper sessionMapper;

    @BeforeEach
    public void setUp() {
        sessionMapper = new SessionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sessionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sessionMapper.fromId(null)).isNull();
    }
}
