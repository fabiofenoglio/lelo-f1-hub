package it.fabiofenoglio.lelohub.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.JoinType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import it.fabiofenoglio.lelohub.domain.Sequence;
import it.fabiofenoglio.lelohub.domain.SequenceStep;
import it.fabiofenoglio.lelohub.domain.SequenceStepAction;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStep_;
import it.fabiofenoglio.lelohub.domain.Sequence_;
import it.fabiofenoglio.lelohub.domain.User;
import it.fabiofenoglio.lelohub.domain.User_;
import it.fabiofenoglio.lelohub.domain.enumeration.ObjectAccessAuthorization;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;
import it.fabiofenoglio.lelohub.repository.SequenceRepository;
import it.fabiofenoglio.lelohub.repository.UserRepository;
import it.fabiofenoglio.lelohub.security.SecurityUtils;
import it.fabiofenoglio.lelohub.service.dto.SequenceCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceListDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterUpdateDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionUpdateDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterUpdateDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionUpdateDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepUpdateDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUpdateDTO;
import it.fabiofenoglio.lelohub.service.helper.NestedEntityComparator;
import it.fabiofenoglio.lelohub.service.mapper.SequenceMapper;
import it.fabiofenoglio.lelohub.web.rest.errors.ForbiddenException;
import it.fabiofenoglio.lelohub.web.rest.errors.NotFoundException;

/**
 * Service Implementation for managing {@link Sequence}.
 */
@Service
@Transactional
public class SequenceService extends QueryService<Sequence>  {

    private final Logger log = LoggerFactory.getLogger(SequenceService.class);

    private final UserRepository userRepository;

    private final SequenceRepository sequenceRepository;

    private final SequenceMapper sequenceMapper;
    
    @PersistenceContext
    private EntityManager entityManager;

    public SequenceService(SequenceRepository sequenceRepository, SequenceMapper sequenceMapper, UserRepository userRepository) {
        this.sequenceRepository = sequenceRepository;
        this.sequenceMapper = sequenceMapper;
        this.userRepository = userRepository;
    }

    public Set<ObjectAccessAuthorization> getUserAuthorizations(Sequence sequence, String user) {
    	Set<ObjectAccessAuthorization> output = new HashSet<>();
    	if (sequence == null) {
    		return output;
    	}

    	if (sequence.getUser() != null) {
    		if (user != null && sequence.getUser().getLogin().equals(user)) {
    			output.add(ObjectAccessAuthorization.OWNER);
    			output.add(ObjectAccessAuthorization.WRITE);
    			output.add(ObjectAccessAuthorization.READ);
    		} else if (SequenceVisibility.PUBLIC.equals(sequence.getVisibility())) {
    			output.add(ObjectAccessAuthorization.READ);
    		}
    	} else {
    		output.add(ObjectAccessAuthorization.READ);
    	}
    	
    	return output;
    }

    public boolean checkAuthorization(Sequence sequence, ObjectAccessAuthorization authorization, String user) {
    	return this.getUserAuthorizations(sequence, user).contains(authorization);
    }

    public void assertAuthorization(Sequence sequence, ObjectAccessAuthorization authorization, String user) {
    	if (!this.checkAuthorization(sequence, authorization, user)) {
    		throw new ForbiddenException();
    	}
    }

    public void assertAuthorization(Long id, ObjectAccessAuthorization authorization) {
    	this.assertAuthorization(sequenceRepository.findById(id).orElse(null), authorization);
    }
    
    public void assertAuthorization(Sequence sequence, ObjectAccessAuthorization authorization) {
    	if (!this.checkAuthorization(sequence, authorization, SecurityUtils.getCurrentUserLogin().orElse(null))) {
    		throw new ForbiddenException();
    	}
    }
    
