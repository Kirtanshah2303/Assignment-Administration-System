package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseAssignmentOutput;
import com.mycompany.myapp.service.dto.CourseAssignmentOutputDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseAssignmentOutput} and its DTO {@link CourseAssignmentOutputDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseAssignmentMapper.class })
public interface CourseAssignmentOutputMapper extends EntityMapper<CourseAssignmentOutputDTO, CourseAssignmentOutput> {
    @Mapping(target = "courseAssignment", source = "courseAssignment", qualifiedByName = "id")
    CourseAssignmentOutputDTO toDto(CourseAssignmentOutput s);
}
