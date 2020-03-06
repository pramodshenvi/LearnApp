package com.ms.learnapp.repository;

import com.ms.learnapp.domain.Course;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

}
