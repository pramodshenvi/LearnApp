package com.ms.learnapp.web.rest;

import com.ms.learnapp.service.SessionParticipationService;
import com.ms.learnapp.web.rest.errors.BadRequestAlertException;
import com.ms.learnapp.service.dto.SessionParticipationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ms.learnapp.domain.SessionParticipation}.
 */
@RestController
@RequestMapping("/api")
public class SessionParticipationResource {

    private final Logger log = LoggerFactory.getLogger(SessionParticipationResource.class);

    private static final String ENTITY_NAME = "sessionParticipation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionParticipationService sessionParticipationService;

    public SessionParticipationResource(SessionParticipationService sessionParticipationService) {
        this.sessionParticipationService = sessionParticipationService;
    }

    /**
     * {@code POST  /session-participations} : Create a new sessionParticipation.
     *
     * @param sessionParticipationDTO the sessionParticipationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionParticipationDTO, or with status {@code 400 (Bad Request)} if the sessionParticipation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/session-participations")
    public ResponseEntity<SessionParticipationDTO> createSessionParticipation(@Valid @RequestBody SessionParticipationDTO sessionParticipationDTO) throws URISyntaxException {
        log.debug("REST request to save SessionParticipation : {}", sessionParticipationDTO);
        if (sessionParticipationDTO.getId() != null) {
            throw new BadRequestAlertException("A new sessionParticipation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionParticipationDTO result = sessionParticipationService.save(sessionParticipationDTO);
        return ResponseEntity.created(new URI("/api/session-participations/" + result.getId()))
            .headers(null)
            .body(result);
    }

    /**
     * {@code GET  /session-participations} : get all the sessionParticipations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionParticipations in body.
     */
    @GetMapping("/session-participations")
    public ResponseEntity<List<SessionParticipationDTO>> getAllSessionParticipations(SessionParticipationDTO dto, Pageable pageable) {
        log.debug("REST request to get a page of SessionParticipations");
        Page<SessionParticipationDTO> page = sessionParticipationService.findAll(dto, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /session-participations} : Get matching Session Participations for a given user.
     *
     * @param sessionParticipationDTO the sessionParticipationDTO to create.
     * @return a list of {@link SessionParticipationDTO} 
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-session-participations")
    public List<SessionParticipationDTO> getMatchingSessionParticipationsForUser(@RequestBody SessionParticipationDTO sessionParticipationDTO) throws URISyntaxException {
        log.debug("REST request to get a page of SessionParticipations");
        return sessionParticipationService.findMatchingSessionParticipationsForUser(sessionParticipationDTO);
    }

    /**
     * {@code GET  /session-participations/:id} : get the "id" sessionParticipation.
     *
     * @param id the id of the sessionParticipationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionParticipationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/session-participations/{id}")
    public ResponseEntity<SessionParticipationDTO> getSessionParticipation(@PathVariable String id) {
        log.debug("REST request to get SessionParticipation : {}", id);
        Optional<SessionParticipationDTO> sessionParticipationDTO = sessionParticipationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionParticipationDTO);
    }

    /**
     * {@code DELETE  /session-participations/:id} : delete the "id" sessionParticipation.
     *
     * @param id the id of the sessionParticipationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/session-participations/{id}")
    public ResponseEntity<Void> deleteSessionParticipation(@PathVariable String id) {
        log.debug("REST request to delete SessionParticipation : {}", id);
        sessionParticipationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
