package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseSessionProgress;
import com.mycompany.myapp.service.dto.CourseSessionProgressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseSessionProgress} and its DTO {@link CourseSessionProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CourseSessionMapper.class })
public interface CourseSessionProgressMapper extends EntityMapper<CourseSessionProgressDTO, CourseSessionProgress> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "courseSession", source = "courseSession", qualifiedByName = "id")
    CourseSessionProgressDTO toDto(CourseSessionProgress s);
}
