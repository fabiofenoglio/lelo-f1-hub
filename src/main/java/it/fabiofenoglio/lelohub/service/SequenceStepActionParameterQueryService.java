package it.fabiofenoglio.lelohub.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepActionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionParameterMapper;

/**
 * Service for executing complex queries for {@link SequenceStepActionParameter} entities in the database.
 * The main input is a {@link SequenceStepActionParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepActionParameterDTO} or a {@link Page} of {@link SequenceStepActionParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepActionParameterQueryService extends QueryService<SequenceStepActionParameter> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionParameterQueryService.class);

    private final SequenceStepActionParameterRepository sequenceStepActionParameterRepository;

    private final SequenceStepActionParameterMapper sequenceStepActionParameterMapper;

    public SequenceStepActionParameterQueryService(SequenceStepActionParameterRepository sequenceStepActionParameterRepository, SequenceStepActionParameterMapper sequenceStepActionParameterMapper) {
        this.sequenceStepActionParameterRepository = sequenceStepActionParameterRepository;
        this.sequenceStepActionParameterMapper = sequenceStepActionParameterMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepActionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepActionParameterDTO> findByCriteria(SequenceStepActionParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepActionParameter> specification = createSpecification(criteria);
        return sequenceStepActionParameterMapper.toDto(sequenceStepActionParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepActionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionParameterDTO> findByCriteria(SequenceStepActionParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepActionParameter> specification = createSpecification(criteria);
        return sequenceStepActionParameterRepository.findAll(specification, page)
            .map(sequenceStepActionParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepActionParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepActionParameter> specification = createSpecification(criteria);
        return sequenceStepActionParameterRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepActionParameterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepActionParameter> createSpecification(SequenceStepActionParameterCriteria criteria) {
        Specification<SequenceStepActionParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepActionParameter_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepActionParameter_.generation));
            }
            if (criteria.getValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueString(), SequenceStepActionParameter_.valueString));
            }
            if (criteria.getValueNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueNumber(), SequenceStepActionParameter_.valueNumber));
            }
            if (criteria.getValueBoolean() != null) {
                specification = specification.and(buildSpecification(criteria.getValueBoolean(), SequenceStepActionParameter_.valueBoolean));
            }
            if (criteria.getValueVariable() != null) {
                specification = specification.and(buildSpecification(criteria.getValueVariable(), SequenceStepActionParameter_.valueVariable));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepActionParameter_.definition, JoinType.LEFT).get(SequenceStepActionDefinitionParameter_.id)));
            }
            if (criteria.getActionId() != null) {
                specification = specification.and(buildSpecification(criteria.getActionId(),
                    root -> root.join(SequenceStepActionParameter_.action, JoinType.LEFT).get(SequenceStepAction_.id)));
            }
        }
        return specification;
    }
}
