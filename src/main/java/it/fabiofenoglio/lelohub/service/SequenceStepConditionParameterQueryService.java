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

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionParameterMapper;

/**
 * Service for executing complex queries for {@link SequenceStepConditionParameter} entities in the database.
 * The main input is a {@link SequenceStepConditionParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepConditionParameterDTO} or a {@link Page} of {@link SequenceStepConditionParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepConditionParameterQueryService extends QueryService<SequenceStepConditionParameter> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionParameterQueryService.class);

    private final SequenceStepConditionParameterRepository sequenceStepConditionParameterRepository;

    private final SequenceStepConditionParameterMapper sequenceStepConditionParameterMapper;

    public SequenceStepConditionParameterQueryService(SequenceStepConditionParameterRepository sequenceStepConditionParameterRepository, SequenceStepConditionParameterMapper sequenceStepConditionParameterMapper) {
        this.sequenceStepConditionParameterRepository = sequenceStepConditionParameterRepository;
        this.sequenceStepConditionParameterMapper = sequenceStepConditionParameterMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepConditionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepConditionParameterDTO> findByCriteria(SequenceStepConditionParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepConditionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionParameterMapper.toDto(sequenceStepConditionParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepConditionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionParameterDTO> findByCriteria(SequenceStepConditionParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepConditionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionParameterRepository.findAll(specification, page)
            .map(sequenceStepConditionParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepConditionParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepConditionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionParameterRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepConditionParameterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepConditionParameter> createSpecification(SequenceStepConditionParameterCriteria criteria) {
        Specification<SequenceStepConditionParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepConditionParameter_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepConditionParameter_.generation));
            }
            if (criteria.getValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueString(), SequenceStepConditionParameter_.valueString));
            }
            if (criteria.getValueNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueNumber(), SequenceStepConditionParameter_.valueNumber));
            }
            if (criteria.getValueBoolean() != null) {
                specification = specification.and(buildSpecification(criteria.getValueBoolean(), SequenceStepConditionParameter_.valueBoolean));
            }
            if (criteria.getValueVariable() != null) {
                specification = specification.and(buildSpecification(criteria.getValueVariable(), SequenceStepConditionParameter_.valueVariable));
            }
            if (criteria.getValueOperator() != null) {
                specification = specification.and(buildSpecification(criteria.getValueOperator(), SequenceStepConditionParameter_.valueOperator));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepConditionParameter_.definition, JoinType.LEFT).get(SequenceStepConditionDefinitionParameter_.id)));
            }
            if (criteria.getConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getConditionId(),
                    root -> root.join(SequenceStepConditionParameter_.condition, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
        }
        return specification;
    }
}
