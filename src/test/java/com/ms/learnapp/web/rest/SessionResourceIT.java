package com.ms.learnapp.web.rest;

import com.ms.learnapp.LearnApp;
import com.ms.learnapp.domain.Session;
import com.ms.learnapp.repository.SessionRepository;
import com.ms.learnapp.service.SessionService;
import com.ms.learnapp.service.dto.SessionDTO;
import com.ms.learnapp.service.mapper.SessionMapper;
import com.ms.learnapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ms.learnapp.web.rest.TestUtil.sameInstant;
import static com.ms.learnapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ms.learnapp.domain.enumeration.SessionLocation;
import com.ms.learnapp.domain.enumeration.AttendanceLocationTypes;
/**
 * Integration tests for the {@link SessionResource} REST controller.
 */
@SpringBootTest(classes = LearnApp.class)
public class SessionResourceIT {

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_AGENDA = "AAAAAAAAAA";
    private static final String UPDATED_AGENDA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SESSION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SESSION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final SessionLocation DEFAULT_LOCATION = SessionLocation.BANGALORE;
    private static final SessionLocation UPDATED_LOCATION = SessionLocation.MUMBAI;

    private static final String DEFAULT_SESSION_PRE_REQUISITES = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_PRE_REQUISITES = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_SM_ES = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_SM_ES = "BBBBBBBBBB";

    private static final AttendanceLocationTypes DEFAULT_ATTENDANCE_LOCATION = AttendanceLocationTypes.CLASSROOM;
    private static final AttendanceLocationTypes UPDATED_ATTENDANCE_LOCATION = AttendanceLocationTypes.REMOTE;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSessionMockMvc;

    private Session session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionResource sessionResource = new SessionResource(sessionService);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createEntity() {
        Session session = new Session()
            .topic(DEFAULT_TOPIC)
            .agenda(DEFAULT_AGENDA)
            .sessionDateTime(DEFAULT_SESSION_DATE_TIME)
            .sessionComplete(false)
            .location(DEFAULT_LOCATION)
            .sessionPreRequisites(DEFAULT_SESSION_PRE_REQUISITES)
            .assignedSMEs(DEFAULT_ASSIGNED_SM_ES)
            .attendanceLocation(DEFAULT_ATTENDANCE_LOCATION);
        return session;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createUpdatedEntity() {
        Session session = new Session()
            .topic(UPDATED_TOPIC)
            .agenda(UPDATED_AGENDA)
            .sessionDateTime(UPDATED_SESSION_DATE_TIME)
            .location(UPDATED_LOCATION)
            .sessionPreRequisites(UPDATED_SESSION_PRE_REQUISITES)
            .assignedSMEs(UPDATED_ASSIGNED_SM_ES)
            .attendanceLocation(UPDATED_ATTENDANCE_LOCATION);
        return session;
    }

    @BeforeEach
    public void initTest() {
        sessionRepository.deleteAll();
        session = createEntity();
    }

    @Test
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testSession.getAgenda()).isEqualTo(DEFAULT_AGENDA);
        assertThat(testSession.getSessionDateTime()).isEqualTo(DEFAULT_SESSION_DATE_TIME);
        assertThat(testSession.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSession.getSessionPreRequisites()).isEqualTo(DEFAULT_SESSION_PRE_REQUISITES);
        assertThat(testSession.getAssignedSMEs()).isEqualTo(DEFAULT_ASSIGNED_SM_ES);
        assertThat(testSession.getAttendanceLocation()).isEqualTo(DEFAULT_ATTENDANCE_LOCATION);
    }

    @Test
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId("existing_id");
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setTopic(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAgendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setAgenda(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setLocation(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.save(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)))
            .andExpect(jsonPath("$.[*].agenda").value(hasItem(DEFAULT_AGENDA)))
            .andExpect(jsonPath("$.[*].sessionDateTime").value(hasItem(sameInstant(DEFAULT_SESSION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].sessionPreRequisites").value(hasItem(DEFAULT_SESSION_PRE_REQUISITES)))
            .andExpect(jsonPath("$.[*].assignedSMEs").value(hasItem(DEFAULT_ASSIGNED_SM_ES)))
            .andExpect(jsonPath("$.[*].attendanceLocation").value(hasItem(DEFAULT_ATTENDANCE_LOCATION.toString())));
    }
    
    @Test
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.save(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC))
            .andExpect(jsonPath("$.agenda").value(DEFAULT_AGENDA))
            .andExpect(jsonPath("$.sessionDateTime").value(sameInstant(DEFAULT_SESSION_DATE_TIME)))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.sessionPreRequisites").value(DEFAULT_SESSION_PRE_REQUISITES))
            .andExpect(jsonPath("$.assignedSMEs").value(DEFAULT_ASSIGNED_SM_ES))
            .andExpect(jsonPath("$.attendanceLocation").value(DEFAULT_ATTENDANCE_LOCATION.toString()));
    }

    @Test
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.save(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        updatedSession
            .topic(UPDATED_TOPIC)
            .agenda(UPDATED_AGENDA)
            .sessionDateTime(UPDATED_SESSION_DATE_TIME)
            .location(UPDATED_LOCATION)
            .sessionPreRequisites(UPDATED_SESSION_PRE_REQUISITES)
            .assignedSMEs(UPDATED_ASSIGNED_SM_ES)
            .attendanceLocation(UPDATED_ATTENDANCE_LOCATION);
        SessionDTO sessionDTO = sessionMapper.toDto(updatedSession);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testSession.getAgenda()).isEqualTo(UPDATED_AGENDA);
        assertThat(testSession.getSessionDateTime()).isEqualTo(UPDATED_SESSION_DATE_TIME);
        assertThat(testSession.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSession.getSessionPreRequisites()).isEqualTo(UPDATED_SESSION_PRE_REQUISITES);
        assertThat(testSession.getAssignedSMEs()).isEqualTo(UPDATED_ASSIGNED_SM_ES);
        assertThat(testSession.getAttendanceLocation()).isEqualTo(UPDATED_ATTENDANCE_LOCATION);
    }

    @Test
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.save(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
