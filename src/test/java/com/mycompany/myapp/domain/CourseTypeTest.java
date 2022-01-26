package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseType.class);
        CourseType courseType1 = new CourseType();
        courseType1.setId(1L);
        CourseType courseType2 = new CourseType();
        courseType2.setId(courseType1.getId());
        assertThat(courseType1).isEqualTo(courseType2);
        courseType2.setId(2L);
        assertThat(courseType1).isNotEqualTo(courseType2);
        courseType1.setId(null);
        assertThat(courseType1).isNotEqualTo(courseType2);
    }
}
