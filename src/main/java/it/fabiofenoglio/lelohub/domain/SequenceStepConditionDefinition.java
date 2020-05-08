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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepConditionDefinition.
 */
@Entity
@Table(name = "seq_step_cond_def")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepConditionDefinition implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    public static SequenceStepConditionDefinition fromId(Long id) {
    	SequenceStepConditionDefinition output = new SequenceStepConditionDefinition();
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

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepConditionDefinitionParameter> parameters = new HashSet<>();

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

    public SequenceStepConditionDefinition generation(SequenceStepConditionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepConditionGeneration generation) {
        this.generation = generation;
    }

    public String getCode() {
        return code;
    }

    public SequenceStepConditionDefinition code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStepConditionDefinition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SequenceStepConditionDefinitionParameter> getParameters() {
        return parameters;
    }

    public SequenceStepConditionDefinition parameters(Set<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameters) {
        this.parameters = sequenceStepConditionDefinitionParameters;
        return this;
    }

    public SequenceStepConditionDefinition addParameters(SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter) {
        this.parameters.add(sequenceStepConditionDefinitionParameter);
        sequenceStepConditionDefinitionParameter.setDefinition(this);
        return this;
    }

    public SequenceStepConditionDefinition removeParameters(SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter) {
        this.parameters.remove(sequenceStepConditionDefinitionParameter);
        sequenceStepConditionDefinitionParameter.setDefinition(null);
        return this;
    }

    public void setParameters(Set<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameters) {
        this.parameters = sequenceStepConditionDefinitionParameters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepConditionDefinition)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepConditionDefinition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepConditionDefinition{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
