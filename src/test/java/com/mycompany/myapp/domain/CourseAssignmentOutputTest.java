package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentOutputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentOutput.class);
        CourseAssignmentOutput courseAssignmentOutput1 = new CourseAssignmentOutput();
        courseAssignmentOutput1.setId(1L);
        CourseAssignmentOutput courseAssignmentOutput2 = new CourseAssignmentOutput();
        courseAssignmentOutput2.setId(courseAssignmentOutput1.getId());
        assertThat(courseAssignmentOutput1).isEqualTo(courseAssignmentOutput2);
        courseAssignmentOutput2.setId(2L);
        assertThat(courseAssignmentOutput1).isNotEqualTo(courseAssignmentOutput2);
        courseAssignmentOutput1.setId(null);
        assertThat(courseAssignmentOutput1).isNotEqualTo(courseAssignmentOutput2);
    }
}
