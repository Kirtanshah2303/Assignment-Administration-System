package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentInputDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentInputDTO.class);
        CourseAssignmentInputDTO courseAssignmentInputDTO1 = new CourseAssignmentInputDTO();
        courseAssignmentInputDTO1.setId(1L);
        CourseAssignmentInputDTO courseAssignmentInputDTO2 = new CourseAssignmentInputDTO();
        assertThat(courseAssignmentInputDTO1).isNotEqualTo(courseAssignmentInputDTO2);
        courseAssignmentInputDTO2.setId(courseAssignmentInputDTO1.getId());
        assertThat(courseAssignmentInputDTO1).isEqualTo(courseAssignmentInputDTO2);
        courseAssignmentInputDTO2.setId(2L);
        assertThat(courseAssignmentInputDTO1).isNotEqualTo(courseAssignmentInputDTO2);
        courseAssignmentInputDTO1.setId(null);
        assertThat(courseAssignmentInputDTO1).isNotEqualTo(courseAssignmentInputDTO2);
    }
}
