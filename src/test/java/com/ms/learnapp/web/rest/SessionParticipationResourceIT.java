package com.ms.learnapp.web.rest;

import com.ms.learnapp.LearnApp;
import com.ms.learnapp.domain.SessionParticipation;
import com.ms.learnapp.repository.SessionParticipationRepository;
import com.ms.learnapp.service.SessionParticipationService;
import com.ms.learnapp.service.dto.SessionParticipationDTO;
import com.ms.learnapp.service.mapper.SessionParticipationMapper;
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

/**
 * Integration tests for the {@link SessionParticipationResource} REST controller.
 */
@SpringBootTest(classes = LearnApp.class)
public class SessionParticipationResourceIT {

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_USER_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REGISTRATION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ATTENDANCE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATTENDANCE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private SessionParticipationRepository sessionParticipationRepository;

    @Autowired
    private SessionParticipationMapper sessionParticipationMapper;

    @Autowired
    private SessionParticipationService sessionParticipationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSessionParticipationMockMvc;

    private SessionParticipation sessionParticipation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionParticipationResource sessionParticipationResource = new SessionParticipationResource(sessionParticipationService);
        this.restSessionParticipationMockMvc = MockMvcBuilders.standaloneSetup(sessionParticipationResource)
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
    public static SessionParticipation createEntity() {
        SessionParticipation sessionParticipation = new SessionParticipation()
            .sessionId(DEFAULT_SESSION_ID)
            .courseId(DEFAULT_COURSE_ID)
            .userName(DEFAULT_USER_NAME)
            .userEmail(DEFAULT_USER_EMAIL)
            .registrationDateTime(DEFAULT_REGISTRATION_DATE_TIME)
            .attendanceDateTime(DEFAULT_ATTENDANCE_DATE_TIME)
            .userId(DEFAULT_USER_ID);
        return sessionParticipation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionParticipation createUpdatedEntity() {
        SessionParticipation sessionParticipation = new SessionParticipation()
            .sessionId(UPDATED_SESSION_ID)
            .courseId(UPDATED_COURSE_ID)
            .userName(UPDATED_USER_NAME)
            .userEmail(UPDATED_USER_EMAIL)
            .registrationDateTime(UPDATED_REGISTRATION_DATE_TIME)
            .attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME)
            .userId(UPDATED_USER_ID);
        return sessionParticipation;
    }

    @BeforeEach
    public void initTest() {
        sessionParticipationRepository.deleteAll();
        sessionParticipation = createEntity();
    }

