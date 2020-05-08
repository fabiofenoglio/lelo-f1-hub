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

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionParameterMapper;

/**
 * Service for executing complex queries for {@link SequenceStepConditionDefinitionParameter} entities in the database.
 * The main input is a {@link SequenceStepConditionDefinitionParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepConditionDefinitionParameterDTO} or a {@link Page} of {@link SequenceStepConditionDefinitionParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepConditionDefinitionParameterQueryService extends QueryService<SequenceStepConditionDefinitionParameter> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionParameterQueryService.class);

    private final SequenceStepConditionDefinitionParameterRepository sequenceStepConditionDefinitionParameterRepository;

    private final SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper;

    public SequenceStepConditionDefinitionParameterQueryService(SequenceStepConditionDefinitionParameterRepository sequenceStepConditionDefinitionParameterRepository, SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper) {
        this.sequenceStepConditionDefinitionParameterRepository = sequenceStepConditionDefinitionParameterRepository;
        this.sequenceStepConditionDefinitionParameterMapper = sequenceStepConditionDefinitionParameterMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepConditionDefinitionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepConditionDefinitionParameterDTO> findByCriteria(SequenceStepConditionDefinitionParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepConditionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepConditionDefinitionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDefinitionParameterDTO> findByCriteria(SequenceStepConditionDefinitionParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepConditionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionParameterRepository.findAll(specification, page)
            .map(sequenceStepConditionDefinitionParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepConditionDefinitionParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepConditionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionParameterRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepConditionDefinitionParameterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepConditionDefinitionParameter> createSpecification(SequenceStepConditionDefinitionParameterCriteria criteria) {
        Specification<SequenceStepConditionDefinitionParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepConditionDefinitionParameter_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepConditionDefinitionParameter_.generation));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SequenceStepConditionDefinitionParameter_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SequenceStepConditionDefinitionParameter_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepConditionDefinitionParameter_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SequenceStepConditionDefinitionParameter_.type));
            }
            if (criteria.getEvaluationEngine() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationEngine(), SequenceStepConditionDefinitionParameter_.evaluationEngine));
            }
            if (criteria.getMinValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinValue(), SequenceStepConditionDefinitionParameter_.minValue));
            }
            if (criteria.getMaxValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxValue(), SequenceStepConditionDefinitionParameter_.maxValue));
            }
            if (criteria.getStepValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStepValue(), SequenceStepConditionDefinitionParameter_.stepValue));
            }
            if (criteria.getMinLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinLength(), SequenceStepConditionDefinitionParameter_.minLength));
            }
            if (criteria.getMaxLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLength(), SequenceStepConditionDefinitionParameter_.maxLength));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepConditionDefinitionParameter_.definition, JoinType.LEFT).get(SequenceStepConditionDefinition_.id)));
            }
        }
        return specification;
    }
}
