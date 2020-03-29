package com.ms.learnapp.repository;

import java.util.List;

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
    @Query("{'user_id' : ?0 , 'session_id' : {$in:?1 }}")
    List<SessionParticipation> findSessionParticipationByUserIdAndSessionIdContaining(String userId, List<String> sessionIds);
}
