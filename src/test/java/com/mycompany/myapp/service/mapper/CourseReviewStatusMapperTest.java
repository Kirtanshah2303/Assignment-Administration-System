package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseReviewStatusMapperTest {

    private CourseReviewStatusMapper courseReviewStatusMapper;

    @BeforeEach
    public void setUp() {
        courseReviewStatusMapper = new CourseReviewStatusMapperImpl();
    }
}
