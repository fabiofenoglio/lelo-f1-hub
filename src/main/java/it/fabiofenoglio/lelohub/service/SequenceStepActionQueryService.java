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

import it.fabiofenoglio.lelohub.domain.SequenceStepAction;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepActionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionMapper;

/**
 * Service for executing complex queries for {@link SequenceStepAction} entities in the database.
 * The main input is a {@link SequenceStepActionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepActionDTO} or a {@link Page} of {@link SequenceStepActionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepActionQueryService extends QueryService<SequenceStepAction> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionQueryService.class);

    private final SequenceStepActionRepository sequenceStepActionRepository;

    private final SequenceStepActionMapper sequenceStepActionMapper;

    public SequenceStepActionQueryService(SequenceStepActionRepository sequenceStepActionRepository, SequenceStepActionMapper sequenceStepActionMapper) {
        this.sequenceStepActionRepository = sequenceStepActionRepository;
        this.sequenceStepActionMapper = sequenceStepActionMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepActionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepActionDTO> findByCriteria(SequenceStepActionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepAction> specification = createSpecification(criteria);
        return sequenceStepActionMapper.toDto(sequenceStepActionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepActionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDTO> findByCriteria(SequenceStepActionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepAction> specification = createSpecification(criteria);
        return sequenceStepActionRepository.findAll(specification, page)
            .map(sequenceStepActionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepActionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepAction> specification = createSpecification(criteria);
        return sequenceStepActionRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepActionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepAction> createSpecification(SequenceStepActionCriteria criteria) {
        Specification<SequenceStepAction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepAction_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepAction_.generation));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepAction_.description));
            }
            if (criteria.getParametersId() != null) {
                specification = specification.and(buildSpecification(criteria.getParametersId(),
                    root -> root.join(SequenceStepAction_.parameters, JoinType.LEFT).get(SequenceStepActionParameter_.id)));
            }
            if (criteria.getDefinitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefinitionId(),
                    root -> root.join(SequenceStepAction_.definition, JoinType.LEFT).get(SequenceStepActionDefinition_.id)));
            }
            if (criteria.getStepId() != null) {
                specification = specification.and(buildSpecification(criteria.getStepId(),
                    root -> root.join(SequenceStepAction_.step, JoinType.LEFT).get(SequenceStep_.id)));
            }
        }
        return specification;
    }
}
