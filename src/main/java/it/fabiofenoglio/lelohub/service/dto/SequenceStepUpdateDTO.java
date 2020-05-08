package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStep} entity.
 */
public class SequenceStepUpdateDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private Integer ordinal;

	@Size(max = 250)
	private String description;

	private Set<SequenceStepConditionUpdateDTO> conditions = new HashSet<>();

	private Set<SequenceStepActionUpdateDTO> actions = new HashSet<>();

	public Set<SequenceStepConditionUpdateDTO> getConditions() {
		return conditions;
	}

	public void setConditions(Set<SequenceStepConditionUpdateDTO> conditions) {
		this.conditions = conditions;
	}

	public Set<SequenceStepActionUpdateDTO> getActions() {
		return actions;
	}

	public void setActions(Set<SequenceStepActionUpdateDTO> actions) {
		this.actions = actions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepUpdateDTO sequenceStepDTO = (SequenceStepUpdateDTO) o;
		if (sequenceStepDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepDTO{" + "id=" + getId() + ", ordinal=" + getOrdinal() + ", description='" + getDescription()
				+ "'" + "}";
	}
}
