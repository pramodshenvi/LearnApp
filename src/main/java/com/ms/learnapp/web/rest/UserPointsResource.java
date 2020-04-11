package com.ms.learnapp.web.rest;

import com.ms.learnapp.service.UserPointsService;
import com.ms.learnapp.web.rest.errors.BadRequestAlertException;
import com.ms.learnapp.service.dto.UserPointsDTO;

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
 * REST controller for managing {@link com.ms.learnapp.domain.UserPoints}.
 */
@RestController
@RequestMapping("/api")
public class UserPointsResource {

    private final Logger log = LoggerFactory.getLogger(UserPointsResource.class);

    private static final String ENTITY_NAME = "userPoints";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPointsService userPointsService;

    public UserPointsResource(UserPointsService userPointsService) {
        this.userPointsService = userPointsService;
    }

    /**
     * {@code POST  /user-points} : Create a new userPoints.
     *
     * @param userPointsDTO the userPointsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPointsDTO, or with status {@code 400 (Bad Request)} if the userPoints has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-points")
    public ResponseEntity<UserPointsDTO> createUserPoints(@Valid @RequestBody UserPointsDTO userPointsDTO) throws URISyntaxException {
        log.debug("REST request to save UserPoints : {}", userPointsDTO);
        if (userPointsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPointsDTO result = userPointsService.save(userPointsDTO);
        return ResponseEntity.created(new URI("/api/user-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-points} : Updates an existing userPoints.
     *
     * @param userPointsDTO the userPointsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPointsDTO,
     * or with status {@code 400 (Bad Request)} if the userPointsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPointsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-points")
    public ResponseEntity<UserPointsDTO> updateUserPoints(@Valid @RequestBody UserPointsDTO userPointsDTO) throws URISyntaxException {
        log.debug("REST request to update UserPoints : {}", userPointsDTO);
        if (userPointsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPointsDTO result = userPointsService.save(userPointsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userPointsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-points} : get all the userPoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPoints in body.
     */
    @GetMapping("/user-points")
    public ResponseEntity<List<UserPointsDTO>> getAllUserPoints(Pageable pageable) {
        log.debug("REST request to get a page of UserPoints");
        Page<UserPointsDTO> page = userPointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-points/:id} : get the "id" userPoints.
     *
     * @param id the id of the userPointsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPointsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-points/{id}")
    public ResponseEntity<UserPointsDTO> getUserPoints(@PathVariable String id) {
        log.debug("REST request to get UserPoints : {}", id);
        Optional<UserPointsDTO> userPointsDTO = userPointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPointsDTO);
    }

    /**
     * {@code DELETE  /user-points/:id} : delete the "id" userPoints.
     *
     * @param id the id of the userPointsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-points/{id}")
    public ResponseEntity<Void> deleteUserPoints(@PathVariable String id) {
        log.debug("REST request to delete UserPoints : {}", id);
        userPointsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
