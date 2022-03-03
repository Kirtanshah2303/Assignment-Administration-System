package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourseReviewStatus;
import com.mycompany.myapp.service.dto.CourseReviewStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseReviewStatus} and its DTO {@link CourseReviewStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CourseSessionMapper.class })
public interface CourseReviewStatusMapper extends EntityMapper<CourseReviewStatusDTO, CourseReviewStatus> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "courseSession", source = "courseSession", qualifiedByName = "sessionTitle")
    CourseReviewStatusDTO toDto(CourseReviewStatus s);
}
