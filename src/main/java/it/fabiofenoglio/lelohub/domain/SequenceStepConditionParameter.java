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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceOperator;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepConditionParameter.
 */
@Entity
@Table(name = "seq_step_cond_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepConditionParameter extends AbstractAuditingEntity<SequenceStepConditionParameter> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepConditionGeneration generation = SequenceStepConditionGeneration.GEN1;

    @Column(name = "value_string")
    private String valueString;

    @Column(name = "value_number")
    private Double valueNumber;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_variable")
    private SequenceVariable valueVariable;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_operator")
    private SequenceOperator valueOperator;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sequenceStepConditionParameters")
    private SequenceStepConditionDefinitionParameter definition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parameters")
    private SequenceStepCondition condition;

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

    public SequenceStepConditionParameter generation(SequenceStepConditionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepConditionGeneration generation) {
        this.generation = generation;
    }

    public String getValueString() {
        return valueString;
    }

    public SequenceStepConditionParameter valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Double getValueNumber() {
        return valueNumber;
    }

    public SequenceStepConditionParameter valueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
        return this;
    }

    public void setValueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
    }

    public Boolean isValueBoolean() {
        return valueBoolean;
    }

    public SequenceStepConditionParameter valueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
        return this;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public SequenceVariable getValueVariable() {
        return valueVariable;
    }

    public SequenceStepConditionParameter valueVariable(SequenceVariable valueVariable) {
        this.valueVariable = valueVariable;
        return this;
    }

    public void setValueVariable(SequenceVariable valueVariable) {
        this.valueVariable = valueVariable;
    }

    public SequenceOperator getValueOperator() {
        return valueOperator;
    }

    public SequenceStepConditionParameter valueOperator(SequenceOperator valueOperator) {
        this.valueOperator = valueOperator;
        return this;
    }

    public void setValueOperator(SequenceOperator valueOperator) {
        this.valueOperator = valueOperator;
    }

    public SequenceStepConditionDefinitionParameter getDefinition() {
        return definition;
    }

    public SequenceStepConditionParameter definition(SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter) {
        this.definition = sequenceStepConditionDefinitionParameter;
        return this;
    }

    public void setDefinition(SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter) {
        this.definition = sequenceStepConditionDefinitionParameter;
    }

    public SequenceStepCondition getCondition() {
        return condition;
    }

    public SequenceStepConditionParameter condition(SequenceStepCondition sequenceStepCondition) {
        this.condition = sequenceStepCondition;
        return this;
    }

    public void setCondition(SequenceStepCondition sequenceStepCondition) {
        this.condition = sequenceStepCondition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepConditionParameter)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepConditionParameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepConditionParameter{" +
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
            "}";
    }
}
