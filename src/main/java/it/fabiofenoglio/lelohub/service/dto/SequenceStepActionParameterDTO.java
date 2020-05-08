package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the
 * {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter} entity.
 */
public class SequenceStepActionParameterDTO extends AbstractAuditingDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private SequenceStepActionGeneration generation;

	private String valueString;

	private Double valueNumber;

	private Boolean valueBoolean;

	private SequenceVariable valueVariable;

	private Long definitionId;

	private String definitionName;

	private Long actionId;

	private SequenceStepActionDefinitionParameterDTO definition;

	public SequenceStepActionDefinitionParameterDTO getDefinition() {
		return definition;
	}

	public void setDefinition(SequenceStepActionDefinitionParameterDTO definition) {
		this.definition = definition;
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

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public Double getValueNumber() {
		return valueNumber;
	}

	public void setValueNumber(Double valueNumber) {
		this.valueNumber = valueNumber;
	}

	public Boolean isValueBoolean() {
		return valueBoolean;
	}

	public void setValueBoolean(Boolean valueBoolean) {
		this.valueBoolean = valueBoolean;
	}

	public SequenceVariable getValueVariable() {
		return valueVariable;
	}

	public void setValueVariable(SequenceVariable valueVariable) {
		this.valueVariable = valueVariable;
	}

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long sequenceStepActionDefinitionParameterId) {
		this.definitionId = sequenceStepActionDefinitionParameterId;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String sequenceStepActionDefinitionParameterName) {
		this.definitionName = sequenceStepActionDefinitionParameterName;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long sequenceStepActionId) {
		this.actionId = sequenceStepActionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepActionParameterDTO sequenceStepActionParameterDTO = (SequenceStepActionParameterDTO) o;
		if (sequenceStepActionParameterDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepActionParameterDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepActionParameterDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'"
				+ ", valueString='" + getValueString() + "'" + ", valueNumber=" + getValueNumber() + ", valueBoolean='"
				+ isValueBoolean() + "'" + ", valueVariable='" + getValueVariable() + "'" + ", createdDate='"
				+ getCreatedDate() + "'" + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", definitionId="
				+ getDefinitionId() + ", definitionName='" + getDefinitionName() + "'" + ", actionId=" + getActionId()
				+ "}";
	}
}
