package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignment.class);
        CourseAssignment courseAssignment1 = new CourseAssignment();
        courseAssignment1.setId(1L);
        CourseAssignment courseAssignment2 = new CourseAssignment();
        courseAssignment2.setId(courseAssignment1.getId());
        assertThat(courseAssignment1).isEqualTo(courseAssignment2);
        courseAssignment2.setId(2L);
        assertThat(courseAssignment1).isNotEqualTo(courseAssignment2);
        courseAssignment1.setId(null);
        assertThat(courseAssignment1).isNotEqualTo(courseAssignment2);
    }
}
