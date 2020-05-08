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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionEvaluationEngine;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepActionDefinitionParameter.
 */
@Entity
@Table(name = "seq_step_action_def_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepActionDefinitionParameter implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    public static SequenceStepActionDefinitionParameter fromId(Long id) {
    	SequenceStepActionDefinitionParameter output = new SequenceStepActionDefinitionParameter();
    	output.setId(id);
    	return output;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepActionGeneration generation = SequenceStepActionGeneration.GEN1;

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
    private SequenceStepActionDefinitionParameterType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_engine", nullable = false)
    private SequenceStepActionEvaluationEngine evaluationEngine;

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
    private SequenceStepActionDefinition definition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceStepActionGeneration getGeneration() {
        return generation;
    }

    public SequenceStepActionDefinitionParameter generation(SequenceStepActionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepActionGeneration generation) {
        this.generation = generation;
    }

    public String getCode() {
        return code;
    }

    public SequenceStepActionDefinitionParameter code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public SequenceStepActionDefinitionParameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStepActionDefinitionParameter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceStepActionDefinitionParameterType getType() {
        return type;
    }

    public SequenceStepActionDefinitionParameter type(SequenceStepActionDefinitionParameterType type) {
        this.type = type;
        return this;
    }

    public void setType(SequenceStepActionDefinitionParameterType type) {
        this.type = type;
    }

    public SequenceStepActionEvaluationEngine getEvaluationEngine() {
        return evaluationEngine;
    }

    public SequenceStepActionDefinitionParameter evaluationEngine(SequenceStepActionEvaluationEngine evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
        return this;
    }

    public void setEvaluationEngine(SequenceStepActionEvaluationEngine evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
    }

    public Double getMinValue() {
        return minValue;
    }

    public SequenceStepActionDefinitionParameter minValue(Double minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public SequenceStepActionDefinitionParameter maxValue(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getStepValue() {
        return stepValue;
    }

    public SequenceStepActionDefinitionParameter stepValue(Double stepValue) {
        this.stepValue = stepValue;
        return this;
    }

    public void setStepValue(Double stepValue) {
        this.stepValue = stepValue;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public SequenceStepActionDefinitionParameter minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public SequenceStepActionDefinitionParameter maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public SequenceStepActionDefinition getDefinition() {
        return definition;
    }

    public SequenceStepActionDefinitionParameter definition(SequenceStepActionDefinition sequenceStepActionDefinition) {
        this.definition = sequenceStepActionDefinition;
        return this;
    }

    public void setDefinition(SequenceStepActionDefinition sequenceStepActionDefinition) {
        this.definition = sequenceStepActionDefinition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepActionDefinitionParameter)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepActionDefinitionParameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepActionDefinitionParameter{" +
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
