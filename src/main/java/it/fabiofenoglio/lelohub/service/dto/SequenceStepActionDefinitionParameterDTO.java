package it.fabiofenoglio.lelohub.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionEvaluationEngine;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter} entity.
 */
public class SequenceStepActionDefinitionParameterDTO implements Serializable, HasID {
    
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

    @NotNull
    @Size(max = 250)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    private SequenceStepActionDefinitionParameterType type;

    @NotNull
    private SequenceStepActionEvaluationEngine evaluationEngine;

    private Double minValue;

    private Double maxValue;

    private Double stepValue;

    private Integer minLength;

    private Integer maxLength;

    private Long definitionId;

    private String definitionCode;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceStepActionDefinitionParameterType getType() {
        return type;
    }

    public void setType(SequenceStepActionDefinitionParameterType type) {
        this.type = type;
    }

    public SequenceStepActionEvaluationEngine getEvaluationEngine() {
        return evaluationEngine;
    }

    public void setEvaluationEngine(SequenceStepActionEvaluationEngine evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getStepValue() {
        return stepValue;
    }

    public void setStepValue(Double stepValue) {
        this.stepValue = stepValue;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = (SequenceStepActionDefinitionParameterDTO) o;
        if (sequenceStepActionDefinitionParameterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sequenceStepActionDefinitionParameterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SequenceStepActionDefinitionParameterDTO{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", evaluationEngine='" + getEvaluationEngine() + "'" +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", stepValue=" + getStepValue() +
            ", minLength=" + getMinLength() +
            ", maxLength=" + getMaxLength() +
            ", definitionId=" + getDefinitionId() +
            ", definitionCode='" + getDefinitionCode() + "'" +
            "}";
    }
}
