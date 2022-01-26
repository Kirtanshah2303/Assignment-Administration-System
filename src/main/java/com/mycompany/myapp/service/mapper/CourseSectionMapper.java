package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseSection;
import com.mycompany.myapp.service.dto.CourseSectionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseSection} and its DTO {@link CourseSectionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseMapper.class })
public interface CourseSectionMapper extends EntityMapper<CourseSectionDTO, CourseSection> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseTitle")
    CourseSectionDTO toDto(CourseSection s);

    @Named("sectionTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sectionTitle", source = "sectionTitle")
    CourseSectionDTO toDtoSectionTitle(CourseSection courseSection);
}
