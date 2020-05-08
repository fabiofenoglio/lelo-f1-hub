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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepActionParameter.
 */
@Entity
@Table(name = "sequence_step_action_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepActionParameter extends AbstractAuditingEntity<SequenceStepActionParameter> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepActionGeneration generation = SequenceStepActionGeneration.GEN1;

    @Column(name = "value_string")
    private String valueString;

    @Column(name = "value_number")
    private Double valueNumber;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_variable")
    private SequenceVariable valueVariable;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sequenceStepActionParameters")
    private SequenceStepActionDefinitionParameter definition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parameters")
    private SequenceStepAction action;

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

    public SequenceStepActionParameter generation(SequenceStepActionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepActionGeneration generation) {
        this.generation = generation;
    }

    public String getValueString() {
        return valueString;
    }

    public SequenceStepActionParameter valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Double getValueNumber() {
        return valueNumber;
    }

    public SequenceStepActionParameter valueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
        return this;
    }

    public void setValueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
    }

    public Boolean isValueBoolean() {
        return valueBoolean;
    }

    public SequenceStepActionParameter valueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
        return this;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public SequenceVariable getValueVariable() {
        return valueVariable;
    }

    public SequenceStepActionParameter valueVariable(SequenceVariable valueVariable) {
        this.valueVariable = valueVariable;
        return this;
    }

    public void setValueVariable(SequenceVariable valueVariable) {
        this.valueVariable = valueVariable;
    }

    public SequenceStepActionDefinitionParameter getDefinition() {
        return definition;
    }

    public SequenceStepActionParameter definition(SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter) {
        this.definition = sequenceStepActionDefinitionParameter;
        return this;
    }

    public void setDefinition(SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter) {
        this.definition = sequenceStepActionDefinitionParameter;
    }

    public SequenceStepAction getAction() {
        return action;
    }

    public SequenceStepActionParameter action(SequenceStepAction sequenceStepAction) {
        this.action = sequenceStepAction;
        return this;
    }

    public void setAction(SequenceStepAction sequenceStepAction) {
        this.action = sequenceStepAction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepActionParameter)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepActionParameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepActionParameter{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", valueString='" + getValueString() + "'" +
            ", valueNumber=" + getValueNumber() +
            ", valueBoolean='" + isValueBoolean() + "'" +
            ", valueVariable='" + getValueVariable() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
