package it.fabiofenoglio.lelohub.service;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionParameterMapper;

/**
 * Service Implementation for managing {@link SequenceStepConditionParameter}.
 */
@Service
@Transactional
public class SequenceStepConditionParameterService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionParameterService.class);

    private final SequenceStepConditionParameterRepository sequenceStepConditionParameterRepository;

    private final SequenceStepConditionParameterMapper sequenceStepConditionParameterMapper;

    public SequenceStepConditionParameterService(SequenceStepConditionParameterRepository sequenceStepConditionParameterRepository, SequenceStepConditionParameterMapper sequenceStepConditionParameterMapper) {
        this.sequenceStepConditionParameterRepository = sequenceStepConditionParameterRepository;
        this.sequenceStepConditionParameterMapper = sequenceStepConditionParameterMapper;
    }

    /**
     * Save a sequenceStepConditionParameter.
     *
     * @param sequenceStepConditionParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepConditionParameterDTO save(SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO) {
        log.debug("Request to save SequenceStepConditionParameter : {}", sequenceStepConditionParameterDTO);
        SequenceStepConditionParameter sequenceStepConditionParameter = sequenceStepConditionParameterMapper.toEntity(sequenceStepConditionParameterDTO);
        sequenceStepConditionParameter = sequenceStepConditionParameterRepository.save(sequenceStepConditionParameter);
        return sequenceStepConditionParameterMapper.toDto(sequenceStepConditionParameter);
    }

    /**
     * Get all the sequenceStepConditionParameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepConditionParameters");
        return sequenceStepConditionParameterRepository.findAll(pageable)
            .map(sequenceStepConditionParameterMapper::toDto);
    }

    /**
     * Get one sequenceStepConditionParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepConditionParameterDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepConditionParameter : {}", id);
        return sequenceStepConditionParameterRepository.findById(id)
            .map(sequenceStepConditionParameterMapper::toDto);
    }

    /**
     * Delete the sequenceStepConditionParameter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepConditionParameter : {}", id);
        sequenceStepConditionParameterRepository.deleteById(id);
    }
    
    public static SequenceStepConditionParameter clone(SequenceStepConditionParameter input, SequenceStepCondition parent, EntityManager contextualEntityManager) {
    	contextualEntityManager.detach(input);

    	input.setId(null);
    	input.setCreatedBy(null);
    	input.setCreatedDate(null);
        input.setLastModifiedBy(null);
        input.setLastModifiedDate(null);
        input.setCondition(parent);
        
        return input;
    }
}
