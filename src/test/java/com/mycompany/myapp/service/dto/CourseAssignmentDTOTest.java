package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentDTO.class);
        CourseAssignmentDTO courseAssignmentDTO1 = new CourseAssignmentDTO();
        courseAssignmentDTO1.setId(1L);
        CourseAssignmentDTO courseAssignmentDTO2 = new CourseAssignmentDTO();
        assertThat(courseAssignmentDTO1).isNotEqualTo(courseAssignmentDTO2);
        courseAssignmentDTO2.setId(courseAssignmentDTO1.getId());
        assertThat(courseAssignmentDTO1).isEqualTo(courseAssignmentDTO2);
        courseAssignmentDTO2.setId(2L);
        assertThat(courseAssignmentDTO1).isNotEqualTo(courseAssignmentDTO2);
        courseAssignmentDTO1.setId(null);
        assertThat(courseAssignmentDTO1).isNotEqualTo(courseAssignmentDTO2);
    }
}
