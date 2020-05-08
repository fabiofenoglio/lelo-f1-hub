package it.fabiofenoglio.lelohub.repository;

import it.fabiofenoglio.lelohub.domain.Sequence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Sequence entity.
 */
@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long>, JpaSpecificationExecutor<Sequence> {

    @Query("select sequence from Sequence sequence where sequence.user.login = ?#{principal.username}")
    List<Sequence> findByUserIsCurrentUser();
    
    @EntityGraph(attributePaths = {
    		"steps",
    		"steps.conditions",
    		"steps.conditions.definition",
    		"steps.conditions.parameters",
    		"steps.conditions.parameters.definition",
    		"steps.conditions.andConditions",
    		"steps.conditions.andConditions.definition",
    		"steps.conditions.andConditions.parameters",
    		"steps.conditions.andConditions.parameters.definition",
    		"steps.conditions.orConditions",
    		"steps.conditions.orConditions.definition",
    		"steps.conditions.orConditions.parameters",
    		"steps.conditions.orConditions.parameters.definition",
    		"steps.actions",
    		"steps.actions.definition",
    		"steps.actions.parameters",
    		"steps.actions.parameters.definition" })
    Sequence findOneById(Long id);
}
