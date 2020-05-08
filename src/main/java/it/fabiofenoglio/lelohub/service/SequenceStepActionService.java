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
import it.fabiofenoglio.lelohub.domain.SequenceStepAction;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionMapper;

/**
 * Service Implementation for managing {@link SequenceStepAction}.
 */
@Service
@Transactional
public class SequenceStepActionService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionService.class);

    private final SequenceStepActionRepository sequenceStepActionRepository;

    private final SequenceStepActionMapper sequenceStepActionMapper;

    public SequenceStepActionService(SequenceStepActionRepository sequenceStepActionRepository, SequenceStepActionMapper sequenceStepActionMapper) {
        this.sequenceStepActionRepository = sequenceStepActionRepository;
        this.sequenceStepActionMapper = sequenceStepActionMapper;
    }

    /**
     * Save a sequenceStepAction.
     *
     * @param sequenceStepActionDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepActionDTO save(SequenceStepActionDTO sequenceStepActionDTO) {
        log.debug("Request to save SequenceStepAction : {}", sequenceStepActionDTO);
        SequenceStepAction sequenceStepAction = sequenceStepActionMapper.toEntity(sequenceStepActionDTO);
        sequenceStepAction = sequenceStepActionRepository.save(sequenceStepAction);
        return sequenceStepActionMapper.toDto(sequenceStepAction);
    }

    /**
     * Get all the sequenceStepActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepActions");
        return sequenceStepActionRepository.findAll(pageable)
            .map(sequenceStepActionMapper::toDto);
    }

    /**
     * Get one sequenceStepAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepActionDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepAction : {}", id);
        return sequenceStepActionRepository.findById(id)
            .map(sequenceStepActionMapper::toDto);
    }

    /**
     * Delete the sequenceStepAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepAction : {}", id);
        sequenceStepActionRepository.deleteById(id);
    }
    
    public static SequenceStepAction clone(SequenceStepAction input, SequenceStep parent, EntityManager contextualEntityManager) {
    	contextualEntityManager.detach(input);

    	input.setId(null);
    	input.setCreatedBy(null);
    	input.setCreatedDate(null);
        input.setLastModifiedBy(null);
        input.setLastModifiedDate(null);
        input.setStep(parent);

        input.setParameters(input.getParameters().stream()
        		.map(o -> SequenceStepActionParameterService.clone(o, input, contextualEntityManager))
        		.collect(Collectors.toSet()) );

        return input;
    }
}
