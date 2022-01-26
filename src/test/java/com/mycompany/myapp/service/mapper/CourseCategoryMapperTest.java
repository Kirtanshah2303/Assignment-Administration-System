package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseCategoryMapperTest {

    private CourseCategoryMapper courseCategoryMapper;

    @BeforeEach
    public void setUp() {
        courseCategoryMapper = new CourseCategoryMapperImpl();
    }
}
