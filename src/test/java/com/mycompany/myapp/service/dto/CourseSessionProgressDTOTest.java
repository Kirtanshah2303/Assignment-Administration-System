package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseSessionProgressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseSessionProgressDTO.class);
        CourseSessionProgressDTO courseSessionProgressDTO1 = new CourseSessionProgressDTO();
        courseSessionProgressDTO1.setId(1L);
        CourseSessionProgressDTO courseSessionProgressDTO2 = new CourseSessionProgressDTO();
        assertThat(courseSessionProgressDTO1).isNotEqualTo(courseSessionProgressDTO2);
        courseSessionProgressDTO2.setId(courseSessionProgressDTO1.getId());
        assertThat(courseSessionProgressDTO1).isEqualTo(courseSessionProgressDTO2);
        courseSessionProgressDTO2.setId(2L);
        assertThat(courseSessionProgressDTO1).isNotEqualTo(courseSessionProgressDTO2);
        courseSessionProgressDTO1.setId(null);
        assertThat(courseSessionProgressDTO1).isNotEqualTo(courseSessionProgressDTO2);
    }
}
