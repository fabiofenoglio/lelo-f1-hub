package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStepAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStepAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepActionRepository extends JpaRepository<SequenceStepAction, Long>, JpaSpecificationExecutor<SequenceStepAction> {
}
