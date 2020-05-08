package it.fabiofenoglio.lelohub.service;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SequenceStepActionDefinitionParameter}.
 */
@Service
@Transactional
public class SequenceStepActionDefinitionParameterService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionParameterService.class);

    private final SequenceStepActionDefinitionParameterRepository sequenceStepActionDefinitionParameterRepository;

    private final SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper;

    public SequenceStepActionDefinitionParameterService(SequenceStepActionDefinitionParameterRepository sequenceStepActionDefinitionParameterRepository, SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper) {
        this.sequenceStepActionDefinitionParameterRepository = sequenceStepActionDefinitionParameterRepository;
        this.sequenceStepActionDefinitionParameterMapper = sequenceStepActionDefinitionParameterMapper;
    }

    /**
     * Save a sequenceStepActionDefinitionParameter.
     *
     * @param sequenceStepActionDefinitionParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepActionDefinitionParameterDTO save(SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO) {
        log.debug("Request to save SequenceStepActionDefinitionParameter : {}", sequenceStepActionDefinitionParameterDTO);
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameterMapper.toEntity(sequenceStepActionDefinitionParameterDTO);
        sequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameterRepository.save(sequenceStepActionDefinitionParameter);
        return sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);
    }

    /**
     * Get all the sequenceStepActionDefinitionParameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDefinitionParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepActionDefinitionParameters");
        return sequenceStepActionDefinitionParameterRepository.findAll(pageable)
            .map(sequenceStepActionDefinitionParameterMapper::toDto);
    }

    /**
     * Get one sequenceStepActionDefinitionParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepActionDefinitionParameterDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepActionDefinitionParameter : {}", id);
        return sequenceStepActionDefinitionParameterRepository.findById(id)
            .map(sequenceStepActionDefinitionParameterMapper::toDto);
    }

    /**
     * Delete the sequenceStepActionDefinitionParameter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepActionDefinitionParameter : {}", id);
        sequenceStepActionDefinitionParameterRepository.deleteById(id);
    }
}
