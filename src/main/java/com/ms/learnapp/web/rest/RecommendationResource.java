package com.ms.learnapp.web.rest;

import com.ms.learnapp.service.CourseService;
import com.ms.learnapp.service.dto.CourseDTO;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecommendationResource {

    private final Logger log = LoggerFactory.getLogger(RecommendationResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseService courseService;

    public RecommendationResource(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * {@code GET  /recommendation/sme} : get all the recommendations for SMEs
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/recommendation/sme")
    public ResponseEntity<List<CourseDTO>> getRecommendationForSME(Pageable pageable) {
        log.debug("REST request to get Recommendations for SMEs");
        Page<CourseDTO> page = courseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recommendation/participant")
    public ResponseEntity<List<CourseDTO>> getRecommendationForParticipant(Pageable pageable) {
        log.debug("REST request to get Recommendations for Participants");
        Page<CourseDTO> page = courseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
