package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepActionDefinitionParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepActionDefinitionParameterRepository extends JpaRepository<SequenceStepActionDefinitionParameter, Long>, JpaSpecificationExecutor<SequenceStepActionDefinitionParameter> {
}
