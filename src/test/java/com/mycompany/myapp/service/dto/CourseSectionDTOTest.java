package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseSectionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSectionDTO.class);
        CourseSectionDTO courseSectionDTO1 = new CourseSectionDTO();
        courseSectionDTO1.setId(1L);
        CourseSectionDTO courseSectionDTO2 = new CourseSectionDTO();
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
        courseSectionDTO2.setId(courseSectionDTO1.getId());
        assertThat(courseSectionDTO1).isEqualTo(courseSectionDTO2);
        courseSectionDTO2.setId(2L);
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
        courseSectionDTO1.setId(null);
        assertThat(courseSectionDTO1).isNotEqualTo(courseSectionDTO2);
    }
}
