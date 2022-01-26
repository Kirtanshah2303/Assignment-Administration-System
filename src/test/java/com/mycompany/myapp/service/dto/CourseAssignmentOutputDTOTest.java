package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseAssignmentOutputDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseAssignmentOutputDTO.class);
        CourseAssignmentOutputDTO courseAssignmentOutputDTO1 = new CourseAssignmentOutputDTO();
        courseAssignmentOutputDTO1.setId(1L);
        CourseAssignmentOutputDTO courseAssignmentOutputDTO2 = new CourseAssignmentOutputDTO();
        assertThat(courseAssignmentOutputDTO1).isNotEqualTo(courseAssignmentOutputDTO2);
        courseAssignmentOutputDTO2.setId(courseAssignmentOutputDTO1.getId());
        assertThat(courseAssignmentOutputDTO1).isEqualTo(courseAssignmentOutputDTO2);
        courseAssignmentOutputDTO2.setId(2L);
        assertThat(courseAssignmentOutputDTO1).isNotEqualTo(courseAssignmentOutputDTO2);
        courseAssignmentOutputDTO1.setId(null);
        assertThat(courseAssignmentOutputDTO1).isNotEqualTo(courseAssignmentOutputDTO2);
    }
}
