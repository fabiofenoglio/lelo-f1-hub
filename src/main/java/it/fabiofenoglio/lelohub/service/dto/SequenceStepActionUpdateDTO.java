package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepAction}
 * entity.
 */
public class SequenceStepActionUpdateDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(max = 250)
	private String description;

	private Long definitionId;

	private Set<SequenceStepActionParameterUpdateDTO> parameters = new HashSet<>();

	private Integer ordinal;

	public Set<SequenceStepActionParameterUpdateDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepActionParameterUpdateDTO> parameters) {
		this.parameters = parameters;
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

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long sequenceStepActionDefinitionId) {
		this.definitionId = sequenceStepActionDefinitionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepActionUpdateDTO sequenceStepActionDTO = (SequenceStepActionUpdateDTO) o;
		if (sequenceStepActionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepActionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepActionDTO{" + "id=" + getId() + ", description='" + getDescription() + "'"
				+ ", definitionId=" + getDefinitionId() + "}";
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}
}
