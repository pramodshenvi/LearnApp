package com.ms.learnapp.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ms.learnapp.domain.Session;
import com.ms.learnapp.domain.SessionParticipation;
import com.ms.learnapp.repository.SessionParticipationRepository;
import com.ms.learnapp.repository.SessionRepository;
import com.ms.learnapp.service.dto.SessionDTO;
import com.ms.learnapp.service.mapper.SessionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link UserProfile}.
 */
@Service
public class UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private final SessionParticipationRepository sessionParticipationRepository;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public UserProfileService(SessionParticipationRepository sessionParticipationRepository,
            SessionRepository sessionRepository,
            SessionMapper sessionMapper) {
        this.sessionParticipationRepository = sessionParticipationRepository;
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    public List<SessionDTO> getAllSessionsAttendedByUser(String userId) {

        return null;
    }

    public List<SessionDTO> getAllSessionsRegisteredByUser(String userId) {
        List<SessionDTO> registeredSessions = null;

        List<SessionParticipation> registeredSessionIds = sessionParticipationRepository.findSessionParticipationByUserId(userId);
        
        // TODO Filter out already attended sessions

        if (registeredSessionIds != null && !registeredSessionIds.isEmpty()) {
            Iterable<Session> sessions = sessionRepository.findAllById(registeredSessionIds.stream()
                                                .map(SessionParticipation::getSessionId)
                                                .collect(Collectors.toList()));

            // Filter past sessions

            registeredSessions = StreamSupport.stream(sessions.spliterator(), true)
                                                .map(sessionMapper::toDto)
                                                .collect(Collectors.toList());
        }
        return registeredSessions;
    }

}
