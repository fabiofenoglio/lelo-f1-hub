package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepActionParameterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-action-parameters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepActionParameterCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering SequenceVariable
     */
    public static class SequenceVariableFilter extends Filter<SequenceVariable> {

        public SequenceVariableFilter() {
        }

        public SequenceVariableFilter(SequenceVariableFilter filter) {
            super(filter);
        }

        @Override
        public SequenceVariableFilter copy() {
            return new SequenceVariableFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepActionGenerationFilter generation;

    private StringFilter valueString;

    private DoubleFilter valueNumber;

    private BooleanFilter valueBoolean;

    private SequenceVariableFilter valueVariable;

    private LongFilter definitionId;

    private LongFilter actionId;

    public SequenceStepActionParameterCriteria() {
    }

    public SequenceStepActionParameterCriteria(SequenceStepActionParameterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.valueString = other.valueString == null ? null : other.valueString.copy();
        this.valueNumber = other.valueNumber == null ? null : other.valueNumber.copy();
        this.valueBoolean = other.valueBoolean == null ? null : other.valueBoolean.copy();
        this.valueVariable = other.valueVariable == null ? null : other.valueVariable.copy();
        this.definitionId = other.definitionId == null ? null : other.definitionId.copy();
        this.actionId = other.actionId == null ? null : other.actionId.copy();
    }

    @Override
    public SequenceStepActionParameterCriteria copy() {
        return new SequenceStepActionParameterCriteria(this);
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

    public StringFilter getValueString() {
        return valueString;
    }

    public void setValueString(StringFilter valueString) {
        this.valueString = valueString;
    }

    public DoubleFilter getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(DoubleFilter valueNumber) {
        this.valueNumber = valueNumber;
    }

    public BooleanFilter getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(BooleanFilter valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public SequenceVariableFilter getValueVariable() {
        return valueVariable;
    }

    public void setValueVariable(SequenceVariableFilter valueVariable) {
        this.valueVariable = valueVariable;
    }

    public LongFilter getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(LongFilter definitionId) {
        this.definitionId = definitionId;
    }

    public LongFilter getActionId() {
        return actionId;
    }

    public void setActionId(LongFilter actionId) {
        this.actionId = actionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepActionParameterCriteria that = (SequenceStepActionParameterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(valueString, that.valueString) &&
            Objects.equals(valueNumber, that.valueNumber) &&
            Objects.equals(valueBoolean, that.valueBoolean) &&
            Objects.equals(valueVariable, that.valueVariable) &&
            Objects.equals(definitionId, that.definitionId) &&
            Objects.equals(actionId, that.actionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        valueString,
        valueNumber,
        valueBoolean,
        valueVariable,
        definitionId,
        actionId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepActionParameterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (valueString != null ? "valueString=" + valueString + ", " : "") +
                (valueNumber != null ? "valueNumber=" + valueNumber + ", " : "") +
                (valueBoolean != null ? "valueBoolean=" + valueBoolean + ", " : "") +
                (valueVariable != null ? "valueVariable=" + valueVariable + ", " : "") +
                (definitionId != null ? "definitionId=" + definitionId + ", " : "") +
                (actionId != null ? "actionId=" + actionId + ", " : "") +
            "}";
    }

}
