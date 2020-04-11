package com.ms.learnapp.service;

import com.ms.learnapp.domain.UserPoints;
import com.ms.learnapp.domain.enumeration.PointsFor;
import com.ms.learnapp.repository.UserPointsRepository;
import com.ms.learnapp.service.dto.UserPointsDTO;
import com.ms.learnapp.service.mapper.UserPointsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserPoints}.
 */
@Service
public class UserPointsService {

    private final Logger log = LoggerFactory.getLogger(UserPointsService.class);

    private final UserPointsRepository userPointsRepository;

    private final UserPointsMapper userPointsMapper;

    public UserPointsService(UserPointsRepository userPointsRepository, UserPointsMapper userPointsMapper) {
        this.userPointsRepository = userPointsRepository;
        this.userPointsMapper = userPointsMapper;
    }

    /**
     * Save a userPoints.
     *
     * @param userPointsDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPointsDTO save(UserPointsDTO userPointsDTO) {
        log.debug("Request to save UserPoints : {}", userPointsDTO);
        UserPoints userPoints = userPointsMapper.toEntity(userPointsDTO);
        userPoints = userPointsRepository.save(userPoints);

        UserPointsDTO upd = new UserPointsDTO();
        upd.setUserId(userPointsDTO.getUserId());
        upd.setSessionTopic(PointsFor.AGGREGATED.toString());
        
        Integer points = 0;
        List<UserPointsDTO> userPointsList = findAll(upd, StringMatcher.EXACT);
        if(userPointsList != null && userPointsList.size() > 0) {
            upd.setId(userPointsList.get(0).getId());
            points = userPointsList.get(0).getPoints();
        }

        upd.setSessionId(PointsFor.AGGREGATED.toString());
        upd.setSessionDateTime(ZonedDateTime.now());
        upd.setPointsFor(PointsFor.AGGREGATED);
        upd.setPoints(points + userPointsDTO.getPoints());

        userPointsRepository.save(userPointsMapper.toEntity(upd));

        return userPointsMapper.toDto(userPoints);
    }

    /**
     * Get all the userPoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserPointsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPoints");
        return userPointsRepository.findAll(pageable)
            .map(userPointsMapper::toDto);
    }

    public List<UserPointsDTO> findAll(UserPointsDTO dto, StringMatcher sm) {
        log.debug("Request to get all matching user point records");
        ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(sm);
        return userPointsRepository.findAll(Example.of(userPointsMapper.toEntity(dto),m))
        .stream()
        .map(userPointsMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userPoints by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserPointsDTO> findOne(String id) {
        log.debug("Request to get UserPoints : {}", id);
        return userPointsRepository.findById(id)
            .map(userPointsMapper::toDto);
    }

    /**
     * Delete the userPoints by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserPoints : {}", id);
        userPointsRepository.deleteById(id);
    }
}
