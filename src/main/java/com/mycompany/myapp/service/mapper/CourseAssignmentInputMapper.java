package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseAssignmentInput;
import com.mycompany.myapp.service.dto.CourseAssignmentInputDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseAssignmentInput} and its DTO {@link CourseAssignmentInputDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseAssignmentMapper.class, UserMapper.class })
public interface CourseAssignmentInputMapper extends EntityMapper<CourseAssignmentInputDTO, CourseAssignmentInput> {
    @Mapping(target = "courseAssignment", source = "courseAssignment", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    CourseAssignmentInputDTO toDto(CourseAssignmentInput s);
}
