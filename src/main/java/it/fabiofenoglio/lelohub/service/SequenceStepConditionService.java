package it.fabiofenoglio.lelohub.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.fabiofenoglio.lelohub.domain.SequenceStep;
import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionMapper;

/**
 * Service Implementation for managing {@link SequenceStepCondition}.
 */
@Service
@Transactional
public class SequenceStepConditionService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionService.class);

    private final SequenceStepConditionRepository sequenceStepConditionRepository;

    private final SequenceStepConditionMapper sequenceStepConditionMapper;

    public SequenceStepConditionService(SequenceStepConditionRepository sequenceStepConditionRepository, SequenceStepConditionMapper sequenceStepConditionMapper) {
        this.sequenceStepConditionRepository = sequenceStepConditionRepository;
        this.sequenceStepConditionMapper = sequenceStepConditionMapper;
    }

    /**
     * Save a sequenceStepCondition.
     *
     * @param sequenceStepConditionDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepConditionDTO save(SequenceStepConditionDTO sequenceStepConditionDTO) {
        log.debug("Request to save SequenceStepCondition : {}", sequenceStepConditionDTO);
        SequenceStepCondition sequenceStepCondition = sequenceStepConditionMapper.toEntity(sequenceStepConditionDTO);
        sequenceStepCondition = sequenceStepConditionRepository.save(sequenceStepCondition);
        return sequenceStepConditionMapper.toDto(sequenceStepCondition);
    }

    /**
     * Get all the sequenceStepConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepConditions");
        return sequenceStepConditionRepository.findAll(pageable)
            .map(sequenceStepConditionMapper::toDto);
    }

    /**
     * Get one sequenceStepCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepConditionDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepCondition : {}", id);
        return sequenceStepConditionRepository.findById(id)
            .map(sequenceStepConditionMapper::toDto);
    }

    /**
     * Delete the sequenceStepCondition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepCondition : {}", id);
        sequenceStepConditionRepository.deleteById(id);
    }

    public static SequenceStepCondition clone(
    		SequenceStepCondition input, 
    		SequenceStep parent, SequenceStepCondition andParent, SequenceStepCondition orParent, 
    		EntityManager contextualEntityManager) {
    	contextualEntityManager.detach(input);

    	input.setId(null);
    	input.setCreatedBy(null);
    	input.setCreatedDate(null);
        input.setLastModifiedBy(null);
        input.setLastModifiedDate(null);
        input.setStep(parent);
        input.setOtherAndCondition(andParent);
        input.setOtherOrCondition(orParent);

        input.setAndConditions(input.getAndConditions().stream()
        		.map(o -> SequenceStepConditionService.clone(o, null, input, null, contextualEntityManager))
        		.collect(Collectors.toSet()) );
        input.setOrConditions(input.getOrConditions().stream()
        		.map(o -> SequenceStepConditionService.clone(o, null, null, input, contextualEntityManager))
        		.collect(Collectors.toSet()) );
        input.setParameters(input.getParameters().stream()
        		.map(o -> SequenceStepConditionParameterService.clone(o, input, contextualEntityManager))
        		.collect(Collectors.toSet()) );

        return input;
    }
}
