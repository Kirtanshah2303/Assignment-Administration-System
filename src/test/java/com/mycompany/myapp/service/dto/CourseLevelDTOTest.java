package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseLevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseLevelDTO.class);
        CourseLevelDTO courseLevelDTO1 = new CourseLevelDTO();
        courseLevelDTO1.setId(1L);
        CourseLevelDTO courseLevelDTO2 = new CourseLevelDTO();
        assertThat(courseLevelDTO1).isNotEqualTo(courseLevelDTO2);
        courseLevelDTO2.setId(courseLevelDTO1.getId());
        assertThat(courseLevelDTO1).isEqualTo(courseLevelDTO2);
        courseLevelDTO2.setId(2L);
        assertThat(courseLevelDTO1).isNotEqualTo(courseLevelDTO2);
        courseLevelDTO1.setId(null);
        assertThat(courseLevelDTO1).isNotEqualTo(courseLevelDTO2);
    }
}
