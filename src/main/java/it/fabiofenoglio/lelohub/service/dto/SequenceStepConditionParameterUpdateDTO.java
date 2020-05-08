package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceOperator;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter} entity.
 */
public class SequenceStepConditionParameterUpdateDTO implements Serializable, HasID {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String valueString;

    private Double valueNumber;

    private Boolean valueBoolean;

    private SequenceVariable valueVariable;

    private SequenceOperator valueOperator;

    private Long definitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SequenceStepConditionParameterUpdateDTO sequenceStepConditionParameterDTO = (SequenceStepConditionParameterUpdateDTO) o;
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
            ", valueString='" + getValueString() + "'" +
            ", valueNumber=" + getValueNumber() +
            ", valueBoolean='" + isValueBoolean() + "'" +
            ", valueVariable='" + getValueVariable() + "'" +
            ", valueOperator='" + getValueOperator() + "'" +
            ", definitionId=" + getDefinitionId() +
            "}";
    }
}
