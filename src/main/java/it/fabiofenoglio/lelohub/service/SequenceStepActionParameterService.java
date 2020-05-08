package it.fabiofenoglio.lelohub.service;

import it.fabiofenoglio.lelohub.domain.SequenceStepAction;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.persistence.EntityManager;

/**
 * Service Implementation for managing {@link SequenceStepActionParameter}.
 */
@Service
@Transactional
public class SequenceStepActionParameterService {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionParameterService.class);

    private final SequenceStepActionParameterRepository sequenceStepActionParameterRepository;

    private final SequenceStepActionParameterMapper sequenceStepActionParameterMapper;

    public SequenceStepActionParameterService(SequenceStepActionParameterRepository sequenceStepActionParameterRepository, SequenceStepActionParameterMapper sequenceStepActionParameterMapper) {
        this.sequenceStepActionParameterRepository = sequenceStepActionParameterRepository;
        this.sequenceStepActionParameterMapper = sequenceStepActionParameterMapper;
    }

    /**
     * Save a sequenceStepActionParameter.
     *
     * @param sequenceStepActionParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceStepActionParameterDTO save(SequenceStepActionParameterDTO sequenceStepActionParameterDTO) {
        log.debug("Request to save SequenceStepActionParameter : {}", sequenceStepActionParameterDTO);
        SequenceStepActionParameter sequenceStepActionParameter = sequenceStepActionParameterMapper.toEntity(sequenceStepActionParameterDTO);
        sequenceStepActionParameter = sequenceStepActionParameterRepository.save(sequenceStepActionParameter);
        return sequenceStepActionParameterMapper.toDto(sequenceStepActionParameter);
    }

    /**
     * Get all the sequenceStepActionParameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceStepActionParameters");
        return sequenceStepActionParameterRepository.findAll(pageable)
            .map(sequenceStepActionParameterMapper::toDto);
    }

    /**
     * Get one sequenceStepActionParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceStepActionParameterDTO> findOne(Long id) {
        log.debug("Request to get SequenceStepActionParameter : {}", id);
        return sequenceStepActionParameterRepository.findById(id)
            .map(sequenceStepActionParameterMapper::toDto);
    }

    /**
     * Delete the sequenceStepActionParameter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceStepActionParameter : {}", id);
        sequenceStepActionParameterRepository.deleteById(id);
    }
    
    public static SequenceStepActionParameter clone(SequenceStepActionParameter input, SequenceStepAction parent, EntityManager contextualEntityManager) {
    	contextualEntityManager.detach(input);

    	input.setId(null);
    	input.setCreatedBy(null);
    	input.setCreatedDate(null);
        input.setLastModifiedBy(null);
        input.setLastModifiedDate(null);
        input.setAction(parent);

        return input;
    }
}
