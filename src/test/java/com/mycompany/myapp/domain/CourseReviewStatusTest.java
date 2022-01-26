package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseReviewStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseReviewStatus.class);
        CourseReviewStatus courseReviewStatus1 = new CourseReviewStatus();
        courseReviewStatus1.setId(1L);
        CourseReviewStatus courseReviewStatus2 = new CourseReviewStatus();
        courseReviewStatus2.setId(courseReviewStatus1.getId());
        assertThat(courseReviewStatus1).isEqualTo(courseReviewStatus2);
        courseReviewStatus2.setId(2L);
        assertThat(courseReviewStatus1).isNotEqualTo(courseReviewStatus2);
        courseReviewStatus1.setId(null);
        assertThat(courseReviewStatus1).isNotEqualTo(courseReviewStatus2);
    }
}
