package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.Sequence}
 * entity. This class is used in
 * {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceResource} to receive
 * all the possible filtering options from the Http GET request parameters. For
 * example the following could be a valid request:
 * {@code /sequences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
public class SequenceCriteria implements Serializable, Criteria {
	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	/**
	 * Class for filtering SequenceGeneration
	 */
	public static class SequenceGenerationFilter extends Filter<SequenceGeneration> {

		public SequenceGenerationFilter() {
		}

		public SequenceGenerationFilter(SequenceGenerationFilter filter) {
			super(filter);
		}

		@Override
		public SequenceGenerationFilter copy() {
			return new SequenceGenerationFilter(this);
		}

	}

	/**
	 * Class for filtering SequenceVisibility
	 */
	public static class SequenceVisibilityFilter extends Filter<SequenceVisibility> {

		public SequenceVisibilityFilter() {
		}

		public SequenceVisibilityFilter(SequenceVisibilityFilter filter) {
			super(filter);
		}

		@Override
		public SequenceVisibilityFilter copy() {
			return new SequenceVisibilityFilter(this);
		}

	}

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private SequenceGenerationFilter generation;

	private StringFilter name;

	private StringFilter description;

	private SequenceVisibilityFilter visibility;

	private LongFilter stepsId;

	private LongFilter userId;

	private StringFilter userLogin;

	private Boolean own;

	private Boolean shared;

	private String visibleToUser;

	private String fullTextSearch;

	public SequenceCriteria() {
	}

	public SequenceCriteria(SequenceCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.generation = other.generation == null ? null : other.generation.copy();
		this.name = other.name == null ? null : other.name.copy();
		this.description = other.description == null ? null : other.description.copy();
		this.visibility = other.visibility == null ? null : other.visibility.copy();
		this.stepsId = other.stepsId == null ? null : other.stepsId.copy();
		this.userId = other.userId == null ? null : other.userId.copy();
		this.userLogin = other.userLogin == null ? null : other.userLogin.copy();
		this.own = other.own;
		this.shared = other.shared;
		this.visibleToUser = other.visibleToUser;
		this.fullTextSearch = other.fullTextSearch;
	}

	@Override
	public SequenceCriteria copy() {
		return new SequenceCriteria(this);
	}

	public String getVisibleToUser() {
		return visibleToUser;
	}

	public void setVisibleToUser(String visibleToUser) {
		this.visibleToUser = visibleToUser;
	}

	public StringFilter getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(StringFilter userLogin) {
		this.userLogin = userLogin;
	}

	public Boolean getOwn() {
		return own;
	}

	public void setOwn(Boolean own) {
		this.own = own;
	}

	public Boolean getShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	public LongFilter getId() {
		return id;
	}

	public void setId(LongFilter id) {
		this.id = id;
	}

	public SequenceGenerationFilter getGeneration() {
		return generation;
	}

	public void setGeneration(SequenceGenerationFilter generation) {
		this.generation = generation;
	}

	public StringFilter getName() {
		return name;
	}

	public void setName(StringFilter name) {
		this.name = name;
	}

	public StringFilter getDescription() {
		return description;
	}

	public void setDescription(StringFilter description) {
		this.description = description;
	}

	public SequenceVisibilityFilter getVisibility() {
		return visibility;
	}

	public void setVisibility(SequenceVisibilityFilter visibility) {
		this.visibility = visibility;
	}

	public LongFilter getStepsId() {
		return stepsId;
	}

	public void setStepsId(LongFilter stepsId) {
		this.stepsId = stepsId;
	}

	public LongFilter getUserId() {
		return userId;
	}

	public void setUserId(LongFilter userId) {
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
		final SequenceCriteria that = (SequenceCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(generation, that.generation)
				&& Objects.equals(name, that.name) && Objects.equals(description, that.description)
				&& Objects.equals(visibility, that.visibility) && Objects.equals(stepsId, that.stepsId)
				&& Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, generation, name, description, visibility, stepsId, userId);
	}

	@Override
	public String toString() {
		return "SequenceCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (generation != null ? "generation=" + generation + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (visibility != null ? "visibility=" + visibility + ", " : "")
				+ (stepsId != null ? "stepsId=" + stepsId + ", " : "")
				+ (userId != null ? "userId=" + userId + ", " : "") + "}";
	}

}
