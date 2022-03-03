package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseAssignmentProgress;
import com.mycompany.myapp.service.dto.CourseAssignmentProgressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseAssignmentProgress} and its DTO {@link CourseAssignmentProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CourseAssignmentMapper.class })
public interface CourseAssignmentProgressMapper extends EntityMapper<CourseAssignmentProgressDTO, CourseAssignmentProgress> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "courseAssignment", source = "courseAssignment", qualifiedByName = "assignmentTitle")
    CourseAssignmentProgressDTO toDto(CourseAssignmentProgress s);
}
