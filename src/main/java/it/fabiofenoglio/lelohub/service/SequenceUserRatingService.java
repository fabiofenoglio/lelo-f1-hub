package it.fabiofenoglio.lelohub.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Collections;
import it.fabiofenoglio.lelohub.domain.Sequence;
import it.fabiofenoglio.lelohub.domain.SequenceUserRating;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;
import it.fabiofenoglio.lelohub.repository.SequenceRepository;
import it.fabiofenoglio.lelohub.repository.SequenceUserRatingRepository;
import it.fabiofenoglio.lelohub.repository.UserRepository;
import it.fabiofenoglio.lelohub.security.SecurityUtils;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingUnvoteActionDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingVoteActionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceUserRatingMapper;
import it.fabiofenoglio.lelohub.web.rest.errors.ForbiddenException;
import it.fabiofenoglio.lelohub.web.rest.errors.NotFoundException;

/**
 * Service Implementation for managing {@link SequenceUserRating}.
 */
@Service
@Transactional
public class SequenceUserRatingService {

    private final Logger log = LoggerFactory.getLogger(SequenceUserRatingService.class);

    private final SequenceUserRatingRepository sequenceUserRatingRepository;

    private final SequenceUserRatingMapper sequenceUserRatingMapper;

    private final UserRepository userRepository;

    private final SequenceRepository sequenceRepository;

    public SequenceUserRatingService(
    		SequenceUserRatingRepository sequenceUserRatingRepository, 
    		SequenceUserRatingMapper sequenceUserRatingMapper,
    		UserRepository userRepository,
    		SequenceRepository sequenceRepository ) {
        this.sequenceUserRatingRepository = sequenceUserRatingRepository;
        this.sequenceUserRatingMapper = sequenceUserRatingMapper;
        this.userRepository = userRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Save a sequenceUserRating.
     *
     * @param sequenceUserRatingDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceUserRatingDTO save(SequenceUserRatingDTO sequenceUserRatingDTO) {
        log.debug("Request to save SequenceUserRating : {}", sequenceUserRatingDTO);
        SequenceUserRating sequenceUserRating = sequenceUserRatingMapper.toEntity(sequenceUserRatingDTO);
        sequenceUserRating = sequenceUserRatingRepository.save(sequenceUserRating);
        return sequenceUserRatingMapper.toDto(sequenceUserRating);
    }

    /**
     * Get all the sequenceUserRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceUserRatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceUserRatings");
        return sequenceUserRatingRepository.findAll(pageable)
            .map(sequenceUserRatingMapper::toDto);
    }

    /**
     * Get one sequenceUserRating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceUserRatingDTO> findOne(Long id) {
        log.debug("Request to get SequenceUserRating : {}", id);
        return sequenceUserRatingRepository.findById(id)
            .map(sequenceUserRatingMapper::toDto);
    }

    /**
     * Delete the sequenceUserRating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceUserRating : {}", id);
        sequenceUserRatingRepository.deleteById(id);
    }

	public SequenceUserRatingDTO userVote(SequenceUserRatingVoteActionDTO voteDTO) {
		List<SequenceUserRating> existing = sequenceUserRatingRepository.findForSequenceByCurrentUser(voteDTO.getSequenceId());
		SequenceUserRating vote;
		
		String currentLogin = SecurityUtils.requireCurrentLogin();

		Sequence parentSequence = sequenceRepository.findOneById(voteDTO.getSequenceId());
		
		if (parentSequence.getUser().getLogin().equals(currentLogin) 
				|| parentSequence.getVisibility() != SequenceVisibility.PUBLIC) {
			throw new ForbiddenException();
		}

		if (existing.isEmpty()) {
			vote = new SequenceUserRating();
			vote.setSequence(Sequence.referenceById(voteDTO.getSequenceId()));
			vote.setUser(this.userRepository.findOneByLogin(SecurityUtils.requireCurrentLogin()).orElseThrow(NotFoundException::new));
		} else {
			vote = existing.get(0);
		}

		vote.setScore(voteDTO.getScore());
		
		vote = sequenceUserRatingRepository.save(vote);
		return sequenceUserRatingMapper.toDto(vote);
	}

	public void userUnVote(SequenceUserRatingUnvoteActionDTO voteDTO) {
		List<SequenceUserRating> existing = sequenceUserRatingRepository.findForSequenceByCurrentUser(voteDTO.getSequenceId());
		SequenceUserRating vote;
		
		if (!existing.isEmpty()) {
			vote = existing.get(0);
			sequenceUserRatingRepository.delete(vote);
		}
	}

	public Map<Long, SequenceUserRatingDTO> findBySequencesForCurrentUser(Collection<Long> idList) {
		Map<Long, SequenceUserRatingDTO> output = new HashMap<>();
		
		if (!Collections.isEmpty(idList)) {
			List<SequenceUserRating> raw = sequenceUserRatingRepository.findForSequencesByCurrentUser(idList);
			for (SequenceUserRating entry: raw) {
				SequenceUserRatingDTO dto = sequenceUserRatingMapper.toDto(entry);
				output.put(dto.getSequenceId(), dto);
			}
		}
		
		return output;
	}
}
