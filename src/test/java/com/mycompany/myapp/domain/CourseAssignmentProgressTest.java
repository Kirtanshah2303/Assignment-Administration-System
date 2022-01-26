package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentProgressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentProgress.class);
        CourseAssignmentProgress courseAssignmentProgress1 = new CourseAssignmentProgress();
        courseAssignmentProgress1.setId(1L);
        CourseAssignmentProgress courseAssignmentProgress2 = new CourseAssignmentProgress();
        courseAssignmentProgress2.setId(courseAssignmentProgress1.getId());
        assertThat(courseAssignmentProgress1).isEqualTo(courseAssignmentProgress2);
        courseAssignmentProgress2.setId(2L);
        assertThat(courseAssignmentProgress1).isNotEqualTo(courseAssignmentProgress2);
        courseAssignmentProgress1.setId(null);
        assertThat(courseAssignmentProgress1).isNotEqualTo(courseAssignmentProgress2);
    }
}
