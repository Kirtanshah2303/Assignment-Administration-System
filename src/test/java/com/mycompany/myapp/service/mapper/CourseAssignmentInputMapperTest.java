package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseAssignmentInputMapperTest {

    private CourseAssignmentInputMapper courseAssignmentInputMapper;

    @BeforeEach
    public void setUp() {
        courseAssignmentInputMapper = new CourseAssignmentInputMapperImpl();
    }
}
