package com.ms.learnapp.service;

import com.ms.learnapp.domain.Session;
import com.ms.learnapp.domain.enumeration.PointsFor;
import com.ms.learnapp.repository.SessionRepository;
import com.ms.learnapp.service.dto.CourseDTO;
import com.ms.learnapp.service.dto.SessionDTO;
import com.ms.learnapp.service.dto.UserPointsDTO;
import com.ms.learnapp.service.mapper.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Session}.
 */
@Service
public class SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    private final CourseService courseService;

    private final UserPointsService userPointsService;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, CourseService courseService, UserPointsService userPointsService) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.courseService = courseService;
        this.userPointsService = userPointsService;
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

        // Gamification Logic
        if(sessionDTO.getSessionComplete() && sessionDTO.getId() != null){
            UserPointsUpdate up = new UserPointsUpdate();
            up.setSession(sessionDTO);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(up);
        }
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

    public List<SessionDTO> findAll(SessionDTO sessionDTO, StringMatcher sm) {
        log.debug("Request to get all Sessions");
        ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(sm);
        return sessionRepository.findAll(Example.of(sessionMapper.toEntity(sessionDTO),m))
        .stream()
        .map(sessionMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
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

    private class UserPointsUpdate implements Runnable {

        private SessionDTO sessionDTO;
        
		@Override
		public void run() {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(sessionDTO.getCourseId());
            List<CourseDTO> courseList = courseService.findAll(courseDTO, StringMatcher.EXACT);
            if(courseList != null && courseList.size() > 0 && sessionDTO != null){
                List<String> userList = sessionDTO.getAssignedSMEs();

                if(userList != null && userList.size() > 0) {
                    for(int i=0; i<userList.size(); i++) {
                        String userId = userList.get(i);
                        if(userId.contains("|")){
                            UserPointsDTO userPointsDTO = new UserPointsDTO();
                            userPointsDTO.setUserId(userList.get(i).split("\\|")[1].trim());
                            userPointsDTO.setSessionTopic(sessionDTO.getTopic());
                            userPointsDTO.setSessionId(sessionDTO.getId());
                            userPointsDTO.setSessionDateTime(sessionDTO.getSessionDateTime());
                            userPointsDTO.setPointsFor(PointsFor.HOST);
                            userPointsDTO.setPoints(courseList.get(0).getSmePoints());

                            userPointsService.save(userPointsDTO);
                        }
                    }
                }
            }
		}

        public void setSession(SessionDTO sessionDTO) {
            this.sessionDTO = sessionDTO;
        }
    }
}
