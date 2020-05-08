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

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionMapper;

/**
 * Service for executing complex queries for {@link SequenceStepConditionDefinition} entities in the database.
 * The main input is a {@link SequenceStepConditionDefinitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepConditionDefinitionDTO} or a {@link Page} of {@link SequenceStepConditionDefinitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepConditionDefinitionQueryService extends QueryService<SequenceStepConditionDefinition> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionQueryService.class);

    private final SequenceStepConditionDefinitionRepository sequenceStepConditionDefinitionRepository;

    private final SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper;

    public SequenceStepConditionDefinitionQueryService(SequenceStepConditionDefinitionRepository sequenceStepConditionDefinitionRepository, SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper) {
        this.sequenceStepConditionDefinitionRepository = sequenceStepConditionDefinitionRepository;
        this.sequenceStepConditionDefinitionMapper = sequenceStepConditionDefinitionMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepConditionDefinitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepConditionDefinitionDTO> findByCriteria(SequenceStepConditionDefinitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepConditionDefinition> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepConditionDefinitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepConditionDefinitionDTO> findByCriteria(SequenceStepConditionDefinitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepConditionDefinition> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionRepository.findAll(specification, page)
            .map(sequenceStepConditionDefinitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepConditionDefinitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepConditionDefinition> specification = createSpecification(criteria);
        return sequenceStepConditionDefinitionRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepConditionDefinitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepConditionDefinition> createSpecification(SequenceStepConditionDefinitionCriteria criteria) {
        Specification<SequenceStepConditionDefinition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepConditionDefinition_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepConditionDefinition_.generation));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SequenceStepConditionDefinition_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepConditionDefinition_.description));
            }
            if (criteria.getParametersId() != null) {
                specification = specification.and(buildSpecification(criteria.getParametersId(),
                    root -> root.join(SequenceStepConditionDefinition_.parameters, JoinType.LEFT).get(SequenceStepConditionDefinitionParameter_.id)));
            }
        }
        return specification;
    }
}