    /**
     * Save a sequence.
     *
     * @param sequenceDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceDTO create(SequenceUpdateDTO sequenceDTO) {
        log.debug("Request to create Sequence : {}", sequenceDTO);
		User currentUser = userRepository.findOneByLogin(SecurityUtils.requireCurrentLogin())
			.orElseThrow(ForbiddenException::new);

        Sequence entity = new Sequence();
        entity.setUser(currentUser);
        updateSequenceFromDTO(entity, sequenceDTO);
        
        entity = sequenceRepository.save(entity);
        
        return toDto(entity, currentUser.getLogin());
    }

    public SequenceDTO update(SequenceUpdateDTO sequenceDTO) {
        log.debug("Request to create Sequence : {}", sequenceDTO);

		Sequence existing = sequenceRepository.findById(sequenceDTO.getId())
			.orElseThrow(NotFoundException::new); 

        this.assertAuthorization(existing, ObjectAccessAuthorization.WRITE);
        
        updateSequenceFromDTO(existing, sequenceDTO);

        existing = sequenceRepository.save(existing);
        
        return toDto(existing, SecurityUtils.requireCurrentLogin());
    }
    
    private Sequence updateSequenceFromDTO(Sequence sequence, SequenceUpdateDTO input) {

    	sequence.setName(input.getName());
    	sequence.setVisibility(input.getVisibility() != null ? input.getVisibility() : SequenceVisibility.PRIVATE);
    	sequence.setDescription(input.getDescription());

    	NestedEntityComparator.compareSets(sequence.getSteps(), input.getSteps())
    	.onElementsInBoth(this::updateStepFromDTO)
    	.onElementsInFirstNotInSecond(sequence::removeSteps)
    	.onElementsInSecondNotInFirst(inputStep -> {
    		SequenceStep newEntity = new SequenceStep();
    		sequence.addSteps(newEntity);
    		updateStepFromDTO(newEntity, inputStep);
    	});
    	
    	return sequence;
    }

    private void updateStepFromDTO(SequenceStep existing, SequenceStepUpdateDTO input) {
    	existing.setDescription(input.getDescription());
    	existing.setOrdinal(input.getOrdinal());

    	NestedEntityComparator.compareSets(existing.getConditions(), input.getConditions())
    	.onElementsInBoth(this::updateStepConditionFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeConditions)
    	.onElementsInSecondNotInFirst(inputCondition -> {
    		SequenceStepCondition newEntity = new SequenceStepCondition();
    		existing.addConditions(newEntity);
    		updateStepConditionFromDTO(newEntity, inputCondition);
    	});
    	
    	NestedEntityComparator.compareSets(existing.getActions(), input.getActions())
    	.onElementsInBoth(this::updateStepActionFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeActions)
    	.onElementsInSecondNotInFirst(inputCondition -> {
    		SequenceStepAction newEntity = new SequenceStepAction();
    		existing.addActions(newEntity);
    		updateStepActionFromDTO(newEntity, inputCondition);
    	});
    }

    private void updateStepConditionFromDTO(SequenceStepCondition existing, SequenceStepConditionUpdateDTO input) {
    	existing.setDescription(input.getDescription());
    	existing.setDefinition(SequenceStepConditionDefinition.fromId(input.getDefinitionId()));
    	existing.setNegate(input.isNegate());

    	NestedEntityComparator.compareSets(existing.getAndConditions(), input.getAndConditions())
    	.onElementsInBoth(this::updateStepConditionFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeAndConditions)
    	.onElementsInSecondNotInFirst(inputCondition -> {
    		SequenceStepCondition newEntity = new SequenceStepCondition();
    		existing.addAndConditions(newEntity);
    		updateStepConditionFromDTO(newEntity, inputCondition);
    	});
    	
    	NestedEntityComparator.compareSets(existing.getOrConditions(), input.getOrConditions())
    	.onElementsInBoth(this::updateStepConditionFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeOrConditions)
    	.onElementsInSecondNotInFirst(inputCondition -> {
    		SequenceStepCondition newEntity = new SequenceStepCondition();
    		existing.addOrConditions(newEntity);
    		updateStepConditionFromDTO(newEntity, inputCondition);
    	});

    	NestedEntityComparator.compareSets(existing.getParameters(), input.getParameters())
    	.onElementsInBoth(this::updateStepConditionParameterFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeParameters)
    	.onElementsInSecondNotInFirst(inputCondition -> {
    		SequenceStepConditionParameter newEntity = new SequenceStepConditionParameter();
    		existing.addParameters(newEntity);
    		updateStepConditionParameterFromDTO(newEntity, inputCondition);
    	});
    }

    private void updateStepConditionParameterFromDTO(SequenceStepConditionParameter existing, SequenceStepConditionParameterUpdateDTO input) {
    	existing.setDefinition(SequenceStepConditionDefinitionParameter.fromId(input.getDefinitionId()));
    	existing.setValueBoolean(input.isValueBoolean());
    	existing.setValueNumber(input.getValueNumber());
    	existing.setValueOperator(input.getValueOperator());
    	existing.setValueString(input.getValueString());
    	existing.setValueVariable(input.getValueVariable());
    }

    private void updateStepActionFromDTO(SequenceStepAction existing, SequenceStepActionUpdateDTO input) {
    	existing.setDescription(input.getDescription());
    	existing.setDefinition(SequenceStepActionDefinition.fromId(input.getDefinitionId()));
    	existing.setOrdinal(input.getOrdinal());

    	NestedEntityComparator.compareSets(existing.getParameters(), input.getParameters())
    	.onElementsInBoth(this::updateStepActionParameterFromDTO)
    	.onElementsInFirstNotInSecond(existing::removeParameters)
    	.onElementsInSecondNotInFirst(inputAction -> {
    		SequenceStepActionParameter newEntity = new SequenceStepActionParameter();
    		existing.addParameters(newEntity);
    		updateStepActionParameterFromDTO(newEntity, inputAction);
    	});
    }

    private void updateStepActionParameterFromDTO(SequenceStepActionParameter existing, SequenceStepActionParameterUpdateDTO input) {
    	existing.setDefinition(SequenceStepActionDefinitionParameter.fromId(input.getDefinitionId()));
    	existing.setValueBoolean(input.isValueBoolean());
    	existing.setValueNumber(input.getValueNumber());
    	existing.setValueString(input.getValueString());
    	existing.setValueVariable(input.getValueVariable());
    }

    /**
     * Get all the sequences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sequences");
        String login = SecurityUtils.requireCurrentLogin();
        
        return sequenceRepository.findAll(pageable).map(o -> toDto(o, login));
    }

    /**
     * Get one sequence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceDTO> findOne(Long id) {
        log.debug("Request to get Sequence : {}", id);
        Sequence entity = sequenceRepository.findOneById(id);
        if (entity == null) {
        	return Optional.empty();
        }

        assertAuthorization(entity, ObjectAccessAuthorization.READ);
		return Optional.of(toDto(entity, SecurityUtils.requireCurrentLogin()));
    }

    /**
     * Delete the sequence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sequence : {}", id);
        this.assertAuthorization(id, ObjectAccessAuthorization.OWNER);
        sequenceRepository.deleteById(id);
    }
    
    @Transactional
    public SequenceDTO clone(Long id, SequenceUpdateDTO withPayload) {
        log.debug("Request to clone Sequence : {}", id);
        Sequence existing = sequenceRepository.findOneById(id);
        
		User currentUser = userRepository.findOneByLogin(SecurityUtils.requireCurrentLogin())
				.orElseThrow(ForbiddenException::new);

        assertAuthorization(existing, ObjectAccessAuthorization.READ);
        
        Sequence cloned = existing;
        entityManager.detach(cloned);
        
        cloned.setId(null);
        cloned.setCreatedBy(null);
        cloned.setCreatedDate(null);
        cloned.setLastModifiedBy(null);
        cloned.setLastModifiedDate(null);
        cloned.setName(existing.getName());
        cloned.setUser(currentUser);
        cloned.setVisibility(existing.getVisibility());
        
        cloned.setSteps(existing.getSteps().stream()
        		.map(o -> SequenceStepService.clone(o, cloned, entityManager))
        		.collect(Collectors.toSet()) );

        Sequence saved = sequenceRepository.save(cloned);
        
        if (withPayload != null && withPayload.getName() != null) {
        	withPayload.setId(saved.getId());
        	return update(withPayload);
        }
        
        return toDto(saved, currentUser.getLogin());
    }

    /**
     * Return a {@link List} of {@link SequenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceListDTO> findByCriteria(SequenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        String login = SecurityUtils.requireCurrentLogin();
        
        final Specification<Sequence> specification = createSpecification(criteria, buildVisibilityCriteria());
        return sequenceRepository.findAll(specification).stream()
        		.map(o -> this.toListDto(o, login))
				.collect(Collectors.toList());
    }

    /**
     * Return a {@link Page} of {@link SequenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceListDTO> findByCriteria(SequenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        String login = SecurityUtils.requireCurrentLogin();
        final Specification<Sequence> specification = createSpecification(criteria, buildVisibilityCriteria());
        
        return sequenceRepository.findAll(specification, page)
            .map(o -> this.toListDto(o, login));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sequence> specification = createSpecification(criteria, buildVisibilityCriteria());
        return sequenceRepository.count(specification);
    }
    
    private SequenceDTO toDto(Sequence sequence, String user) {
    	SequenceDTO dto = sequenceMapper.toDto(sequence);
    	dto.getAuthorizations().clear();
    	this.getUserAuthorizations(sequence, user).forEach(auth -> dto.getAuthorizations().put(auth, true));
    	return dto;
    }
    
    private SequenceListDTO toListDto(Sequence sequence, String user) {
    	SequenceListDTO dto = sequenceMapper.toListDto(sequence);
    	dto.getAuthorizations().clear();
    	this.getUserAuthorizations(sequence, user).forEach(auth -> dto.getAuthorizations().put(auth, true));
    	return dto;
    }

    private SequenceCriteria buildVisibilityCriteria() {
    	String userLogin = SecurityUtils.requireCurrentLogin();
    	SequenceCriteria output = new SequenceCriteria();
		output.setVisibleToUser(userLogin);
    	return output;
    }

    /**
     * Function to convert {@link SequenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sequence> createSpecification(SequenceCriteria... criterias) {
        Specification<Sequence> specification = Specification.where(null);
        for (SequenceCriteria criteria: criterias) {
	        if (criteria != null) {
	            if (criteria.getId() != null) {
	                specification = specification.and(buildRangeSpecification(criteria.getId(), Sequence_.id));
	            }
	            if (criteria.getGeneration() != null) {
	                specification = specification.and(buildSpecification(criteria.getGeneration(), Sequence_.generation));
	            }
	            if (criteria.getName() != null) {
	                specification = specification.and(buildStringSpecification(criteria.getName(), Sequence_.name));
	            }
	            if (criteria.getDescription() != null) {
	                specification = specification.and(buildStringSpecification(criteria.getDescription(), Sequence_.description));
	            }
	            if (criteria.getVisibility() != null) {
	                specification = specification.and(buildSpecification(criteria.getVisibility(), Sequence_.visibility));
	            }
	            if (criteria.getStepsId() != null) {
	                specification = specification.and(buildSpecification(criteria.getStepsId(),
	                    root -> root.join(Sequence_.steps, JoinType.LEFT).get(SequenceStep_.id)));
	            }
	            if (criteria.getUserId() != null) {
	                specification = specification.and(buildSpecification(criteria.getUserId(),
	                    root -> root.join(Sequence_.user, JoinType.LEFT).get(User_.id)));
	            }
	            if (criteria.getUserLogin() != null) {
	                specification = specification.and(buildSpecification(criteria.getUserLogin(),
	                    root -> root.join(Sequence_.user, JoinType.LEFT).get(User_.login)));
	            }
	            if (criteria.getOwn() != null && criteria.getOwn()) {
	            	specification = specification.and((root, query, builder) -> {
	            		return builder.equal(root.join(Sequence_.user, JoinType.LEFT).get(User_.login), SecurityUtils.requireCurrentLogin());
	            	});
	            }
	            if (criteria.getShared() != null && criteria.getShared()) {
	            	specification = specification.and((root, query, builder) -> {
	            		return builder.notEqual(root.join(Sequence_.user, JoinType.LEFT).get(User_.login), SecurityUtils.requireCurrentLogin());
	            	});
	            }
	            if (criteria.getVisibleToUser() != null) {
	            	specification = specification.and((root, query, builder) -> {
	            		return builder.or(
	            				builder.equal(root.join(Sequence_.user, JoinType.LEFT).get(User_.login), criteria.getVisibleToUser()),
	            				builder.equal(root.get(Sequence_.visibility), SequenceVisibility.PUBLIC)
            				);
	            	});
	            }
	            if (!StringUtils.isBlank(criteria.getFullTextSearch())) {
	            	String searchTerm = "%" + criteria.getFullTextSearch().trim().toLowerCase() + "%";
	            	specification = specification.and((root, query, builder) -> {
	            		return builder.or(
	            				builder.like(builder.lower(root.get(Sequence_.name)), searchTerm),
	            				builder.like(builder.lower(root.get(Sequence_.description)), searchTerm),
	            				builder.like(root.join(Sequence_.user, JoinType.LEFT).get(User_.login), searchTerm)
            				);
	            	});
	            }
	        }
        }
        return specification;
    }

}
