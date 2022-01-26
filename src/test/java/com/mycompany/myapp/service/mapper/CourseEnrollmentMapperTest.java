package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseEnrollmentMapperTest {

    private CourseEnrollmentMapper courseEnrollmentMapper;

    @BeforeEach
    public void setUp() {
        courseEnrollmentMapper = new CourseEnrollmentMapperImpl();
    }
}
