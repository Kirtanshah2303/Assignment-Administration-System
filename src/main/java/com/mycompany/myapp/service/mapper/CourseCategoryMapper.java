package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseCategory;
import com.mycompany.myapp.service.dto.CourseCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseCategory} and its DTO {@link CourseCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseCategoryMapper extends EntityMapper<CourseCategoryDTO, CourseCategory> {
    @Named("courseCategoryTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "courseCategoryTitle", source = "courseCategoryTitle")
    CourseCategoryDTO toDtoCourseCategoryTitle(CourseCategory courseCategory);
}
