package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceUserRating} entity.
 */
public class SequenceUserRatingVoteActionDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer score;

    private Long sequenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SequenceUserRatingVoteActionDTO sequenceUserRatingDTO = (SequenceUserRatingVoteActionDTO) o;
        if (sequenceUserRatingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sequenceUserRatingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SequenceUserRatingDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", sequenceId=" + getSequenceId() +
            "}";
    }
}
