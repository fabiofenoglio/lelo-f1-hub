package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepCondition} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepConditionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-conditions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepConditionCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SequenceStepConditionGeneration
     */
    public static class SequenceStepConditionGenerationFilter extends Filter<SequenceStepConditionGeneration> {

        public SequenceStepConditionGenerationFilter() {
        }

        public SequenceStepConditionGenerationFilter(SequenceStepConditionGenerationFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepConditionGenerationFilter copy() {
            return new SequenceStepConditionGenerationFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepConditionGenerationFilter generation;

    private StringFilter description;

    private BooleanFilter negate;

    private LongFilter parametersId;

    private LongFilter andConditionsId;

    private LongFilter orConditionsId;

    private LongFilter definitionId;

    private LongFilter stepId;

    private LongFilter otherAndConditionId;

    private LongFilter otherOrConditionId;

    public SequenceStepConditionCriteria() {
    }

    public SequenceStepConditionCriteria(SequenceStepConditionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.negate = other.negate == null ? null : other.negate.copy();
        this.parametersId = other.parametersId == null ? null : other.parametersId.copy();
        this.andConditionsId = other.andConditionsId == null ? null : other.andConditionsId.copy();
        this.orConditionsId = other.orConditionsId == null ? null : other.orConditionsId.copy();
        this.definitionId = other.definitionId == null ? null : other.definitionId.copy();
        this.stepId = other.stepId == null ? null : other.stepId.copy();
        this.otherAndConditionId = other.otherAndConditionId == null ? null : other.otherAndConditionId.copy();
        this.otherOrConditionId = other.otherOrConditionId == null ? null : other.otherOrConditionId.copy();
    }

    @Override
    public SequenceStepConditionCriteria copy() {
        return new SequenceStepConditionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceStepConditionGenerationFilter getGeneration() {
        return generation;
    }

    public void setGeneration(SequenceStepConditionGenerationFilter generation) {
        this.generation = generation;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getNegate() {
        return negate;
    }

    public void setNegate(BooleanFilter negate) {
        this.negate = negate;
    }

    public LongFilter getParametersId() {
        return parametersId;
    }

    public void setParametersId(LongFilter parametersId) {
        this.parametersId = parametersId;
    }

    public LongFilter getAndConditionsId() {
        return andConditionsId;
    }

    public void setAndConditionsId(LongFilter andConditionsId) {
        this.andConditionsId = andConditionsId;
    }

    public LongFilter getOrConditionsId() {
        return orConditionsId;
    }

    public void setOrConditionsId(LongFilter orConditionsId) {
        this.orConditionsId = orConditionsId;
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

    public LongFilter getOtherAndConditionId() {
        return otherAndConditionId;
    }

    public void setOtherAndConditionId(LongFilter otherAndConditionId) {
        this.otherAndConditionId = otherAndConditionId;
    }

    public LongFilter getOtherOrConditionId() {
        return otherOrConditionId;
    }

    public void setOtherOrConditionId(LongFilter otherOrConditionId) {
        this.otherOrConditionId = otherOrConditionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepConditionCriteria that = (SequenceStepConditionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(negate, that.negate) &&
            Objects.equals(parametersId, that.parametersId) &&
            Objects.equals(andConditionsId, that.andConditionsId) &&
            Objects.equals(orConditionsId, that.orConditionsId) &&
            Objects.equals(definitionId, that.definitionId) &&
            Objects.equals(stepId, that.stepId) &&
            Objects.equals(otherAndConditionId, that.otherAndConditionId) &&
            Objects.equals(otherOrConditionId, that.otherOrConditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        description,
        negate,
        parametersId,
        andConditionsId,
        orConditionsId,
        definitionId,
        stepId,
        otherAndConditionId,
        otherOrConditionId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepConditionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (negate != null ? "negate=" + negate + ", " : "") +
                (parametersId != null ? "parametersId=" + parametersId + ", " : "") +
                (andConditionsId != null ? "andConditionsId=" + andConditionsId + ", " : "") +
                (orConditionsId != null ? "orConditionsId=" + orConditionsId + ", " : "") +
                (definitionId != null ? "definitionId=" + definitionId + ", " : "") +
                (stepId != null ? "stepId=" + stepId + ", " : "") +
                (otherAndConditionId != null ? "otherAndConditionId=" + otherAndConditionId + ", " : "") +
                (otherOrConditionId != null ? "otherOrConditionId=" + otherOrConditionId + ", " : "") +
            "}";
    }

}
