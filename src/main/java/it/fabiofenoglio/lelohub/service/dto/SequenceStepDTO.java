package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import it.fabiofenoglio.lelohub.domain.SequenceStepAction;
import it.fabiofenoglio.lelohub.domain.SequenceStepCondition;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStep} entity.
 */
public class SequenceStepDTO extends AbstractAuditingDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private SequenceStepGeneration generation;

	@NotNull
	private Integer ordinal;

	@Size(max = 250)
	private String description;

	private Long sequenceId;

	private String sequenceName;

	private Set<SequenceStepConditionDTO> conditions = new HashSet<>();

	private Set<SequenceStepActionDTO> actions = new HashSet<>();

	public Set<SequenceStepConditionDTO> getConditions() {
		return conditions;
	}

	public void setConditions(Set<SequenceStepConditionDTO> conditions) {
		this.conditions = conditions;
	}

	public Set<SequenceStepActionDTO> getActions() {
		return actions;
	}

	public void setActions(Set<SequenceStepActionDTO> actions) {
		this.actions = actions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceStepGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceStepGeneration generation) {
		this.generation = generation;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepDTO sequenceStepDTO = (SequenceStepDTO) o;
		if (sequenceStepDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'" + ", ordinal="
				+ getOrdinal() + ", description='" + getDescription() + "'" + ", createdDate='" + getCreatedDate() + "'"
				+ ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedDate='" + getLastModifiedDate() + "'"
				+ ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", sequenceId=" + getSequenceId()
				+ ", sequenceName='" + getSequenceName() + "'" + "}";
	}
}
