package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseEnrollment;
import com.mycompany.myapp.service.dto.CourseEnrollmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseEnrollment} and its DTO {@link CourseEnrollmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CourseMapper.class })
public interface CourseEnrollmentMapper extends EntityMapper<CourseEnrollmentDTO, CourseEnrollment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "course", source = "course", qualifiedByName = "id")
    CourseEnrollmentDTO toDto(CourseEnrollment s);
}
