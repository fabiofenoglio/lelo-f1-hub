package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceOperator;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter} entity.
 */
public class SequenceStepConditionParameterDTO extends AbstractAuditingDTO implements Serializable, HasID {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private SequenceStepConditionGeneration generation;

    private String valueString;

    private Double valueNumber;

    private Boolean valueBoolean;

    private SequenceVariable valueVariable;

    private SequenceOperator valueOperator;

    private Long definitionId;

    private String definitionName;

    private Long conditionId;
    
    private SequenceStepConditionDefinitionParameterDTO definition;

    public SequenceStepConditionDefinitionParameterDTO getDefinition() {
		return definition;
	}

	public void setDefinition(SequenceStepConditionDefinitionParameterDTO definition) {
		this.definition = definition;
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

    public SequenceOperator getValueOperator() {
        return valueOperator;
    }

    public void setValueOperator(SequenceOperator valueOperator) {
        this.valueOperator = valueOperator;
    }

    public Long getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(Long sequenceStepConditionDefinitionParameterId) {
        this.definitionId = sequenceStepConditionDefinitionParameterId;
    }

    public String getDefinitionName() {
        return definitionName;
    }

    public void setDefinitionName(String sequenceStepConditionDefinitionParameterName) {
        this.definitionName = sequenceStepConditionDefinitionParameterName;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long sequenceStepConditionId) {
        this.conditionId = sequenceStepConditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO = (SequenceStepConditionParameterDTO) o;
        if (sequenceStepConditionParameterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sequenceStepConditionParameterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SequenceStepConditionParameterDTO{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", valueString='" + getValueString() + "'" +
            ", valueNumber=" + getValueNumber() +
            ", valueBoolean='" + isValueBoolean() + "'" +
            ", valueVariable='" + getValueVariable() + "'" +
            ", valueOperator='" + getValueOperator() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", definitionId=" + getDefinitionId() +
            ", definitionName='" + getDefinitionName() + "'" +
            ", conditionId=" + getConditionId() +
            "}";
    }
}
