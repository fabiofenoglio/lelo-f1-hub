package it.fabiofenoglio.lelohub.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.fabiofenoglio.lelohub.domain.SequenceUserRating;

/**
 * Spring Data  repository for the SequenceUserRating entity.
 */
@Repository
public interface SequenceUserRatingRepository extends JpaRepository<SequenceUserRating, Long>, JpaSpecificationExecutor<SequenceUserRating> {

    @Query("select sequenceUserRating from SequenceUserRating sequenceUserRating where sequenceUserRating.user.login = ?#{principal.username}")
    List<SequenceUserRating> findByUserIsCurrentUser();

    @Query("select sequenceUserRating from SequenceUserRating sequenceUserRating where sequenceUserRating.user.login = ?#{principal.username} and sequenceUserRating.sequence.id = ?1")
    List<SequenceUserRating> findForSequenceByCurrentUser(Long sequenceId);

    @Query("select sequenceUserRating from SequenceUserRating sequenceUserRating where sequenceUserRating.user.login = ?#{principal.username} and sequenceUserRating.sequence.id IN ?1")
    List<SequenceUserRating> findForSequencesByCurrentUser(Collection<Long> sequenceIdList);
}
