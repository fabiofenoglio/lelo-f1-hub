package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepActionParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepActionParameterRepository extends JpaRepository<SequenceStepActionParameter, Long>, JpaSpecificationExecutor<SequenceStepActionParameter> {
}
