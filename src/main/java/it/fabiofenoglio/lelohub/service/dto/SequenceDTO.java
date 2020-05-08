package it.fabiofenoglio.lelohub.service.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.Sequence} entity.
 */
public class SequenceDTO extends SequenceRootDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<SequenceStepDTO> steps = new HashSet<>();

	public Set<SequenceStepDTO> getSteps() {
		return steps;
	}

	public void setSteps(Set<SequenceStepDTO> steps) {
		this.steps = steps;
	}

}
