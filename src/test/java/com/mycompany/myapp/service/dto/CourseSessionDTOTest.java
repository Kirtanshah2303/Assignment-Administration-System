package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseSessionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSessionDTO.class);
        CourseSessionDTO courseSessionDTO1 = new CourseSessionDTO();
        courseSessionDTO1.setId(1L);
        CourseSessionDTO courseSessionDTO2 = new CourseSessionDTO();
        assertThat(courseSessionDTO1).isNotEqualTo(courseSessionDTO2);
        courseSessionDTO2.setId(courseSessionDTO1.getId());
        assertThat(courseSessionDTO1).isEqualTo(courseSessionDTO2);
        courseSessionDTO2.setId(2L);
        assertThat(courseSessionDTO1).isNotEqualTo(courseSessionDTO2);
        courseSessionDTO1.setId(null);
        assertThat(courseSessionDTO1).isNotEqualTo(courseSessionDTO2);
    }
}
