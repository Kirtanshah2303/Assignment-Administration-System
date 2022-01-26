package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseTypeMapperTest {

    private CourseTypeMapper courseTypeMapper;

    @BeforeEach
    public void setUp() {
        courseTypeMapper = new CourseTypeMapperImpl();
    }
}
