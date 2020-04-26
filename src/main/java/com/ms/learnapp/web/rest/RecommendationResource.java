package com.ms.learnapp.web.rest;

import com.ms.learnapp.service.CourseService;
import com.ms.learnapp.service.dto.CourseDTO;
import com.ms.learnapp.security.SecurityUtils;
import com.ms.learnapp.service.UserService;
import com.ms.learnapp.domain.User;
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
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class RecommendationResource {

    private final Logger log = LoggerFactory.getLogger(RecommendationResource.class);
    

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseService courseService;
    private final UserService userservice;
    private User currentUser;

    public RecommendationResource(CourseService courseService,UserService userservice) {
        this.courseService = courseService;
        this.userservice = userservice;
    }

    /**
     * {@code GET  /recommendation/sme} : get all the recommendations for SMEs
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/recommendation/sme")
    public ResponseEntity<List<CourseDTO>> getRecommendationForSME(Pageable pageable) 
    {
        log.debug("REST request to get Recommendations for SMEs"); 
        currentUser = userservice.getCurrentUser();     
        //log.debug("deepali "+currentUser.getInterestedInSkills()); 
        List<String> expertInSkills = currentUser.getExpertInSkills();
          
        Page<CourseDTO> page = courseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recommendation/participant")
    public ResponseEntity<List<CourseDTO>> getRecommendationForParticipant(Pageable pageable) {
        log.debug("REST request to get Recommendations for Participants");  
        currentUser = userservice.getCurrentUser();    
        List<String> interestedInSkills = currentUser.getInterestedInSkills(); 
        log.debug("interested Skills : "+currentUser.getInterestedInSkills()); 
        List<CourseDTO> recommendationCourses = new ArrayList<CourseDTO>();
        Page<CourseDTO> page = courseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    
        if(interestedInSkills!=null && interestedInSkills.size()>0)
        {
          List<CourseDTO> allCourses = page.getContent();
          log.debug("all courses :" + allCourses.size());
          for(CourseDTO courseDTO : allCourses)
          {
               List<String> courseSkills = courseDTO.getSmeSkills();
               Set<String> result = courseSkills.stream()
                                    .distinct()
                                    .filter(interestedInSkills::contains)
                                    .collect(Collectors.toSet());                    
                if(result.size()>0)
                {
                    recommendationCourses.add(courseDTO);
                }
          }
          if(recommendationCourses.size()>0)
          {
             Collections.sort(recommendationCourses,new Comparator<CourseDTO>(){
                 public int compare(CourseDTO a,CourseDTO b)
                 {
                     return b.getParticipantPoints() - a.getParticipantPoints();
                 }
             });
            return ResponseEntity.ok().headers(headers).body(recommendationCourses);
          }
        }
  
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
