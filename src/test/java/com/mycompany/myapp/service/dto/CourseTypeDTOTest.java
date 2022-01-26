package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseTypeDTO.class);
        CourseTypeDTO courseTypeDTO1 = new CourseTypeDTO();
        courseTypeDTO1.setId(1L);
        CourseTypeDTO courseTypeDTO2 = new CourseTypeDTO();
        assertThat(courseTypeDTO1).isNotEqualTo(courseTypeDTO2);
        courseTypeDTO2.setId(courseTypeDTO1.getId());
        assertThat(courseTypeDTO1).isEqualTo(courseTypeDTO2);
        courseTypeDTO2.setId(2L);
        assertThat(courseTypeDTO1).isNotEqualTo(courseTypeDTO2);
        courseTypeDTO1.setId(null);
        assertThat(courseTypeDTO1).isNotEqualTo(courseTypeDTO2);
    }
}
