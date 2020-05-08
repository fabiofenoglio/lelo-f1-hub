package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepAction}
 * entity.
 */
public class SequenceStepActionDTO extends AbstractAuditingDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer ordinal;

	@NotNull
	private SequenceStepActionGeneration generation;

	@Size(max = 250)
	private String description;

	private Long definitionId;

	private String definitionCode;

	private Long stepId;

	private SequenceStepActionDefinitionDTO definition;

	private Set<SequenceStepActionParameterDTO> parameters = new HashSet<>();

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public SequenceStepActionDefinitionDTO getDefinition() {
		return definition;
	}

	public void setDefinition(SequenceStepActionDefinitionDTO definition) {
		this.definition = definition;
	}

	public Set<SequenceStepActionParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepActionParameterDTO> parameters) {
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

	public String getDefinitionCode() {
		return definitionCode;
	}

	public void setDefinitionCode(String sequenceStepActionDefinitionCode) {
		this.definitionCode = sequenceStepActionDefinitionCode;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long sequenceStepId) {
		this.stepId = sequenceStepId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepActionDTO sequenceStepActionDTO = (SequenceStepActionDTO) o;
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
		return "SequenceStepActionDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'" + ", description='"
				+ getDescription() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", createdBy='" + getCreatedBy()
				+ "'" + ", lastModifiedDate='" + getLastModifiedDate() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + ", definitionId=" + getDefinitionId() + ", definitionCode='"
				+ getDefinitionCode() + "'" + ", stepId=" + getStepId() + "}";
	}
}
