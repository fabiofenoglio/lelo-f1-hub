package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepConditionDefinitionParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepConditionDefinitionParameterRepository extends JpaRepository<SequenceStepConditionDefinitionParameter, Long>, JpaSpecificationExecutor<SequenceStepConditionDefinitionParameter> {
}
