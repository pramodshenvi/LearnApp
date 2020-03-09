package com.ms.learnapp.service;

import com.ms.learnapp.domain.Session;
import com.ms.learnapp.repository.SessionRepository;
import com.ms.learnapp.service.dto.SessionDTO;
import com.ms.learnapp.service.mapper.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Session}.
 */
@Service
public class SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    /**
     * Save a session.
     *
     * @param sessionDTO the entity to save.
     * @return the persisted entity.
     */
    public SessionDTO save(SessionDTO sessionDTO) {
        log.debug("Request to save Session : {}", sessionDTO);
        Session session = sessionMapper.toEntity(sessionDTO);
        session = sessionRepository.save(session);
        return sessionMapper.toDto(session);
    }

    /**
     * Get all the sessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SessionDTO> findAll(Pageable pageable, SessionDTO s) {
        log.debug("Request to get all Sessions");
        if(s != null){
            ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
            return sessionRepository.findAll(Example.of(sessionMapper.toEntity(s),m), pageable)
            .map(sessionMapper::toDto);
        }
        return sessionRepository.findAll(pageable)
            .map(sessionMapper::toDto);
    }

    /**
     * Get one session by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SessionDTO> findOne(String id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id)
            .map(sessionMapper::toDto);
    }

    /**
     * Delete the session by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Session : {}", id);
        sessionRepository.deleteById(id);
    }
}
