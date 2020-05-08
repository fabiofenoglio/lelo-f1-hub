package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.Sequence} entity.
 */
public class SequenceUpdateDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(max = 250)
	private String name;

	@Size(max = 2000)
	private String description;

	@NotNull
	private SequenceVisibility visibility;

	private Long userId;

	private Set<SequenceStepUpdateDTO> steps = new HashSet<>();

	public Set<SequenceStepUpdateDTO> getSteps() {
		return steps;
	}

	public void setSteps(Set<SequenceStepUpdateDTO> steps) {
		this.steps = steps;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SequenceVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(SequenceVisibility visibility) {
		this.visibility = visibility;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SequenceUpdateDTO sequenceDTO = (SequenceUpdateDTO) o;
		if (sequenceDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sequenceDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SequenceDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription()
				+ "'" + ", visibility='" + getVisibility() + "'" + ", userId=" + getUserId() + "}";
	}
}
