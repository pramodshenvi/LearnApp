package com.ms.learnapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPointsMapperTest {

    private UserPointsMapper userPointsMapper;

    @BeforeEach
    public void setUp() {
        userPointsMapper = new UserPointsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(userPointsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userPointsMapper.fromId(null)).isNull();
    }
}
