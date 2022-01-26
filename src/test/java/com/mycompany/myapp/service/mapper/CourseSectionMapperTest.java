package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseSectionMapperTest {

    private CourseSectionMapper courseSectionMapper;

    @BeforeEach
    public void setUp() {
        courseSectionMapper = new CourseSectionMapperImpl();
    }
}
