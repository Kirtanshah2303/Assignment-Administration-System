package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseAssignment;
import com.mycompany.myapp.service.dto.CourseAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseAssignment} and its DTO {@link CourseAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseSectionMapper.class })
public interface CourseAssignmentMapper extends EntityMapper<CourseAssignmentDTO, CourseAssignment> {
    @Mapping(target = "courseSection", source = "courseSection", qualifiedByName = "sectionTitle")
    CourseAssignmentDTO toDto(CourseAssignment s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseAssignmentDTO toDtoId(CourseAssignment courseAssignment);
}
