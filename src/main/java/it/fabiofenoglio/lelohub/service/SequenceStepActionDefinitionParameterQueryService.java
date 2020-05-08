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

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionParameterMapper;

/**
 * Service for executing complex queries for {@link SequenceStepActionDefinitionParameter} entities in the database.
 * The main input is a {@link SequenceStepActionDefinitionParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepActionDefinitionParameterDTO} or a {@link Page} of {@link SequenceStepActionDefinitionParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepActionDefinitionParameterQueryService extends QueryService<SequenceStepActionDefinitionParameter> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionParameterQueryService.class);

    private final SequenceStepActionDefinitionParameterRepository sequenceStepActionDefinitionParameterRepository;

    private final SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper;

    public SequenceStepActionDefinitionParameterQueryService(SequenceStepActionDefinitionParameterRepository sequenceStepActionDefinitionParameterRepository, SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper) {
        this.sequenceStepActionDefinitionParameterRepository = sequenceStepActionDefinitionParameterRepository;
        this.sequenceStepActionDefinitionParameterMapper = sequenceStepActionDefinitionParameterMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepActionDefinitionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepActionDefinitionParameterDTO> findByCriteria(SequenceStepActionDefinitionParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepActionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepActionDefinitionParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDefinitionParameterDTO> findByCriteria(SequenceStepActionDefinitionParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepActionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionParameterRepository.findAll(specification, page)
            .map(sequenceStepActionDefinitionParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepActionDefinitionParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepActionDefinitionParameter> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionParameterRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepActionDefinitionParameterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepActionDefinitionParameter> createSpecification(SequenceStepActionDefinitionParameterCriteria criteria) {
        Specification<SequenceStepActionDefinitionParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepActionDefinitionParameter_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepActionDefinitionParameter_.generation));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SequenceStepActionDefinitionParameter_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SequenceStepActionDefinitionParameter_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepActionDefinitionParameter_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SequenceStepActionDefinitionParameter_.type));
            }
            if (criteria.getEvaluationEngine() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationEngine(), SequenceStepActionDefinitionParameter_.evaluationEngine));
            }
            if (criteria.getMinValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinValue(), SequenceStepActionDefinitionParameter_.minValue));
            }
            if (criteria.getMaxValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxValue(), SequenceStepActionDefinitionParameter_.maxValue));
            }
            if (criteria.getStepValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStepValue(), SequenceStepActionDefinitionParameter_.stepValue));
            }
            if (criteria.getMinLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinLength(), SequenceStepActionDefinitionParameter_.minLength));
            }
            if (criteria.getMaxLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLength(), SequenceStepActionDefinitionParameter_.maxLength));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepActionDefinitionParameter_.definition, JoinType.LEFT).get(SequenceStepActionDefinition_.id)));
            }
        }
        return specification;
    }
}
