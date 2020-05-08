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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceStepAction.
 */
@Entity
@Table(name = "sequence_step_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceStepAction extends AbstractAuditingEntity<SequenceStepAction> implements Serializable, HasID {

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "ordinal", nullable = false)
	private Integer ordinal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "generation", nullable = false)
	private SequenceStepActionGeneration generation = SequenceStepActionGeneration.GEN1;

	@Size(max = 250)
	@Column(name = "description", length = 250)
	private String description;

	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<SequenceStepActionParameter> parameters = new HashSet<>();

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("sequenceStepActions")
	private SequenceStepActionDefinition definition;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("actions")
	private SequenceStep step;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceStepActionGeneration getGeneration() {
		return generation;
	}

	public SequenceStepAction generation(SequenceStepActionGeneration generation) {
		this.generation = generation;
		return this;
	}

	public void setGeneration(SequenceStepActionGeneration generation) {
		this.generation = generation;
	}

	public String getDescription() {
		return description;
	}

	public SequenceStepAction description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SequenceStepActionParameter> getParameters() {
		return parameters;
	}

	public SequenceStepAction parameters(Set<SequenceStepActionParameter> sequenceStepActionParameters) {
		this.parameters = sequenceStepActionParameters;
		return this;
	}

	public SequenceStepAction addParameters(SequenceStepActionParameter sequenceStepActionParameter) {
		this.parameters.add(sequenceStepActionParameter);
		sequenceStepActionParameter.setAction(this);
		return this;
	}

	public SequenceStepAction removeParameters(SequenceStepActionParameter sequenceStepActionParameter) {
		this.parameters.remove(sequenceStepActionParameter);
		sequenceStepActionParameter.setAction(null);
		return this;
	}

	public void setParameters(Set<SequenceStepActionParameter> sequenceStepActionParameters) {
		this.parameters = sequenceStepActionParameters;
	}

	public SequenceStepActionDefinition getDefinition() {
		return definition;
	}

	public SequenceStepAction definition(SequenceStepActionDefinition sequenceStepActionDefinition) {
		this.definition = sequenceStepActionDefinition;
		return this;
	}

	public void setDefinition(SequenceStepActionDefinition sequenceStepActionDefinition) {
		this.definition = sequenceStepActionDefinition;
	}

	public SequenceStep getStep() {
		return step;
	}

	public SequenceStepAction step(SequenceStep sequenceStep) {
		this.step = sequenceStep;
		return this;
	}

	public void setStep(SequenceStep sequenceStep) {
		this.step = sequenceStep;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SequenceStepAction)) {
			return false;
		}
		return id != null && id.equals(((SequenceStepAction) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "SequenceStepAction{" + "id=" + getId() + ", generation='" + getGeneration() + "'" + ", description='"
				+ getDescription() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", createdBy='" + getCreatedBy()
				+ "'" + ", lastModifiedDate='" + getLastModifiedDate() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + "}";
	}
}
