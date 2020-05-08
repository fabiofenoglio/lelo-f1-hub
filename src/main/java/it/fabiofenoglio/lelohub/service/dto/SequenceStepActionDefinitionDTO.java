package it.fabiofenoglio.lelohub.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the
 * {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition} entity.
 */
public class SequenceStepActionDefinitionDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private SequenceStepActionGeneration generation;

	@NotNull
	@Size(max = 100)
	private String code;

	@Size(max = 250)
	private String description;

	private Set<SequenceStepActionDefinitionParameterDTO> parameters;

	public Set<SequenceStepActionDefinitionParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepActionDefinitionParameterDTO> parameters) {
		this.parameters = parameters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceStepActionGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceStepActionGeneration generation) {
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

		SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = (SequenceStepActionDefinitionDTO) o;
		if (sequenceStepActionDefinitionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepActionDefinitionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepActionDefinitionDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'"
				+ ", code='" + getCode() + "'" + ", description='" + getDescription() + "'" + "}";
	}
}
