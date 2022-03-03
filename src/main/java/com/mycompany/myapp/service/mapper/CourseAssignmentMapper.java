package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseAssignment} and its DTO {@link CourseAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseSessionMapper.class })
public interface CourseAssignmentMapper extends EntityMapper<CourseAssignmentDTO, CourseAssignment> {
    @Mapping(target = "courseSession", source = "courseSession", qualifiedByName = "sessionTitle")
    CourseAssignmentDTO toDto(CourseAssignment s);

    @Named("assignmentTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assignmentTitle", source = "assignmentTitle")
    CourseAssignmentDTO toDtoAssignmentTitle(CourseAssignment courseAssignment);
}
