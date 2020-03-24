package com.ms.learnapp.repository;

import com.ms.learnapp.domain.SessionParticipation;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SessionParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionParticipationRepository extends MongoRepository<SessionParticipation, String> {

}
