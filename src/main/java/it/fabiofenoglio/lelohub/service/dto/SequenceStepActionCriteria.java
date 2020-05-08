package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepAction} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepActionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-actions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepActionCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SequenceStepActionGeneration
     */
    public static class SequenceStepActionGenerationFilter extends Filter<SequenceStepActionGeneration> {

        public SequenceStepActionGenerationFilter() {
        }

        public SequenceStepActionGenerationFilter(SequenceStepActionGenerationFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepActionGenerationFilter copy() {
            return new SequenceStepActionGenerationFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepActionGenerationFilter generation;

    private StringFilter description;

    private LongFilter parametersId;

    private LongFilter definitionId;

    private LongFilter stepId;

    public SequenceStepActionCriteria() {
    }

    public SequenceStepActionCriteria(SequenceStepActionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.parametersId = other.parametersId == null ? null : other.parametersId.copy();
        this.definitionId = other.definitionId == null ? null : other.definitionId.copy();
        this.stepId = other.stepId == null ? null : other.stepId.copy();
    }

    @Override
    public SequenceStepActionCriteria copy() {
        return new SequenceStepActionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceStepActionGenerationFilter getGeneration() {
        return generation;
    }

    public void setGeneration(SequenceStepActionGenerationFilter generation) {
        this.generation = generation;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getParametersId() {
        return parametersId;
    }

    public void setParametersId(LongFilter parametersId) {
        this.parametersId = parametersId;
    }

    public LongFilter getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(LongFilter definitionId) {
        this.definitionId = definitionId;
    }

    public LongFilter getStepId() {
        return stepId;
    }

    public void setStepId(LongFilter stepId) {
        this.stepId = stepId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepActionCriteria that = (SequenceStepActionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(parametersId, that.parametersId) &&
            Objects.equals(definitionId, that.definitionId) &&
            Objects.equals(stepId, that.stepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        description,
        parametersId,
        definitionId,
        stepId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepActionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (parametersId != null ? "parametersId=" + parametersId + ", " : "") +
                (definitionId != null ? "definitionId=" + definitionId + ", " : "") +
                (stepId != null ? "stepId=" + stepId + ", " : "") +
            "}";
    }

}
