package com.ms.learnapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ms.learnapp.web.rest.TestUtil;

public class CourseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = new Course();
        course1.setId("id1");
        Course course2 = new Course();
        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);
        course2.setId("id2");
        assertThat(course1).isNotEqualTo(course2);
        course1.setId(null);
        assertThat(course1).isNotEqualTo(course2);
    }
}
