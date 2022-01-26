package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseSessionMapperTest {

    private CourseSessionMapper courseSessionMapper;

    @BeforeEach
    public void setUp() {
        courseSessionMapper = new CourseSessionMapperImpl();
    }
}
