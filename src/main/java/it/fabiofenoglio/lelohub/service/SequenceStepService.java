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

import it.fabiofenoglio.lelohub.domain.Sequence;
import it.fabiofenoglio.lelohub.domain.SequenceStep;
import it.fabiofenoglio.lelohub.repository.SequenceStepRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepMapper;

/**
 * Service Implementation for managing {@link SequenceStep}.
 */
@Service
@Transactional
public class SequenceStepService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepService.class);

    private final SequenceStepRepository sequenceStepRepository;

    private final SequenceStepMapper sequenceStepMapper;

    public SequenceStepService(SequenceStepRepository sequenceStepRepository, SequenceStepMapper sequenceStepMapper) {
        this.sequenceStepRepository = sequenceStepRepository;
        this.sequenceStepMapper = sequenceStepMapper;
    }

    /**
     * Save a sequenceStep.
     *
     * @param sequenceStepDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepDTO save(SequenceStepDTO sequenceStepDTO) {
        log.debug("Request to save SequenceStep : {}", sequenceStepDTO);
        SequenceStep sequenceStep = sequenceStepMapper.toEntity(sequenceStepDTO);
        sequenceStep = sequenceStepRepository.save(sequenceStep);
        return sequenceStepMapper.toDto(sequenceStep);
    }

    /**
     * Get all the sequenceSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceSteps");
        return sequenceStepRepository.findAll(pageable)
            .map(sequenceStepMapper::toDto);
    }

    /**
     * Get one sequenceStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepDTO> findOne(Long id) {
        log.debug("Request to get SequenceStep : {}", id);
        return sequenceStepRepository.findById(id)
            .map(sequenceStepMapper::toDto);
    }

    /**
     * Delete the sequenceStep by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStep : {}", id);
        sequenceStepRepository.deleteById(id);
    }
    
    public static SequenceStep clone(SequenceStep input, Sequence parent, EntityManager contextualEntityManager) {
    	contextualEntityManager.detach(input);

    	input.setId(null);
    	input.setCreatedBy(null);
    	input.setCreatedDate(null);
        input.setLastModifiedBy(null);
        input.setLastModifiedDate(null);
        input.setSequence(parent);

        input.setConditions(input.getConditions().stream()
        		.map(o -> SequenceStepConditionService.clone(o, input, null, null, contextualEntityManager))
        		.collect(Collectors.toSet()) );

        input.setActions(input.getActions().stream()
        		.map(o -> SequenceStepActionService.clone(o, input, contextualEntityManager))
        		.collect(Collectors.toSet()) );

        return input;
    }
}
