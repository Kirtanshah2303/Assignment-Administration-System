package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentInputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentInput.class);
        CourseAssignmentInput courseAssignmentInput1 = new CourseAssignmentInput();
        courseAssignmentInput1.setId(1L);
        CourseAssignmentInput courseAssignmentInput2 = new CourseAssignmentInput();
        courseAssignmentInput2.setId(courseAssignmentInput1.getId());
        assertThat(courseAssignmentInput1).isEqualTo(courseAssignmentInput2);
        courseAssignmentInput2.setId(2L);
        assertThat(courseAssignmentInput1).isNotEqualTo(courseAssignmentInput2);
        courseAssignmentInput1.setId(null);
        assertThat(courseAssignmentInput1).isNotEqualTo(courseAssignmentInput2);
    }
}
