package com.ms.learnapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class CourseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseDTO.class);
        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setId("id1");
        CourseDTO courseDTO2 = new CourseDTO();
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO2.setId(courseDTO1.getId());
        assertThat(courseDTO1).isEqualTo(courseDTO2);
        courseDTO2.setId("id2");
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO1.setId(null);
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
    }
}
