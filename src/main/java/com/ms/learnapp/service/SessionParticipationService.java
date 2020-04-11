package com.ms.learnapp.service;

import com.ms.learnapp.domain.SessionParticipation;
import com.ms.learnapp.domain.enumeration.PointsFor;
import com.ms.learnapp.repository.SessionParticipationRepository;
import com.ms.learnapp.service.dto.CourseDTO;
import com.ms.learnapp.service.dto.SessionDTO;
import com.ms.learnapp.service.dto.SessionParticipationDTO;
import com.ms.learnapp.service.dto.UserPointsDTO;
import com.ms.learnapp.service.mapper.SessionParticipationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SessionParticipation}.
 */
@Service
public class SessionParticipationService {

    private final Logger log = LoggerFactory.getLogger(SessionParticipationService.class);

    private final SessionParticipationRepository sessionParticipationRepository;

    private final SessionParticipationMapper sessionParticipationMapper;

    private final CourseService courseService;

    private final SessionService sessionService;

    private final UserPointsService userPointsService;

    public SessionParticipationService(SessionParticipationRepository sessionParticipationRepository, SessionParticipationMapper sessionParticipationMapper, CourseService courseService, SessionService sessionService, UserPointsService userPointsService) {
        this.sessionParticipationRepository = sessionParticipationRepository;
        this.sessionParticipationMapper = sessionParticipationMapper;
        this.courseService = courseService;
        this.sessionService = sessionService;
        this.userPointsService = userPointsService;
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
        List<SessionParticipationDTO> matchingSessions = findMatchingSessionParticipationsForUser(sessionParticipationDTO);
        if(matchingSessions != null && matchingSessions.size() > 0) {
            sessionParticipation.setId(matchingSessions.get(0).getId());
            if(matchingSessions.get(0).getRegistrationDateTime() != null)
                sessionParticipation.setRegistrationDateTime(matchingSessions.get(0).getRegistrationDateTime());
            if(matchingSessions.get(0).getAttendanceDateTime() != null)
                sessionParticipation.setAttendanceDateTime(matchingSessions.get(0).getAttendanceDateTime());
        }
        // Add gamification logic
        if(sessionParticipationDTO.getAttendanceDateTime() != null &&
            (matchingSessions == null || matchingSessions.size() == 0 || matchingSessions.get(0).getAttendanceDateTime() == null)) {
            UserPointsUpdate up = new UserPointsUpdate();
            up.setCourseId(sessionParticipationDTO.getCourseId());
            up.setSessionId(sessionParticipationDTO.getSessionId());
            up.setUserId(sessionParticipationDTO.getUserId());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(up);
        }
        sessionParticipation = sessionParticipationRepository.save(sessionParticipation);
        return sessionParticipationMapper.toDto(sessionParticipation);
    }

    /**
     * Get all the sessionParticipations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SessionParticipationDTO> findAll(SessionParticipationDTO dto, Pageable pageable) {
        log.debug("Request to get all SessionParticipations");
        if(dto != null){
            ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
            return sessionParticipationRepository.findAll(Example.of(sessionParticipationMapper.toEntity(dto),m), pageable)
            .map(sessionParticipationMapper::toDto);
        }
        return sessionParticipationRepository.findAll(pageable)
            .map(sessionParticipationMapper::toDto);
    }

    /**
     * Get all the matching sessionParticipations for a given user.
     *
     * @param sessionParticipationDTO dto containing user id and comma delimited session id list.
     * @return the list of entities.
     */
    public List<SessionParticipationDTO> findMatchingSessionParticipationsForUser(SessionParticipationDTO dto) {
        log.debug("Request to get all SessionParticipations");
        List<String> sessionIds = Arrays.asList(dto.getSessionId().split(","));
        String userId = dto.getUserId();
        return sessionParticipationRepository.findSessionParticipationByUserIdAndSessionIdContaining(userId, sessionIds)
        .stream()
        .map(sessionParticipationMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
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

    private class UserPointsUpdate implements Runnable {

        private String courseId;
        private String sessionId;
        private String userId;
        
		@Override
		public void run() {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(courseId);
            List<CourseDTO> courseList = courseService.findAll(courseDTO, StringMatcher.EXACT);
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setId(sessionId);
            List<SessionDTO> sessionList = sessionService.findAll(sessionDTO, StringMatcher.EXACT);
            if(courseList != null && courseList.size() > 0 && sessionList != null && sessionList.size() > 0){
                UserPointsDTO userPointsDTO = new UserPointsDTO();
                userPointsDTO.setUserId(userId);
                userPointsDTO.setSessionTopic(sessionList.get(0).getTopic());
                userPointsDTO.setSessionId(sessionId);
                userPointsDTO.setSessionDateTime(sessionList.get(0).getSessionDateTime());
                userPointsDTO.setPointsFor(PointsFor.PARTICIPANT);
                userPointsDTO.setPoints(courseList.get(0).getParticipantPoints());

                userPointsService.save(userPointsDTO);
            }
		}

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
