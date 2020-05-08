package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the
 * {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition}
 * entity.
 */
public class SequenceStepConditionDefinitionDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private SequenceStepConditionGeneration generation;

	@NotNull
	@Size(max = 100)
	private String code;

	@Size(max = 250)
	private String description;

	private Set<SequenceStepConditionDefinitionParameterDTO> parameters;

	public Set<SequenceStepConditionDefinitionParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepConditionDefinitionParameterDTO> parameters) {
		this.parameters = parameters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceStepConditionGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceStepConditionGeneration generation) {
		this.generation = generation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

		SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = (SequenceStepConditionDefinitionDTO) o;
		if (sequenceStepConditionDefinitionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepConditionDefinitionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepConditionDefinitionDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'"
				+ ", code='" + getCode() + "'" + ", description='" + getDescription() + "'" + "}";
	}
}
