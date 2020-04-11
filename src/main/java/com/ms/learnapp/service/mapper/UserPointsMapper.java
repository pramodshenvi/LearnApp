package com.ms.learnapp.service.mapper;


import com.ms.learnapp.domain.*;
import com.ms.learnapp.service.dto.UserPointsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPoints} and its DTO {@link UserPointsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserPointsMapper extends EntityMapper<UserPointsDTO, UserPoints> {



    default UserPoints fromId(String id) {
        if (id == null) {
            return null;
        }
        UserPoints userPoints = new UserPoints();
        userPoints.setId(id);
        return userPoints;
    }
}
