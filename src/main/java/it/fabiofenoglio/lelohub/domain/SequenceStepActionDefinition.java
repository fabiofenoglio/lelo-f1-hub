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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepActionDefinition.
 */
@Entity
@Table(name = "seq_step_action_def")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepActionDefinition implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    public static SequenceStepActionDefinition fromId(Long id) {
    	SequenceStepActionDefinition output = new SequenceStepActionDefinition();
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

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStepActionDefinitionParameter> parameters = new HashSet<>();

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

    public SequenceStepActionDefinition generation(SequenceStepActionGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceStepActionGeneration generation) {
        this.generation = generation;
    }

    public String getCode() {
        return code;
    }

    public SequenceStepActionDefinition code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public SequenceStepActionDefinition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SequenceStepActionDefinitionParameter> getParameters() {
        return parameters;
    }

    public SequenceStepActionDefinition parameters(Set<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameters) {
        this.parameters = sequenceStepActionDefinitionParameters;
        return this;
    }

    public SequenceStepActionDefinition addParameters(SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter) {
        this.parameters.add(sequenceStepActionDefinitionParameter);
        sequenceStepActionDefinitionParameter.setDefinition(this);
        return this;
    }

    public SequenceStepActionDefinition removeParameters(SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter) {
        this.parameters.remove(sequenceStepActionDefinitionParameter);
        sequenceStepActionDefinitionParameter.setDefinition(null);
        return this;
    }

    public void setParameters(Set<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameters) {
        this.parameters = sequenceStepActionDefinitionParameters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceStepActionDefinition)) {
            return false;
        }
        return id != null && id.equals(((SequenceStepActionDefinition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceStepActionDefinition{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
