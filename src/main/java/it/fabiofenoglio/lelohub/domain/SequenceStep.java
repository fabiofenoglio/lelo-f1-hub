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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStep.
 */
@Entity
@Table(name = "sequence_step")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStep extends AbstractAuditingEntity<SequenceStep> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceStepGeneration generation = SequenceStepGeneration.GEN1;

    @NotNull
    @Column(name = "ordinal", nullable = false)
    private Integer ordinal;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepCondition> conditions = new HashSet<>();

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepAction> actions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("steps")
    private Sequence sequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceStepGeneration getGeneration() {
        return generation;
    }

    public SequenceStep generation(SequenceStepGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepGeneration generation) {
        this.generation = generation;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public SequenceStep ordinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStep description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SequenceStepCondition> getConditions() {
        return conditions;
    }

    public SequenceStep conditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.conditions = sequenceStepConditions;
        return this;
    }

    public SequenceStep addConditions(SequenceStepCondition sequenceStepCondition) {
        this.conditions.add(sequenceStepCondition);
        sequenceStepCondition.setStep(this);
        return this;
    }

    public SequenceStep removeConditions(SequenceStepCondition sequenceStepCondition) {
        this.conditions.remove(sequenceStepCondition);
        sequenceStepCondition.setStep(null);
        return this;
    }

    public void setConditions(Set<SequenceStepCondition> sequenceStepConditions) {
        this.conditions = sequenceStepConditions;
    }

    public Set<SequenceStepAction> getActions() {
        return actions;
    }

    public SequenceStep actions(Set<SequenceStepAction> sequenceStepActions) {
        this.actions = sequenceStepActions;
        return this;
    }

    public SequenceStep addActions(SequenceStepAction sequenceStepAction) {
        this.actions.add(sequenceStepAction);
        sequenceStepAction.setStep(this);
        return this;
    }

    public SequenceStep removeActions(SequenceStepAction sequenceStepAction) {
        this.actions.remove(sequenceStepAction);
        sequenceStepAction.setStep(null);
        return this;
    }

    public void setActions(Set<SequenceStepAction> sequenceStepActions) {
        this.actions = sequenceStepActions;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public SequenceStep sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStep)) {
            return false;
        }
        return id != null && id.equals(((SequenceStep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStep{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", ordinal=" + getOrdinal() +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
