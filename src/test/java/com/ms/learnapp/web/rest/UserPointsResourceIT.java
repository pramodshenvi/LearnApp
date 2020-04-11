package com.ms.learnapp.web.rest;

import com.ms.learnapp.LearnApp;
import com.ms.learnapp.domain.UserPoints;
import com.ms.learnapp.repository.UserPointsRepository;
import com.ms.learnapp.service.UserPointsService;
import com.ms.learnapp.service.dto.UserPointsDTO;
import com.ms.learnapp.service.mapper.UserPointsMapper;
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

import com.ms.learnapp.domain.enumeration.PointsFor;
/**
 * Integration tests for the {@link UserPointsResource} REST controller.
 */
@SpringBootTest(classes = LearnApp.class)
public class UserPointsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_TOPIC = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SESSION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SESSION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final PointsFor DEFAULT_POINTS_FOR = PointsFor.HOST;
    private static final PointsFor UPDATED_POINTS_FOR = PointsFor.PARTICIPANT;

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    @Autowired
    private UserPointsRepository userPointsRepository;

    @Autowired
    private UserPointsMapper userPointsMapper;

    @Autowired
    private UserPointsService userPointsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restUserPointsMockMvc;

    private UserPoints userPoints;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPointsResource userPointsResource = new UserPointsResource(userPointsService);
        this.restUserPointsMockMvc = MockMvcBuilders.standaloneSetup(userPointsResource)
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
    public static UserPoints createEntity() {
        UserPoints userPoints = new UserPoints()
            .userId(DEFAULT_USER_ID)
            .sessionId(DEFAULT_SESSION_ID)
            .sessionTopic(DEFAULT_SESSION_TOPIC)
            .sessionDateTime(DEFAULT_SESSION_DATE_TIME)
            .pointsFor(DEFAULT_POINTS_FOR)
            .points(DEFAULT_POINTS);
        return userPoints;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPoints createUpdatedEntity() {
        UserPoints userPoints = new UserPoints()
            .userId(UPDATED_USER_ID)
            .sessionId(UPDATED_SESSION_ID)
            .sessionTopic(UPDATED_SESSION_TOPIC)
            .sessionDateTime(UPDATED_SESSION_DATE_TIME)
            .pointsFor(UPDATED_POINTS_FOR)
            .points(UPDATED_POINTS);
        return userPoints;
    }

    @BeforeEach
    public void initTest() {
        userPointsRepository.deleteAll();
        userPoints = createEntity();
    }

    @Test
    public void createUserPoints() throws Exception {
        int databaseSizeBeforeCreate = userPointsRepository.findAll().size();

        // Create the UserPoints
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);
        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPoints in the database
        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeCreate + 1);
        UserPoints testUserPoints = userPointsList.get(userPointsList.size() - 1);
        assertThat(testUserPoints.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserPoints.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testUserPoints.getSessionTopic()).isEqualTo(DEFAULT_SESSION_TOPIC);
        assertThat(testUserPoints.getSessionDateTime()).isEqualTo(DEFAULT_SESSION_DATE_TIME);
        assertThat(testUserPoints.getPointsFor()).isEqualTo(DEFAULT_POINTS_FOR);
        assertThat(testUserPoints.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    public void createUserPointsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPointsRepository.findAll().size();

        // Create the UserPoints with an existing ID
        userPoints.setId("existing_id");
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPoints in the database
        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setUserId(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setSessionId(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSessionTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setSessionTopic(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSessionDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setSessionDateTime(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPointsForIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setPointsFor(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPointsRepository.findAll().size();
        // set the field null
        userPoints.setPoints(null);

        // Create the UserPoints, which fails.
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        restUserPointsMockMvc.perform(post("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserPoints() throws Exception {
        // Initialize the database
        userPointsRepository.save(userPoints);

        // Get all the userPointsList
        restUserPointsMockMvc.perform(get("/api/user-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPoints.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].sessionTopic").value(hasItem(DEFAULT_SESSION_TOPIC)))
            .andExpect(jsonPath("$.[*].sessionDateTime").value(hasItem(sameInstant(DEFAULT_SESSION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].pointsFor").value(hasItem(DEFAULT_POINTS_FOR.toString())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)));
    }
    
    @Test
    public void getUserPoints() throws Exception {
        // Initialize the database
        userPointsRepository.save(userPoints);

        // Get the userPoints
        restUserPointsMockMvc.perform(get("/api/user-points/{id}", userPoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPoints.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.sessionTopic").value(DEFAULT_SESSION_TOPIC))
            .andExpect(jsonPath("$.sessionDateTime").value(sameInstant(DEFAULT_SESSION_DATE_TIME)))
            .andExpect(jsonPath("$.pointsFor").value(DEFAULT_POINTS_FOR.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    public void getNonExistingUserPoints() throws Exception {
        // Get the userPoints
        restUserPointsMockMvc.perform(get("/api/user-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserPoints() throws Exception {
        // Initialize the database
        userPointsRepository.save(userPoints);

        int databaseSizeBeforeUpdate = userPointsRepository.findAll().size();

        // Update the userPoints
        UserPoints updatedUserPoints = userPointsRepository.findById(userPoints.getId()).get();
        updatedUserPoints
            .userId(UPDATED_USER_ID)
            .sessionId(UPDATED_SESSION_ID)
            .sessionTopic(UPDATED_SESSION_TOPIC)
            .sessionDateTime(UPDATED_SESSION_DATE_TIME)
            .pointsFor(UPDATED_POINTS_FOR)
            .points(UPDATED_POINTS);
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(updatedUserPoints);

        restUserPointsMockMvc.perform(put("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isOk());

        // Validate the UserPoints in the database
        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeUpdate);
        UserPoints testUserPoints = userPointsList.get(userPointsList.size() - 1);
        assertThat(testUserPoints.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserPoints.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testUserPoints.getSessionTopic()).isEqualTo(UPDATED_SESSION_TOPIC);
        assertThat(testUserPoints.getSessionDateTime()).isEqualTo(UPDATED_SESSION_DATE_TIME);
        assertThat(testUserPoints.getPointsFor()).isEqualTo(UPDATED_POINTS_FOR);
        assertThat(testUserPoints.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    public void updateNonExistingUserPoints() throws Exception {
        int databaseSizeBeforeUpdate = userPointsRepository.findAll().size();

        // Create the UserPoints
        UserPointsDTO userPointsDTO = userPointsMapper.toDto(userPoints);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPointsMockMvc.perform(put("/api/user-points")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPointsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPoints in the database
        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserPoints() throws Exception {
        // Initialize the database
        userPointsRepository.save(userPoints);

        int databaseSizeBeforeDelete = userPointsRepository.findAll().size();

        // Delete the userPoints
        restUserPointsMockMvc.perform(delete("/api/user-points/{id}", userPoints.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPoints> userPointsList = userPointsRepository.findAll();
        assertThat(userPointsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
