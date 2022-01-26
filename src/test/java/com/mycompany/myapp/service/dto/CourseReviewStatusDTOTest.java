package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseReviewStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseReviewStatusDTO.class);
        CourseReviewStatusDTO courseReviewStatusDTO1 = new CourseReviewStatusDTO();
        courseReviewStatusDTO1.setId(1L);
        CourseReviewStatusDTO courseReviewStatusDTO2 = new CourseReviewStatusDTO();
        assertThat(courseReviewStatusDTO1).isNotEqualTo(courseReviewStatusDTO2);
        courseReviewStatusDTO2.setId(courseReviewStatusDTO1.getId());
        assertThat(courseReviewStatusDTO1).isEqualTo(courseReviewStatusDTO2);
        courseReviewStatusDTO2.setId(2L);
        assertThat(courseReviewStatusDTO1).isNotEqualTo(courseReviewStatusDTO2);
        courseReviewStatusDTO1.setId(null);
        assertThat(courseReviewStatusDTO1).isNotEqualTo(courseReviewStatusDTO2);
    }
}
