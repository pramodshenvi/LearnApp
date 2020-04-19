package com.ms.learnapp.web.rest;

import java.util.List;

import com.ms.learnapp.security.SecurityUtils;
import com.ms.learnapp.service.UserProfileService;
import com.ms.learnapp.service.dto.SessionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.ms.learnapp.domain.UserProfile}.
 */
@RestController
@RequestMapping("/api/user-profile")
public class UserProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileResource.class);

    private static final String ENTITY_NAME = "userProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserProfileService userProfileService;

    public UserProfileResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    /**
     * {@code GET  /registered-sessions} : all sessions registered by the user.
     * 
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessions registered in body.
     */
    @GetMapping("/registered-sessions")
    public ResponseEntity<List<SessionDTO>> getAllRegisteredSessions() {
        log.debug("REST request to get all registered sessions for the current user");
        String userId = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("Current user login not found"));
        List<SessionDTO> sessions = userProfileService.getAllSessionsRegisteredByUser(userId);
        return ResponseEntity.ok().body(sessions);
    }
}
