package it.fabiofenoglio.lelohub.service;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SequenceStepActionDefinition}.
 */
@Service
@Transactional
public class SequenceStepActionDefinitionService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionService.class);

    private final SequenceStepActionDefinitionRepository sequenceStepActionDefinitionRepository;

    private final SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper;

    public SequenceStepActionDefinitionService(SequenceStepActionDefinitionRepository sequenceStepActionDefinitionRepository, SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper) {
        this.sequenceStepActionDefinitionRepository = sequenceStepActionDefinitionRepository;
        this.sequenceStepActionDefinitionMapper = sequenceStepActionDefinitionMapper;
    }

    /**
     * Save a sequenceStepActionDefinition.
     *
     * @param sequenceStepActionDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepActionDefinitionDTO save(SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO) {
        log.debug("Request to save SequenceStepActionDefinition : {}", sequenceStepActionDefinitionDTO);
        SequenceStepActionDefinition sequenceStepActionDefinition = sequenceStepActionDefinitionMapper.toEntity(sequenceStepActionDefinitionDTO);
        sequenceStepActionDefinition = sequenceStepActionDefinitionRepository.save(sequenceStepActionDefinition);
        return sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);
    }

    /**
     * Get all the sequenceStepActionDefinitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDefinitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepActionDefinitions");
        return sequenceStepActionDefinitionRepository.findAll(pageable)
            .map(sequenceStepActionDefinitionMapper::toDto);
    }

    /**
     * Get one sequenceStepActionDefinition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepActionDefinitionDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepActionDefinition : {}", id);
        return sequenceStepActionDefinitionRepository.findById(id)
            .map(sequenceStepActionDefinitionMapper::toDto);
    }

    /**
     * Delete the sequenceStepActionDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepActionDefinition : {}", id);
        sequenceStepActionDefinitionRepository.deleteById(id);
    }
}
