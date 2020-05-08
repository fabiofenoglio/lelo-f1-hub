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

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionMapper;

/**
 * Service for executing complex queries for {@link SequenceStepActionDefinition} entities in the database.
 * The main input is a {@link SequenceStepActionDefinitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceStepActionDefinitionDTO} or a {@link Page} of {@link SequenceStepActionDefinitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceStepActionDefinitionQueryService extends QueryService<SequenceStepActionDefinition> {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionQueryService.class);

    private final SequenceStepActionDefinitionRepository sequenceStepActionDefinitionRepository;

    private final SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper;

    public SequenceStepActionDefinitionQueryService(SequenceStepActionDefinitionRepository sequenceStepActionDefinitionRepository, SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper) {
        this.sequenceStepActionDefinitionRepository = sequenceStepActionDefinitionRepository;
        this.sequenceStepActionDefinitionMapper = sequenceStepActionDefinitionMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceStepActionDefinitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceStepActionDefinitionDTO> findByCriteria(SequenceStepActionDefinitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceStepActionDefinition> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceStepActionDefinitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceStepActionDefinitionDTO> findByCriteria(SequenceStepActionDefinitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceStepActionDefinition> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionRepository.findAll(specification, page)
            .map(sequenceStepActionDefinitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceStepActionDefinitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceStepActionDefinition> specification = createSpecification(criteria);
        return sequenceStepActionDefinitionRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceStepActionDefinitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceStepActionDefinition> createSpecification(SequenceStepActionDefinitionCriteria criteria) {
        Specification<SequenceStepActionDefinition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceStepActionDefinition_.id));
            }
            if (criteria.getGeneration() != null) {
                specification = specification.and(buildSpecification(criteria.getGeneration(), SequenceStepActionDefinition_.generation));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SequenceStepActionDefinition_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SequenceStepActionDefinition_.description));
            }
            if (criteria.getParametersId() != null) {
                specification = specification.and(buildSpecification(criteria.getParametersId(),
                    root -> root.join(SequenceStepActionDefinition_.parameters, JoinType.LEFT).get(SequenceStepActionDefinitionParameter_.id)));
            }
        }
        return specification;
    }
}
