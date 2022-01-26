package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseSessionProgressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSessionProgress.class);
        CourseSessionProgress courseSessionProgress1 = new CourseSessionProgress();
        courseSessionProgress1.setId(1L);
        CourseSessionProgress courseSessionProgress2 = new CourseSessionProgress();
        courseSessionProgress2.setId(courseSessionProgress1.getId());
        assertThat(courseSessionProgress1).isEqualTo(courseSessionProgress2);
        courseSessionProgress2.setId(2L);
        assertThat(courseSessionProgress1).isNotEqualTo(courseSessionProgress2);
        courseSessionProgress1.setId(null);
        assertThat(courseSessionProgress1).isNotEqualTo(courseSessionProgress2);
    }
}
