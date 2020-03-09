package com.ms.learnapp.web.rest;

import com.ms.learnapp.service.SessionService;
import com.ms.learnapp.web.rest.errors.BadRequestAlertException;
import com.ms.learnapp.service.dto.SessionDTO;

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
 * REST controller for managing {@link com.ms.learnapp.domain.Session}.
 */
@RestController
@RequestMapping("/api")
public class SessionResource {

    private final Logger log = LoggerFactory.getLogger(SessionResource.class);

    private static final String ENTITY_NAME = "session";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionService sessionService;

    public SessionResource(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * {@code POST  /sessions} : Create a new session.
     *
     * @param sessionDTO the sessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionDTO, or with status {@code 400 (Bad Request)} if the session has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sessions")
    public ResponseEntity<SessionDTO> createSession(@Valid @RequestBody SessionDTO sessionDTO) throws URISyntaxException {
        log.debug("REST request to save Session : {}", sessionDTO);
        if (sessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new session cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionDTO result = sessionService.save(sessionDTO);
        return ResponseEntity.created(new URI("/api/sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sessions} : Updates an existing session.
     *
     * @param sessionDTO the sessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionDTO,
     * or with status {@code 400 (Bad Request)} if the sessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sessions")
    public ResponseEntity<SessionDTO> updateSession(@Valid @RequestBody SessionDTO sessionDTO) throws URISyntaxException {
        log.debug("REST request to update Session : {}", sessionDTO);
        if (sessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SessionDTO result = sessionService.save(sessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sessions} : get all the sessions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessions in body.
     */
    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDTO>> getAllSessions(Pageable pageable, SessionDTO dto) {
        log.debug("REST request to get a page of Sessions");
        Page<SessionDTO> page = sessionService.findAll(pageable, dto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sessions/:id} : get the "id" session.
     *
     * @param id the id of the sessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable String id) {
        log.debug("REST request to get Session : {}", id);
        Optional<SessionDTO> sessionDTO = sessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionDTO);
    }

    /**
     * {@code DELETE  /sessions/:id} : delete the "id" session.
     *
     * @param id the id of the sessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable String id) {
        log.debug("REST request to delete Session : {}", id);
        sessionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
