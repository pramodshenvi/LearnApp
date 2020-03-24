package com.ms.learnapp.service;

import com.ms.learnapp.domain.SessionParticipation;
import com.ms.learnapp.repository.SessionParticipationRepository;
import com.ms.learnapp.service.dto.SessionParticipationDTO;
import com.ms.learnapp.service.mapper.SessionParticipationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SessionParticipation}.
 */
@Service
public class SessionParticipationService {

    private final Logger log = LoggerFactory.getLogger(SessionParticipationService.class);

    private final SessionParticipationRepository sessionParticipationRepository;

    private final SessionParticipationMapper sessionParticipationMapper;

    public SessionParticipationService(SessionParticipationRepository sessionParticipationRepository, SessionParticipationMapper sessionParticipationMapper) {
        this.sessionParticipationRepository = sessionParticipationRepository;
        this.sessionParticipationMapper = sessionParticipationMapper;
    }

    /**
     * Save a sessionParticipation.
     *
     * @param sessionParticipationDTO the entity to save.
     * @return the persisted entity.
     */
    public SessionParticipationDTO save(SessionParticipationDTO sessionParticipationDTO) {
        log.debug("Request to save SessionParticipation : {}", sessionParticipationDTO);
        SessionParticipation sessionParticipation = sessionParticipationMapper.toEntity(sessionParticipationDTO);
        sessionParticipation = sessionParticipationRepository.save(sessionParticipation);
        return sessionParticipationMapper.toDto(sessionParticipation);
    }

    /**
     * Get all the sessionParticipations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SessionParticipationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SessionParticipations");
        return sessionParticipationRepository.findAll(pageable)
            .map(sessionParticipationMapper::toDto);
    }

    /**
     * Get one sessionParticipation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SessionParticipationDTO> findOne(String id) {
        log.debug("Request to get SessionParticipation : {}", id);
        return sessionParticipationRepository.findById(id)
            .map(sessionParticipationMapper::toDto);
    }

    /**
     * Delete the sessionParticipation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SessionParticipation : {}", id);
        sessionParticipationRepository.deleteById(id);
    }
}
