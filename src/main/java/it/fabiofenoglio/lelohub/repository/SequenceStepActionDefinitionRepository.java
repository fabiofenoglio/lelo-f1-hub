package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepActionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepActionDefinitionRepository extends JpaRepository<SequenceStepActionDefinition, Long>, JpaSpecificationExecutor<SequenceStepActionDefinition> {
}
