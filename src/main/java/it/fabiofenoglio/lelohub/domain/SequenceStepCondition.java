package it.fabiofenoglio.lelohub.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepCondition.
 */
@Entity
@Table(name = "sequence_step_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepCondition extends AbstractAuditingEntity<SequenceStepCondition> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepConditionGeneration generation = SequenceStepConditionGeneration.GEN1;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "negate")
    private Boolean negate;

    @OneToMany(mappedBy = "condition", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepConditionParameter> parameters = new HashSet<>();

    @OneToMany(mappedBy = "otherAndCondition", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepCondition> andConditions = new HashSet<>();

    @OneToMany(mappedBy = "otherOrCondition", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepCondition> orConditions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sequenceStepConditions")
    private SequenceStepConditionDefinition definition;

    @ManyToOne
    @JsonIgnoreProperties("conditions")
    private SequenceStep step;

    @ManyToOne
    @JsonIgnoreProperties("andConditions")
    private SequenceStepCondition otherAndCondition;

    @ManyToOne
    @JsonIgnoreProperties("orConditions")
    private SequenceStepCondition otherOrCondition;

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

    public SequenceStepCondition generation(SequenceStepConditionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepConditionGeneration generation) {
        this.generation = generation;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStepCondition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isNegate() {
        return negate;
    }

    public SequenceStepCondition negate(Boolean negate) {
        this.negate = negate;
        return this;
    }

    public void setNegate(Boolean negate) {
        this.negate = negate;
    }

    public Set<SequenceStepConditionParameter> getParameters() {
        return parameters;
    }

    public SequenceStepCondition parameters(Set<SequenceStepConditionParameter> sequenceStepConditionParameters) {
        this.parameters = sequenceStepConditionParameters;
        return this;
    }

    public SequenceStepCondition addParameters(SequenceStepConditionParameter sequenceStepConditionParameter) {
        this.parameters.add(sequenceStepConditionParameter);
        sequenceStepConditionParameter.setCondition(this);
        return this;
    }

    public SequenceStepCondition removeParameters(SequenceStepConditionParameter sequenceStepConditionParameter) {
        this.parameters.remove(sequenceStepConditionParameter);
        sequenceStepConditionParameter.setCondition(null);
        return this;
    }

    public void setParameters(Set<SequenceStepConditionParameter> sequenceStepConditionParameters) {
        this.parameters = sequenceStepConditionParameters;
    }

    public Set<SequenceStepCondition> getAndConditions() {
        return andConditions;
    }

    public SequenceStepCondition andConditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.andConditions = sequenceStepConditions;
        return this;
    }

    public SequenceStepCondition addAndConditions(SequenceStepCondition sequenceStepCondition) {
        this.andConditions.add(sequenceStepCondition);
        sequenceStepCondition.setOtherAndCondition(this);
        return this;
    }

    public SequenceStepCondition removeAndConditions(SequenceStepCondition sequenceStepCondition) {
        this.andConditions.remove(sequenceStepCondition);
        sequenceStepCondition.setOtherAndCondition(null);
        return this;
    }

    public void setAndConditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.andConditions = sequenceStepConditions;
    }

    public Set<SequenceStepCondition> getOrConditions() {
        return orConditions;
    }

    public SequenceStepCondition orConditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.orConditions = sequenceStepConditions;
        return this;
    }

    public SequenceStepCondition addOrConditions(SequenceStepCondition sequenceStepCondition) {
        this.orConditions.add(sequenceStepCondition);
        sequenceStepCondition.setOtherOrCondition(this);
        return this;
    }

    public SequenceStepCondition removeOrConditions(SequenceStepCondition sequenceStepCondition) {
        this.orConditions.remove(sequenceStepCondition);
        sequenceStepCondition.setOtherOrCondition(null);
        return this;
    }

    public void setOrConditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.orConditions = sequenceStepConditions;
    }

    public SequenceStepConditionDefinition getDefinition() {
        return definition;
    }

    public SequenceStepCondition definition(SequenceStepConditionDefinition sequenceStepConditionDefinition) {
        this.definition = sequenceStepConditionDefinition;
        return this;
    }

    public void setDefinition(SequenceStepConditionDefinition sequenceStepConditionDefinition) {
        this.definition = sequenceStepConditionDefinition;
    }

    public SequenceStep getStep() {
        return step;
    }

    public SequenceStepCondition step(SequenceStep sequenceStep) {
        this.step = sequenceStep;
        return this;
    }

    public void setStep(SequenceStep sequenceStep) {
        this.step = sequenceStep;
    }

    public SequenceStepCondition getOtherAndCondition() {
        return otherAndCondition;
    }

    public SequenceStepCondition otherAndCondition(SequenceStepCondition sequenceStepCondition) {
        this.otherAndCondition = sequenceStepCondition;
        return this;
    }

    public void setOtherAndCondition(SequenceStepCondition sequenceStepCondition) {
        this.otherAndCondition = sequenceStepCondition;
    }

    public SequenceStepCondition getOtherOrCondition() {
        return otherOrCondition;
    }

    public SequenceStepCondition otherOrCondition(SequenceStepCondition sequenceStepCondition) {
        this.otherOrCondition = sequenceStepCondition;
        return this;
    }

    public void setOtherOrCondition(SequenceStepCondition sequenceStepCondition) {
        this.otherOrCondition = sequenceStepCondition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepCondition)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepCondition{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", description='" + getDescription() + "'" +
            ", negate='" + isNegate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
