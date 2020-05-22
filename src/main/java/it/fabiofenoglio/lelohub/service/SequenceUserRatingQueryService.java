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

import it.fabiofenoglio.lelohub.domain.SequenceUserRating;
import it.fabiofenoglio.lelohub.domain.*; // for static metamodels
import it.fabiofenoglio.lelohub.repository.SequenceUserRatingRepository;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceUserRatingMapper;

/**
 * Service for executing complex queries for {@link SequenceUserRating} entities in the database.
 * The main input is a {@link SequenceUserRatingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceUserRatingDTO} or a {@link Page} of {@link SequenceUserRatingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceUserRatingQueryService extends QueryService<SequenceUserRating> {

    private final Logger log = LoggerFactory.getLogger(SequenceUserRatingQueryService.class);

    private final SequenceUserRatingRepository sequenceUserRatingRepository;

    private final SequenceUserRatingMapper sequenceUserRatingMapper;

    public SequenceUserRatingQueryService(SequenceUserRatingRepository sequenceUserRatingRepository, SequenceUserRatingMapper sequenceUserRatingMapper) {
        this.sequenceUserRatingRepository = sequenceUserRatingRepository;
        this.sequenceUserRatingMapper = sequenceUserRatingMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceUserRatingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceUserRatingDTO> findByCriteria(SequenceUserRatingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceUserRating> specification = createSpecification(criteria);
        return sequenceUserRatingMapper.toDto(sequenceUserRatingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceUserRatingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceUserRatingDTO> findByCriteria(SequenceUserRatingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceUserRating> specification = createSpecification(criteria);
        return sequenceUserRatingRepository.findAll(specification, page)
            .map(sequenceUserRatingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceUserRatingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceUserRating> specification = createSpecification(criteria);
        return sequenceUserRatingRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceUserRatingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceUserRating> createSpecification(SequenceUserRatingCriteria criteria) {
        Specification<SequenceUserRating> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceUserRating_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SequenceUserRating_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SequenceUserRating_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SequenceUserRating_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SequenceUserRating_.lastModifiedBy));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), SequenceUserRating_.score));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(SequenceUserRating_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getSequenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getSequenceId(),
                    root -> root.join(SequenceUserRating_.sequence, JoinType.LEFT).get(Sequence_.id)));
            }
        }
        return specification;
    }
}
