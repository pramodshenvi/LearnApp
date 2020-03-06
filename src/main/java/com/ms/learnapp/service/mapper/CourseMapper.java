package com.ms.learnapp.service.mapper;


import com.ms.learnapp.domain.*;
import com.ms.learnapp.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {



    default Course fromId(String id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
