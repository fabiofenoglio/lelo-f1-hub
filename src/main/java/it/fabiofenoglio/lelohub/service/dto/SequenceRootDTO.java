package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.fabiofenoglio.lelohub.domain.enumeration.ObjectAccessAuthorization;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A DTO for the {@link it.fabiofenoglio.lelohub.domain.Sequence} entity.
 */
public abstract class SequenceRootDTO extends AbstractAuditingDTO implements Serializable, HasID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long id;

	@NotNull
	protected SequenceGeneration generation;

	@NotNull
	@Size(max = 250)
	protected String name;

	@Size(max = 2000)
	protected String description;

	@NotNull
	protected SequenceVisibility visibility;

	protected Long userId;

	protected String userLogin;

	protected Map<ObjectAccessAuthorization, Boolean> authorizations = new HashMap<>();

	public Map<ObjectAccessAuthorization, Boolean> getAuthorizations() {
		return authorizations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SequenceGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceGeneration generation) {
		this.generation = generation;
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

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SequenceRootDTO other = (SequenceRootDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
