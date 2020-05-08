package it.fabiofenoglio.lelohub.service;

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SequenceStepConditionDefinition}.
 */
@Service
@Transactional
public class SequenceStepConditionDefinitionService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionService.class);

    private final SequenceStepConditionDefinitionRepository sequenceStepConditionDefinitionRepository;

    private final SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper;

    public SequenceStepConditionDefinitionService(SequenceStepConditionDefinitionRepository sequenceStepConditionDefinitionRepository, SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper) {
        this.sequenceStepConditionDefinitionRepository = sequenceStepConditionDefinitionRepository;
        this.sequenceStepConditionDefinitionMapper = sequenceStepConditionDefinitionMapper;
    }

    /**
     * Save a sequenceStepConditionDefinition.
     *
     * @param sequenceStepConditionDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepConditionDefinitionDTO save(SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO) {
        log.debug("Request to save SequenceStepConditionDefinition : {}", sequenceStepConditionDefinitionDTO);
        SequenceStepConditionDefinition sequenceStepConditionDefinition = sequenceStepConditionDefinitionMapper.toEntity(sequenceStepConditionDefinitionDTO);
        sequenceStepConditionDefinition = sequenceStepConditionDefinitionRepository.save(sequenceStepConditionDefinition);
        return sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);
    }

    /**
     * Get all the sequenceStepConditionDefinitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDefinitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepConditionDefinitions");
        return sequenceStepConditionDefinitionRepository.findAll(pageable)
            .map(sequenceStepConditionDefinitionMapper::toDto);
    }

    /**
     * Get one sequenceStepConditionDefinition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepConditionDefinitionDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepConditionDefinition : {}", id);
        return sequenceStepConditionDefinitionRepository.findById(id)
            .map(sequenceStepConditionDefinitionMapper::toDto);
    }

    /**
     * Delete the sequenceStepConditionDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepConditionDefinition : {}", id);
        sequenceStepConditionDefinitionRepository.deleteById(id);
    }
}
