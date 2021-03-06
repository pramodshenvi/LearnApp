package com.ms.learnapp.service;

import com.ms.learnapp.domain.Course;
import com.ms.learnapp.repository.CourseRepository;
import com.ms.learnapp.service.dto.CourseDTO;
import com.ms.learnapp.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Course}.
 */
@Service
public class CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable)
            .map(courseMapper::toDto);
    }

    /**
     * Get all the matching courses.
     *
     * @param CourseDTO and StringMatcher the pagination information.
     * @return the list of entities.
     */
    public List<CourseDTO> findAll(CourseDTO courseDTO, StringMatcher sm) {
        log.debug("Request to get all matching Courses");
        ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(sm);
        return courseRepository.findAll(Example.of(courseMapper.toEntity(courseDTO),m))
        .stream()
        .map(courseMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one course by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CourseDTO> findOne(String id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id)
            .map(courseMapper::toDto);
    }

    /**
     * Delete the course by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
