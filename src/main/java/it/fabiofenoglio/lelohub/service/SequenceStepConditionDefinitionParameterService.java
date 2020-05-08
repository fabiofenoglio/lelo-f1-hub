package it.fabiofenoglio.lelohub.service;

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SequenceStepConditionDefinitionParameter}.
 */
@Service
@Transactional
public class SequenceStepConditionDefinitionParameterService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionParameterService.class);

    private final SequenceStepConditionDefinitionParameterRepository sequenceStepConditionDefinitionParameterRepository;

    private final SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper;

    public SequenceStepConditionDefinitionParameterService(SequenceStepConditionDefinitionParameterRepository sequenceStepConditionDefinitionParameterRepository, SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper) {
        this.sequenceStepConditionDefinitionParameterRepository = sequenceStepConditionDefinitionParameterRepository;
        this.sequenceStepConditionDefinitionParameterMapper = sequenceStepConditionDefinitionParameterMapper;
    }

    /**
     * Save a sequenceStepConditionDefinitionParameter.
     *
     * @param sequenceStepConditionDefinitionParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepConditionDefinitionParameterDTO save(SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO) {
        log.debug("Request to save SequenceStepConditionDefinitionParameter : {}", sequenceStepConditionDefinitionParameterDTO);
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameterMapper.toEntity(sequenceStepConditionDefinitionParameterDTO);
        sequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameterRepository.save(sequenceStepConditionDefinitionParameter);
        return sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);
    }

    /**
     * Get all the sequenceStepConditionDefinitionParameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDefinitionParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepConditionDefinitionParameters");
        return sequenceStepConditionDefinitionParameterRepository.findAll(pageable)
            .map(sequenceStepConditionDefinitionParameterMapper::toDto);
    }

    /**
     * Get one sequenceStepConditionDefinitionParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepConditionDefinitionParameterDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepConditionDefinitionParameter : {}", id);
        return sequenceStepConditionDefinitionParameterRepository.findById(id)
            .map(sequenceStepConditionDefinitionParameterMapper::toDto);
    }

    /**
     * Delete the sequenceStepConditionDefinitionParameter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepConditionDefinitionParameter : {}", id);
        sequenceStepConditionDefinitionParameterRepository.deleteById(id);
    }
}
