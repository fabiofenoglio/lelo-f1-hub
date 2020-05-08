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

import it.fabiofenoglio.lelohub.domain.SequenceStep;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepMapper;

/**
 * Service for executing complex queries for {@link SequenceStep} entities in the database.
 * The main input is a {@link SequenceStepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepDTO} or a {@link Page} of {@link SequenceStepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepQueryService extends QueryService<SequenceStep> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepQueryService.class);

    private final SequenceStepRepository sequenceStepRepository;

    private final SequenceStepMapper sequenceStepMapper;

    public SequenceStepQueryService(SequenceStepRepository sequenceStepRepository, SequenceStepMapper sequenceStepMapper) {
        this.sequenceStepRepository = sequenceStepRepository;
        this.sequenceStepMapper = sequenceStepMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepDTO> findByCriteria(SequenceStepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStep> specification = createSpecification(criteria);
        return sequenceStepMapper.toDto(sequenceStepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepDTO> findByCriteria(SequenceStepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStep> specification = createSpecification(criteria);
        return sequenceStepRepository.findAll(specification, page)
            .map(sequenceStepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStep> specification = createSpecification(criteria);
        return sequenceStepRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStep> createSpecification(SequenceStepCriteria criteria) {
        Specification<SequenceStep> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStep_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStep_.generation));
            }
            if (criteria.getOrdinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdinal(), SequenceStep_.ordinal));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStep_.description));
            }
            if (criteria.getConditionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getConditionsId(),
                    root -> root.join(SequenceStep_.conditions, JoinType.LEFT).get(SequenceStepCondition_.id)));
            }
            if (criteria.getActionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getActionsId(),
                    root -> root.join(SequenceStep_.actions, JoinType.LEFT).get(SequenceStepAction_.id)));
            }
            if (criteria.getSequenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getSequenceId(),
                    root -> root.join(SequenceStep_.sequence, JoinType.LEFT).get(Sequence_.id)));
            }
        }
        return specification;
    }
}
