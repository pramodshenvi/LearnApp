package com.ms.learnapp.service.mapper;


import com.ms.learnapp.domain.*;
import com.ms.learnapp.service.dto.SessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Session} and its DTO {@link SessionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {



    default Session fromId(String id) {
        if (id == null) {
            return null;
        }
        Session session = new Session();
        session.setId(id);
        return session;
    }
}
