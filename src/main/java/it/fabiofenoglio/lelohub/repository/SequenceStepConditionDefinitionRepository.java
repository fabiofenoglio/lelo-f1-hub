package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepConditionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepConditionDefinitionRepository extends JpaRepository<SequenceStepConditionDefinition, Long>, JpaSpecificationExecutor<SequenceStepConditionDefinition> {
}
