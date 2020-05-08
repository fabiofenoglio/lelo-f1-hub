package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepConditionParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepConditionParameterRepository extends JpaRepository<SequenceStepConditionParameter, Long>, JpaSpecificationExecutor<SequenceStepConditionParameter> {
}
