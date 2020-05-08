package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.SequenceStep;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SequenceStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceStepRepository extends JpaRepository<SequenceStep, Long>, JpaSpecificationExecutor<SequenceStep> {
}
