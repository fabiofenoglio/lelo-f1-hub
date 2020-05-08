package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepConditionRepository extends JpaRepository<SequenceStepCondition, Long>, JpaSpecificationExecutor<SequenceStepCondition> {
}
