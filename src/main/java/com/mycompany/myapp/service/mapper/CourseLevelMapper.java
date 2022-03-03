package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseLevel;
import com.mycompany.myapp.service.dto.CourseLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseLevel} and its DTO {@link CourseLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseLevelMapper extends EntityMapper<CourseLevelDTO, CourseLevel> {
    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    CourseLevelDTO toDtoTitle(CourseLevel courseLevel);
}
