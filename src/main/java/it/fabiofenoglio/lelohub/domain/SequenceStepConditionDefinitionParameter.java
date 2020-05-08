package it.fabiofenoglio.lelohub.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionEvaluationEngine;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepConditionDefinitionParameter.
 */
@Entity
@Table(name = "seq_step_cond_def_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepConditionDefinitionParameter implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    public static SequenceStepConditionDefinitionParameter fromId(Long id) {
    	SequenceStepConditionDefinitionParameter output = new SequenceStepConditionDefinitionParameter();
    	output.setId(id);
    	return output;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepConditionGeneration generation = SequenceStepConditionGeneration.GEN1;

    @NotNull
    @Size(max = 100)
    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @NotNull
    @Size(max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SequenceStepConditionDefinitionParameterType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_engine", nullable = false)
    private SequenceStepConditionEvaluationEngine evaluationEngine;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "step_value")
    private Double stepValue;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "max_length")
    private Integer maxLength;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parameters")
    private SequenceStepConditionDefinition definition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceStepConditionGeneration getGeneration() {
        return generation;
    }

    public SequenceStepConditionDefinitionParameter generation(SequenceStepConditionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepConditionGeneration generation) {
        this.generation = generation;
    }

    public String getCode() {
        return code;
    }

    public SequenceStepConditionDefinitionParameter code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public SequenceStepConditionDefinitionParameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStepConditionDefinitionParameter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceStepConditionDefinitionParameterType getType() {
        return type;
    }

    public SequenceStepConditionDefinitionParameter type(SequenceStepConditionDefinitionParameterType type) {
        this.type = type;
        return this;
    }

    public void setType(SequenceStepConditionDefinitionParameterType type) {
        this.type = type;
    }

    public SequenceStepConditionEvaluationEngine getEvaluationEngine() {
        return evaluationEngine;
    }

    public SequenceStepConditionDefinitionParameter evaluationEngine(SequenceStepConditionEvaluationEngine evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
        return this;
    }

    public void setEvaluationEngine(SequenceStepConditionEvaluationEngine evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
    }

    public Double getMinValue() {
        return minValue;
    }

    public SequenceStepConditionDefinitionParameter minValue(Double minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public SequenceStepConditionDefinitionParameter maxValue(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getStepValue() {
        return stepValue;
    }

    public SequenceStepConditionDefinitionParameter stepValue(Double stepValue) {
        this.stepValue = stepValue;
        return this;
    }

    public void setStepValue(Double stepValue) {
        this.stepValue = stepValue;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public SequenceStepConditionDefinitionParameter minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public SequenceStepConditionDefinitionParameter maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public SequenceStepConditionDefinition getDefinition() {
        return definition;
    }

    public SequenceStepConditionDefinitionParameter definition(SequenceStepConditionDefinition sequenceStepConditionDefinition) {
        this.definition = sequenceStepConditionDefinition;
        return this;
    }

    public void setDefinition(SequenceStepConditionDefinition sequenceStepConditionDefinition) {
        this.definition = sequenceStepConditionDefinition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepConditionDefinitionParameter)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepConditionDefinitionParameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepConditionDefinitionParameter{" +
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
            "}";
    }
}
