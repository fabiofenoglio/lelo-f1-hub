package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepCondition}
 * entity.
 */
public class SequenceStepConditionUpdateDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(max = 250)
	private String description;

	private Boolean negate;

	private Long definitionId;

	private Set<SequenceStepConditionParameterUpdateDTO> parameters = new HashSet<>();

	private Set<SequenceStepConditionUpdateDTO> andConditions = new HashSet<>();

	private Set<SequenceStepConditionUpdateDTO> orConditions = new HashSet<>();

	public Set<SequenceStepConditionParameterUpdateDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepConditionParameterUpdateDTO> parameters) {
		this.parameters = parameters;
	}

	public Set<SequenceStepConditionUpdateDTO> getAndConditions() {
		return andConditions;
	}

	public void setAndConditions(Set<SequenceStepConditionUpdateDTO> andConditions) {
		this.andConditions = andConditions;
	}

	public Set<SequenceStepConditionUpdateDTO> getOrConditions() {
		return orConditions;
	}

	public void setOrConditions(Set<SequenceStepConditionUpdateDTO> orConditions) {
		this.orConditions = orConditions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isNegate() {
		return negate;
	}

	public void setNegate(Boolean negate) {
		this.negate = negate;
	}

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long sequenceStepConditionDefinitionId) {
		this.definitionId = sequenceStepConditionDefinitionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepConditionUpdateDTO sequenceStepConditionDTO = (SequenceStepConditionUpdateDTO) o;
		if (sequenceStepConditionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepConditionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepConditionDTO{" + "id=" + getId() + ", description='" + getDescription() + "'" + ", negate='"
				+ isNegate() + "'" + ", definitionId=" + getDefinitionId() + "}";
	}
}
