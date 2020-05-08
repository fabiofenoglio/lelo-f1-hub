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

import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionMapper;

/**
 * Service for executing complex queries for {@link SequenceStepCondition} entities in the database.
 * The main input is a {@link SequenceStepConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepConditionDTO} or a {@link Page} of {@link SequenceStepConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepConditionQueryService extends QueryService<SequenceStepCondition> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionQueryService.class);

    private final SequenceStepConditionRepository sequenceStepConditionRepository;

    private final SequenceStepConditionMapper sequenceStepConditionMapper;

    public SequenceStepConditionQueryService(SequenceStepConditionRepository sequenceStepConditionRepository, SequenceStepConditionMapper sequenceStepConditionMapper) {
        this.sequenceStepConditionRepository = sequenceStepConditionRepository;
        this.sequenceStepConditionMapper = sequenceStepConditionMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepConditionDTO> findByCriteria(SequenceStepConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepCondition> specification = createSpecification(criteria);
        return sequenceStepConditionMapper.toDto(sequenceStepConditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDTO> findByCriteria(SequenceStepConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepCondition> specification = createSpecification(criteria);
        return sequenceStepConditionRepository.findAll(specification, page)
            .map(sequenceStepConditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepCondition> specification = createSpecification(criteria);
        return sequenceStepConditionRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepConditionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepCondition> createSpecification(SequenceStepConditionCriteria criteria) {
        Specification<SequenceStepCondition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepCondition_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepCondition_.generation));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepCondition_.description));
            }
            if (criteria.getNegate() != null) {
                specification = specification.and(buildSpecification(criteria.getNegate(), SequenceStepCondition_.negate));
            }
            if (criteria.getParametersId() != null) {
                specification = specification.and(buildSpecification(criteria.getParametersId(),
                    root -> root.join(SequenceStepCondition_.parameters, JoinType.LEFT).get(SequenceStepConditionParameter_.id)));
            }
            if (criteria.getAndConditionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAndConditionsId(),
                    root -> root.join(SequenceStepCondition_.andConditions, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
            if (criteria.getOrConditionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrConditionsId(),
                    root -> root.join(SequenceStepCondition_.orConditions, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepCondition_.definition, JoinType.LEFT).get(SequenceStepConditionDefinition_.id)));
            }
            if (criteria.getStepId() != null) {
                specification = specification.and(buildSpecification(criteria.getStepId(),
                    root -> root.join(SequenceStepCondition_.step, JoinType.LEFT).get(SequenceStep_.id)));
            }
            if (criteria.getOtherAndConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getOtherAndConditionId(),
                    root -> root.join(SequenceStepCondition_.otherAndCondition, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
            if (criteria.getOtherOrConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getOtherOrConditionId(),
                    root -> root.join(SequenceStepCondition_.otherOrCondition, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
        }
        return specification;
    }
}
