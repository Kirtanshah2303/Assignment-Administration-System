package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseCategoryDTO.class);
        CourseCategoryDTO courseCategoryDTO1 = new CourseCategoryDTO();
        courseCategoryDTO1.setId(1L);
        CourseCategoryDTO courseCategoryDTO2 = new CourseCategoryDTO();
        assertThat(courseCategoryDTO1).isNotEqualTo(courseCategoryDTO2);
        courseCategoryDTO2.setId(courseCategoryDTO1.getId());
        assertThat(courseCategoryDTO1).isEqualTo(courseCategoryDTO2);
        courseCategoryDTO2.setId(2L);
        assertThat(courseCategoryDTO1).isNotEqualTo(courseCategoryDTO2);
        courseCategoryDTO1.setId(null);
        assertThat(courseCategoryDTO1).isNotEqualTo(courseCategoryDTO2);
    }
}
