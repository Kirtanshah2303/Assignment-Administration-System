package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentProgressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentProgressDTO.class);
        CourseAssignmentProgressDTO courseAssignmentProgressDTO1 = new CourseAssignmentProgressDTO();
        courseAssignmentProgressDTO1.setId(1L);
        CourseAssignmentProgressDTO courseAssignmentProgressDTO2 = new CourseAssignmentProgressDTO();
        assertThat(courseAssignmentProgressDTO1).isNotEqualTo(courseAssignmentProgressDTO2);
        courseAssignmentProgressDTO2.setId(courseAssignmentProgressDTO1.getId());
        assertThat(courseAssignmentProgressDTO1).isEqualTo(courseAssignmentProgressDTO2);
        courseAssignmentProgressDTO2.setId(2L);
        assertThat(courseAssignmentProgressDTO1).isNotEqualTo(courseAssignmentProgressDTO2);
        courseAssignmentProgressDTO1.setId(null);
        assertThat(courseAssignmentProgressDTO1).isNotEqualTo(courseAssignmentProgressDTO2);
    }
}
