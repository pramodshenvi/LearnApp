package com.ms.learnapp.service;

import com.ms.learnapp.domain.Skill;
import com.ms.learnapp.repository.SkillRepository;
import com.ms.learnapp.service.dto.SkillDTO;
import com.ms.learnapp.service.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    public List<SkillDTO> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll().stream()
            .map(skillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<SkillDTO> findAllForCriteria(SkillDTO s) {
        log.debug("Request to get all skills that match the criteria: "+s.toString());
        ExampleMatcher m = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        return skillRepository.findAll(Example.of(skillMapper.toEntity(s),m))
        .stream()
        .map(skillMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one skill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SkillDTO> findOne(String id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id)
            .map(skillMapper::toDto);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
