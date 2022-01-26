package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseType;
import com.mycompany.myapp.service.dto.CourseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseType} and its DTO {@link CourseTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseTypeMapper extends EntityMapper<CourseTypeDTO, CourseType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseTypeDTO toDtoId(CourseType courseType);
}
