package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseAssignmentOutputMapperTest {

    private CourseAssignmentOutputMapper courseAssignmentOutputMapper;

    @BeforeEach
    public void setUp() {
        courseAssignmentOutputMapper = new CourseAssignmentOutputMapperImpl();
    }
}
