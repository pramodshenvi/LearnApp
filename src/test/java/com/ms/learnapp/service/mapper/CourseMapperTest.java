package com.ms.learnapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperTest {

    private CourseMapper courseMapper;

    @BeforeEach
    public void setUp() {
        courseMapper = new CourseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(courseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(courseMapper.fromId(null)).isNull();
    }
}
