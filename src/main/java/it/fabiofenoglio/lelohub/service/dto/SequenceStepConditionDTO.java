package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepCondition}
 * entity.
 */
public class SequenceStepConditionDTO extends AbstractAuditingDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private SequenceStepConditionGeneration generation;

	@Size(max = 250)
	private String description;

	private Boolean negate;

	private Long definitionId;

	private String definitionCode;

	private Long stepId;

	private Long otherAndConditionId;

	private Long otherOrConditionId;

	private SequenceStepConditionDefinitionDTO definition;

	private Set<SequenceStepConditionParameterDTO> parameters = new HashSet<>();

	private Set<SequenceStepConditionDTO> andConditions = new HashSet<>();

	private Set<SequenceStepConditionDTO> orConditions = new HashSet<>();

	public SequenceStepConditionDefinitionDTO getDefinition() {
		return definition;
	}

	public void setDefinition(SequenceStepConditionDefinitionDTO definition) {
		this.definition = definition;
	}

	public Set<SequenceStepConditionParameterDTO> getParameters() {
		return parameters;
	}

	public void setParameters(Set<SequenceStepConditionParameterDTO> parameters) {
		this.parameters = parameters;
	}

	public Set<SequenceStepConditionDTO> getAndConditions() {
		return andConditions;
	}

	public void setAndConditions(Set<SequenceStepConditionDTO> andConditions) {
		this.andConditions = andConditions;
	}

	public Set<SequenceStepConditionDTO> getOrConditions() {
		return orConditions;
	}

	public void setOrConditions(Set<SequenceStepConditionDTO> orConditions) {
		this.orConditions = orConditions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceStepConditionGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceStepConditionGeneration generation) {
		this.generation = generation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isNegate() {
		return negate;
	}

	public void setNegate(Boolean negate) {
		this.negate = negate;
	}

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long sequenceStepConditionDefinitionId) {
		this.definitionId = sequenceStepConditionDefinitionId;
	}

	public String getDefinitionCode() {
		return definitionCode;
	}

	public void setDefinitionCode(String sequenceStepConditionDefinitionCode) {
		this.definitionCode = sequenceStepConditionDefinitionCode;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long sequenceStepId) {
		this.stepId = sequenceStepId;
	}

	public Long getOtherAndConditionId() {
		return otherAndConditionId;
	}

	public void setOtherAndConditionId(Long sequenceStepConditionId) {
		this.otherAndConditionId = sequenceStepConditionId;
	}

	public Long getOtherOrConditionId() {
		return otherOrConditionId;
	}

	public void setOtherOrConditionId(Long sequenceStepConditionId) {
		this.otherOrConditionId = sequenceStepConditionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceStepConditionDTO sequenceStepConditionDTO = (SequenceStepConditionDTO) o;
		if (sequenceStepConditionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceStepConditionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceStepConditionDTO{" + "id=" + getId() + ", generation='" + getGeneration() + "'"
				+ ", description='" + getDescription() + "'" + ", negate='" + isNegate() + "'" + ", createdDate='"
				+ getCreatedDate() + "'" + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", definitionId="
				+ getDefinitionId() + ", definitionCode='" + getDefinitionCode() + "'" + ", stepId=" + getStepId()
				+ ", otherAndConditionId=" + getOtherAndConditionId() + ", otherOrConditionId="
				+ getOtherOrConditionId() + "}";
	}
}
