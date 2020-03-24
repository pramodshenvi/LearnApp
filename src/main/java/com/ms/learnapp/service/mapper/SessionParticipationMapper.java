package com.ms.learnapp.service.mapper;


import com.ms.learnapp.domain.*;
import com.ms.learnapp.service.dto.SessionParticipationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SessionParticipation} and its DTO {@link SessionParticipationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SessionParticipationMapper extends EntityMapper<SessionParticipationDTO, SessionParticipation> {



    default SessionParticipation fromId(String id) {
        if (id == null) {
            return null;
        }
        SessionParticipation sessionParticipation = new SessionParticipation();
        sessionParticipation.setId(id);
        return sessionParticipation;
    }
}
