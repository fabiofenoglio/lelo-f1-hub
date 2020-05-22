package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceUserRating} entity.
 */
public class SequenceUserRatingUnvoteActionDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long sequenceId;

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

}