    @Test
    public void createSessionParticipation() throws Exception {
        int databaseSizeBeforeCreate = sessionParticipationRepository.findAll().size();

        // Create the SessionParticipation
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);
        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isCreated());

        // Validate the SessionParticipation in the database
        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeCreate + 1);
        SessionParticipation testSessionParticipation = sessionParticipationList.get(sessionParticipationList.size() - 1);
        assertThat(testSessionParticipation.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testSessionParticipation.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testSessionParticipation.getUserEmail()).isEqualTo(DEFAULT_USER_EMAIL);
        assertThat(testSessionParticipation.getRegistrationDateTime()).isEqualTo(DEFAULT_REGISTRATION_DATE_TIME);
        assertThat(testSessionParticipation.getAttendanceDateTime()).isEqualTo(DEFAULT_ATTENDANCE_DATE_TIME);
        assertThat(testSessionParticipation.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    public void createSessionParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionParticipationRepository.findAll().size();

        // Create the SessionParticipation with an existing ID
        sessionParticipation.setId("existing_id");
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SessionParticipation in the database
        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionParticipationRepository.findAll().size();
        // set the field null
        sessionParticipation.setSessionId(null);

        // Create the SessionParticipation, which fails.
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionParticipationRepository.findAll().size();
        // set the field null
        sessionParticipation.setUserName(null);

        // Create the SessionParticipation, which fails.
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionParticipationRepository.findAll().size();
        // set the field null
        sessionParticipation.setUserEmail(null);

        // Create the SessionParticipation, which fails.
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionParticipationRepository.findAll().size();
        // set the field null
        sessionParticipation.setUserId(null);

        // Create the SessionParticipation, which fails.
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        restSessionParticipationMockMvc.perform(post("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSessionParticipations() throws Exception {
        // Initialize the database
        sessionParticipationRepository.save(sessionParticipation);

        // Get all the sessionParticipationList
        restSessionParticipationMockMvc.perform(get("/api/session-participations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionParticipation.getId())))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].courseId").value(hasItem(DEFAULT_COURSE_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userEmail").value(hasItem(DEFAULT_USER_EMAIL)))
            .andExpect(jsonPath("$.[*].registrationDateTime").value(hasItem(sameInstant(DEFAULT_REGISTRATION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].attendanceDateTime").value(hasItem(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }
    
    @Test
    public void getSessionParticipation() throws Exception {
        // Initialize the database
        sessionParticipationRepository.save(sessionParticipation);

        // Get the sessionParticipation
        restSessionParticipationMockMvc.perform(get("/api/session-participations/{id}", sessionParticipation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionParticipation.getId()))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.courseId").value(DEFAULT_COURSE_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userEmail").value(DEFAULT_USER_EMAIL))
            .andExpect(jsonPath("$.registrationDateTime").value(sameInstant(DEFAULT_REGISTRATION_DATE_TIME)))
            .andExpect(jsonPath("$.attendanceDateTime").value(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME)))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    public void getNonExistingSessionParticipation() throws Exception {
        // Get the sessionParticipation
        restSessionParticipationMockMvc.perform(get("/api/session-participations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSessionParticipation() throws Exception {
        // Initialize the database
        sessionParticipationRepository.save(sessionParticipation);

        int databaseSizeBeforeUpdate = sessionParticipationRepository.findAll().size();

        // Update the sessionParticipation
        SessionParticipation updatedSessionParticipation = sessionParticipationRepository.findById(sessionParticipation.getId()).get();
        updatedSessionParticipation
            .sessionId(UPDATED_SESSION_ID)
            .courseId(UPDATED_COURSE_ID)
            .userName(UPDATED_USER_NAME)
            .userEmail(UPDATED_USER_EMAIL)
            .registrationDateTime(UPDATED_REGISTRATION_DATE_TIME)
            .attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME)
            .userId(UPDATED_USER_ID);
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(updatedSessionParticipation);

        restSessionParticipationMockMvc.perform(put("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isOk());

        // Validate the SessionParticipation in the database
        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeUpdate);
        SessionParticipation testSessionParticipation = sessionParticipationList.get(sessionParticipationList.size() - 1);
        assertThat(testSessionParticipation.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testSessionParticipation.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testSessionParticipation.getUserEmail()).isEqualTo(UPDATED_USER_EMAIL);
        assertThat(testSessionParticipation.getRegistrationDateTime()).isEqualTo(UPDATED_REGISTRATION_DATE_TIME);
        assertThat(testSessionParticipation.getAttendanceDateTime()).isEqualTo(UPDATED_ATTENDANCE_DATE_TIME);
        assertThat(testSessionParticipation.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    public void updateNonExistingSessionParticipation() throws Exception {
        int databaseSizeBeforeUpdate = sessionParticipationRepository.findAll().size();

        // Create the SessionParticipation
        SessionParticipationDTO sessionParticipationDTO = sessionParticipationMapper.toDto(sessionParticipation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionParticipationMockMvc.perform(put("/api/session-participations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sessionParticipationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SessionParticipation in the database
        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSessionParticipation() throws Exception {
        // Initialize the database
        sessionParticipationRepository.save(sessionParticipation);

        int databaseSizeBeforeDelete = sessionParticipationRepository.findAll().size();

        // Delete the sessionParticipation
        restSessionParticipationMockMvc.perform(delete("/api/session-participations/{id}", sessionParticipation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SessionParticipation> sessionParticipationList = sessionParticipationRepository.findAll();
        assertThat(sessionParticipationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
